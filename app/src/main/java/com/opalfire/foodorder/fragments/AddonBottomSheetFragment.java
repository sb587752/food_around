package com.opalfire.foodorder.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.CartAddOnsAdapter;
import com.opalfire.foodorder.adapter.ViewCartAdapter;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Addon;
import com.opalfire.foodorder.models.Cart;
import com.opalfire.foodorder.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddonBottomSheetFragment extends BottomSheetDialogFragment {
    @SuppressLint({"RestrictedApi"})
    public static Cart selectedCart;

    List<Addon> addonList;
    Context context;
    BottomSheetBehavior<NestedScrollView> mBottomSheetBehavior;
    @BindView(R.id.food_type)
    ImageView foodType;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.add_ons_rv)
    RecyclerView addOnsRv;
    @BindView(R.id.addons)
    TextView addons;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.update)
    TextView update;
    Unbinder unbinder;
    @BindView(R.id.nsv_addon_bottom_sheet_layout)
    NestedScrollView nsvAddonBottomSheetLayout;
    private Product product;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @SuppressLint({"RestrictedApi"})
    public void setupDialog(final Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        final View inflate = getLayoutInflater().inflate(R.layout.addon_bottom_sheet_fragment, null);
        dialog.setContentView(inflate);
        ButterKnife.bind(this, inflate);
        context = getContext();
        addons = inflate.findViewById(R.id.addons);
        price = inflate.findViewById(R.id.price);
        addOnsRv.setLayoutManager(new LinearLayoutManager(context, 1, false));
        addOnsRv.setItemAnimator(new DefaultItemAnimator());
        addOnsRv.setHasFixedSize(false);
        addOnsRv.setNestedScrollingEnabled(false);
        mBottomSheetBehavior = BottomSheetBehavior.from(nsvAddonBottomSheetLayout);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        addonList = new ArrayList<>();
        addOnsRv.setAdapter(new CartAddOnsAdapter(addonList, context));
        if (selectedCart != null) {
            product = selectedCart.getProduct();
            GlobalData.cartAddons = selectedCart.getCartAddons();
            addonList.clear();
            addonList.addAll(product.getAddons());
            addOnsRv.getAdapter().notifyDataSetChanged();
        } else if (GlobalData.isSelectedProduct != null) {
            product = GlobalData.isSelectedProduct;
            addonList.clear();
            addonList.addAll(product.getAddons());
            addOnsRv.getAdapter().notifyDataSetChanged();
        }
        productName.setText(product.getName());
        productPrice.setText(product.getPrices().getCurrency() + " " + product.getPrices().getPrice());
        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("product_id", product.getId().toString());
                hashMap.put(Param.QUANTITY, String.valueOf(GlobalData.isSelctedCart.getQuantity()));
                hashMap.put("cart_id", String.valueOf(GlobalData.isSelctedCart.getId()));
                for (int i = 0; i < CartAddOnsAdapter.list.size(); i++) {
                    Addon addon = CartAddOnsAdapter.list.get(i);
                    if (addon.getAddon().getChecked()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("product_addons[")
                                .append(i)
                                .append("]");
                        hashMap.put(stringBuilder.toString(), addon.getId().toString());
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("addons_qty[")
                                .append(i)
                                .append("]");
                        hashMap.put(stringBuilder.toString(), addon.getQuantity().toString());
                    }
                }
                Log.e("AddCart_add", hashMap.toString());
                ViewCartAdapter.addCart(hashMap);
                dialog.dismiss();
            }
        });
    }

    private void setAddOnsText() {
        addons.setText("");
        if (selectedCart != null) {
            Product product = selectedCart.getProduct();
            int intValue = selectedCart.getQuantity();
            int i = product.getPrices().getPrice() * intValue;
            Integer obj = 1;
            for (Addon addon : CartAddOnsAdapter.list) {
                if (addon.getAddon().getChecked()) {
                    if (obj != null) {
                        addons.append(addon.getAddon().getName());
                        obj = null;
                    } else {
                        addons.append(", " + addon.getAddon().getName());
                    }
                    i += (addon.getQuantity() * addon.getPrice()) * intValue;
                }
            }
            if (intValue == 1) {
                price.setText(intValue + " Item | " + GlobalData.currencySymbol);
                return;
            }
            price.setText(intValue + " Item | " + GlobalData.currencySymbol);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
