package com.opalfire.orderaround.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.HotelViewActivity;
import com.opalfire.orderaround.activities.ProductDetailActivity;
import com.opalfire.orderaround.adapter.HotelCatagoeryAdapter;
import com.opalfire.orderaround.adapter.ProductsAdapter;
import com.opalfire.orderaround.adapter.ViewCartAdapter;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.AddCart;
import com.opalfire.orderaround.models.Cart;
import com.opalfire.orderaround.models.CartAddon;
import com.opalfire.orderaround.models.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartChoiceModeFragment extends BottomSheetDialogFragment {
    public static boolean isSearch = false;
    public static boolean isViewcart = false;
    public static Cart lastCart;
    Activity activity;
    String addOnsValue = "";
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    List<CartAddon> cartAddonList;
    Context mContext;
    Product product;
    HashMap<String, String> repeatCartMap;
    Unbinder unbinder;
    @BindView(R.id.food_type)
    ImageView foodType;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.add_ons_items_txt)
    TextView addOnsItemsTxt;
    @BindView(R.id.add_ons_qty)
    TextView addOnsQty;
    @BindView(R.id.i_will_choose_btn)
    Button iWillChooseBtn;
    @BindView(R.id.repeat_btn)
    Button repeatBtn;

    private CustomDialog customDialog;

    @SuppressLint({"RestrictedApi"})
    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.cart_choice_mode_fragment, null);
        dialog.setContentView(inflate);
        ButterKnife.bind(this, inflate);
        this.mContext = getContext();
        this.activity = getActivity();
        this.customDialog = new CustomDialog(this.mContext);
        if (GlobalData.isSelectedProduct != null) {
            this.product = GlobalData.isSelectedProduct;
            this.productName.setText(this.product.getName());
            String productPriceValue = this.product.getPrices().getCurrency() + " " + this.product.getPrices().getPrice();
            productPrice.setText(productPriceValue);
            this.cartAddonList = new ArrayList<>();
            i = 0;
            if (GlobalData.addCart != null) {
                if (isViewcart) {
                    this.cartAddonList = lastCart.getCartAddons();
                } else {
                    for (i = 0; i < GlobalData.addCart.getProductList().size(); i++) {
                        if (GlobalData.addCart.getProductList().get(i).getProductId().equals(this.product.getId())) {
                            lastCart = GlobalData.addCart.getProductList().get(i);
                            this.cartAddonList = lastCart.getCartAddons();
                        }
                    }
                }
            } else if (this.product.getCart() != null && this.product.getCart().isEmpty()) {
                this.cartAddonList = this.product.getCart().get(this.product.getCart().size() - 1).getCartAddons();
                lastCart = this.product.getCart().get(this.product.getCart().size() - 1);
            }
            this.addOnsQty.setText(this.mContext.getResources().getQuantityString(R.plurals.add_ons, this.cartAddonList.size(), new Object[]{Integer.valueOf(this.cartAddonList.size())}));
            while (i < this.cartAddonList.size()) {
                if (i == 0) {
                    this.addOnsItemsTxt.setText(this.cartAddonList.get(i).getAddonProduct().getAddon().getName());
                } else {
                    addOnsItemsTxt.append(", " + this.cartAddonList.get(i).getAddonProduct().getAddon().getName());
                }
                i++;
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.i_will_choose_btn, R.id.repeat_btn})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.i_will_choose_btn) {
            this.mContext.startActivity(new Intent(this.mContext, ProductDetailActivity.class));
            this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
        } else if (view.getId() == R.id.repeat_btn) {
            int i;
            this.repeatCartMap = new HashMap<>();
            this.repeatCartMap.put("product_id", this.product.getId().toString());
            this.repeatCartMap.put(Param.QUANTITY, String.valueOf(lastCart.getQuantity() + 1));
            this.repeatCartMap.put("cart_id", String.valueOf(lastCart.getId()));
            for (i = 0; i < this.cartAddonList.size(); i++) {
                CartAddon cartAddon = this.cartAddonList.get(i);
                HashMap<String, String> hashMap = this.repeatCartMap;
                String ssd = "product_addons[" + i + "]";
                hashMap.put(ssd, cartAddon.getAddonProduct().getId().toString());
                hashMap = this.repeatCartMap;
                hashMap.put("addons_qty[" + i + "]", cartAddon.getQuantity().toString());
            }
            Log.e("Repeat_cart", this.repeatCartMap.toString());
            if (isViewcart) {
                ViewCartAdapter.addCart(this.repeatCartMap);
            } else if (isSearch) {
                ProductsAdapter.addCart(this.repeatCartMap);
                if (GlobalData.searchProductList != null) {
                    for (i = 0; i < GlobalData.searchProductList.size(); i++) {
                        if (GlobalData.searchProductList.get(i).getId().equals(this.product.getId())) {
                            GlobalData.searchProductList.get(i).getCart().get(GlobalData.searchProductList.get(i).getCart().size() - 1).setQuantity(lastCart.getQuantity() + 1);
//                            ProductSearchFragment.productsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } else {
                HotelCatagoeryAdapter.addCart(this.repeatCartMap);
                if (HotelViewActivity.categoryList != null) {
                    for (i = 0; i < HotelViewActivity.categoryList.size(); i++) {
                        for (int i2 = 0; i2 < HotelViewActivity.categoryList.get(i).getProducts().size(); i2++) {
                            if (HotelViewActivity.categoryList.get(i).getProducts().get(i2).getId().equals(this.product.getId())) {
                                HotelViewActivity.categoryList.get(i).getProducts().get(i2).getCart().get(HotelViewActivity.categoryList.get(i).getProducts().get(i2).getCart().size() - 1).setQuantity(Integer.valueOf(lastCart.getQuantity().intValue() + 1));
//                                HotelViewActivity.catagoeryAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
            dismiss();
        }
    }

    private void addItem(HashMap<String, String> hashMap) {
        this.customDialog.show();
        apiInterface.postAddCart(hashMap).enqueue(new Callback<AddCart>() {
            @Override
            public void onResponse(@NonNull Call<AddCart> call, @NonNull Response<AddCart> response) {
                CartChoiceModeFragment.this.customDialog.dismiss();
                if (response.isSuccessful()) {
                    GlobalData.addCart = response.body();
                    CartChoiceModeFragment.this.dismiss();
                    return;
                }
                try {
                    Toast.makeText(CartChoiceModeFragment.this.mContext, new JSONObject(response.errorBody().string()).optString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AddCart> call, @NonNull Throwable t) {
                CartChoiceModeFragment.this.customDialog.dismiss();
                Toast.makeText(CartChoiceModeFragment.this.mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                CartChoiceModeFragment.this.dismiss();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


}
