package com.opalfire.foodorder.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.facebook.appevents.AppEventsConstants;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.AccountPaymentActivity;
import com.opalfire.foodorder.activities.SaveDeliveryLocationActivity;
import com.opalfire.foodorder.activities.SetDeliveryLocationActivity;
import com.opalfire.foodorder.adapter.ViewCartAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.ConnectionHelper;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.Cart;
import com.opalfire.foodorder.utils.Utils;
import com.robinhood.ticker.TickerUtils;

import org.json.JSONObject;

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

public class CartFragment extends Fragment {
    private static final String NUMBER_LIST = TickerUtils.provideNumberList();
    public static HashMap<String, String> checkoutMap;
    public static RelativeLayout dataLayout;
    public static int deliveryChargeValue = 0;
    public static TextView deliveryCharges;
    public static TextView discountAmount;
    public static RelativeLayout errorLayout;
    public static TextView itemTotalAmount;
    public static TextView payAmount;
    public static TextView promoCodeApply;
    public static TextView serviceTax;
    public static int tax = 0;
    public static ViewCartAdapter viewCartAdapter;
    public static List<Cart> viewCartItemList;
    int ADDRESS_SELECTION = 1;
    Activity activity;

    ConnectionHelper connectionHelper;
    @BindView(R.id.restaurant_image)
    ImageView restaurantImage;
    @BindView(R.id.restaurant_name)
    TextView restaurantName;
    @BindView(R.id.restaurant_description)
    TextView restaurantDescription;
    @BindView(R.id.order_item_rv)
    RecyclerView orderItemRv;
    @BindView(R.id.custom_notes)
    TextView customNotes;
    @BindView(R.id.use_wallet_chk_box)
    CheckBox useWalletChkBox;
    @BindView(R.id.amount_txt)
    TextView amountTxt;
    @BindView(R.id.wallet_layout)
    LinearLayout walletLayout;
    @BindView(R.id.total_amount)
    TextView totalAmount;
    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;
    @BindView(R.id.address_header)
    TextView addressHeader;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.address_delivery_time)
    TextView addressDeliveryTime;
    @BindView(R.id.add_address_txt)
    TextView addAddressTxt;
    @BindView(R.id.proceed_to_pay_btn)
    Button proceedToPayBtn;
    @BindView(R.id.location_info_layout)
    LinearLayout locationInfoLayout;
    @BindView(R.id.map_marker_image)
    ImageView mapMarkerImage;
    @BindView(R.id.location_error_title)
    TextView locationErrorTitle;
    @BindView(R.id.location_error_sub_title)
    TextView locationErrorSubTitle;
    @BindView(R.id.add_address_btn)
    Button addAddressBtn;
    @BindView(R.id.selected_address_btn)
    Button selectedAddressBtn;
    @BindView(R.id.location_error_layout)
    RelativeLayout locationErrorLayout;
    @BindView(R.id.dummy_image_view)
    ImageView dummyImageView;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.error_layout_description)
    TextView errorLayoutDescription;
    CustomDialog customDialog;
    int discount = 0;
    FragmentManager fragmentManager;
    int itemCount = 0;
    int itemQuantity = 0;
    Fragment orderFullViewFragment;
    int priceAmount = 0;
    ViewSkeletonScreen skeleton;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    Unbinder unbinder;
    private Context context;
    private ViewGroup toolbar;
    private View toolbarLayout;

    @OnClick({R.id.wallet_layout})
    public void onViewClicked() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        context = getContext();
        activity = getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_cart, viewGroup, false);
        unbinder = ButterKnife.bind(this, inflate);
        connectionHelper = new ConnectionHelper(context);
        itemTotalAmount = inflate.findViewById(R.id.item_total_amount);
        deliveryCharges = inflate.findViewById(R.id.delivery_charges);
        promoCodeApply = inflate.findViewById(R.id.promo_code_apply);
        discountAmount = inflate.findViewById(R.id.discount_amount);
        serviceTax = inflate.findViewById(R.id.service_tax);
        payAmount = inflate.findViewById(R.id.total_amount);
        dataLayout = inflate.findViewById(R.id.data_layout);
        errorLayout = inflate.findViewById(R.id.error_layout);
        HomeActivity.updateNotificationCount(context, 0);
        customDialog = new CustomDialog(context);
        skeleton = Skeleton.bind(dataLayout).load(R.layout.skeleton_fragment_cart).show();
        viewCartItemList = new ArrayList<>();
        orderItemRv.setLayoutManager(new LinearLayoutManager(context, 1, false));
        orderItemRv.setItemAnimator(new DefaultItemAnimator());
        orderItemRv.setHasFixedSize(false);
        orderItemRv.setNestedScrollingEnabled(false);
        GlobalData.getInstance();
        if (GlobalData.selectedAddress != null) {
            GlobalData.getInstance();
            if (GlobalData.selectedAddress.getLandmark() != null) {
                GlobalData.getInstance();
                if (GlobalData.addressList.getAddresses().size() == 1) {
                    addAddressTxt.setText(getString(R.string.add_address));
                } else {
                    addAddressTxt.setText(getString(R.string.change_address));
                }
                addAddressBtn.setBackgroundResource(R.drawable.button_corner_bg_green);
                addAddressBtn.setText(getResources().getString(R.string.proceed_to_pay));
                addressHeader.setText(GlobalData.selectedAddress.getType());
                GlobalData.getInstance();
                addressDetail.setText(GlobalData.selectedAddress.getMapAddress());
                if (!(viewCartItemList == null || viewCartItemList.size() > 0)) {
                    addressDeliveryTime.setText(viewCartItemList.get(View.VISIBLE).getProduct().getShop().getEstimatedDeliveryTime().toString() + " Mins");
                }
                return inflate;
            }
        }
        GlobalData.getInstance();
        if (GlobalData.addressList != null) {
            addAddressBtn.setBackgroundResource(R.drawable.button_corner_bg_theme);
            addAddressBtn.setText(getResources().getString(R.string.add_address));
            GlobalData.getInstance();
            locationErrorSubTitle.setText(GlobalData.addressHeader);
            selectedAddressBtn.setVisibility(View.VISIBLE);
            locationErrorLayout.setVisibility(View.VISIBLE);
            locationInfoLayout.setVisibility(View.GONE);
        } else {
            GlobalData.getInstance();
            if (GlobalData.selectedAddress != null) {
                locationErrorSubTitle.setText(GlobalData.selectedAddress.getMapAddress());
            } else {
                GlobalData.getInstance();
                locationErrorSubTitle.setText(GlobalData.addressHeader);
            }
            locationErrorLayout.setVisibility(View.VISIBLE);
            selectedAddressBtn.setVisibility(View.GONE);
            locationInfoLayout.setVisibility(View.GONE);
        }
        return inflate;
    }


    private void getViewCart() {
        apiInterface.getViewCart().enqueue(new Callback<AddCart>() {
            public void onFailure(@NonNull Call<AddCart> call, @NonNull Throwable throwable) {
                errorLayout.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
            }

            public void onResponse(Call<AddCart> call, Response<AddCart> cartResponse) {
                skeleton.hide();
                if (!cartResponse.isSuccessful() && cartResponse.errorBody() != null) {
                    errorLayout.setVisibility(View.VISIBLE);
                    dataLayout.setVisibility(View.GONE);
                }
                try {
                    JSONObject errObj = new JSONObject(cartResponse.errorBody().string());
                    Toast.makeText(context, errObj.optString("message"), Toast.LENGTH_LONG).show();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cartResponse.body() != null && cartResponse.isSuccessful()) {
                    customDialog.dismiss();
                    itemCount = cartResponse.body().getProductList().size();
                    GlobalData.getInstance();
                    GlobalData.notificationCount = cartResponse.body().getProductList().size();
                    if (itemCount == 0) {
                        errorLayout.setVisibility(View.VISIBLE);
                        dataLayout.setVisibility(View.GONE);
                        GlobalData.addCart = cartResponse.body();
                        GlobalData.addCart = null;
                        return;
                    }
                    AddCart mAddCart = cartResponse.body();
                    errorLayout.setVisibility(View.GONE);
                    dataLayout.setVisibility(View.VISIBLE);
                    int i = 0;
                    while (i < itemCount) {
                        itemQuantity += cartResponse.body().getProductList().get(i).getQuantity();
                        if (cartResponse.body().getProductList().get(i).getProduct().getPrices().getPrice() != null) {
                            priceAmount += cartResponse.body().getProductList().get(i).getQuantity() * cartResponse.body().getProductList().get(i).getProduct().getPrices().getPrice();
                        }
                        if ((mAddCart.getProductList().get(i).getCartAddons() != null) && (!mAddCart.getProductList().get(i).getCartAddons().isEmpty())) {
                            int j = 0;
                            while (j < mAddCart.getProductList().get(i).getCartAddons().size()) {
                                priceAmount += mAddCart.getProductList().get(i).getQuantity() * (mAddCart.getProductList().get(i).getCartAddons().get(j).getQuantity() * mAddCart.getProductList().get(i).getCartAddons().get(j).getAddonProduct().getPrice());
                                j += 1;
                            }
                        }
                        i += 1;
                    }
                    GlobalData.notificationCount = itemQuantity;
                    GlobalData.getInstance();
                    GlobalData.addCartShopId = cartResponse.body().getProductList().get(0).getProduct().getShopId();
                    GlobalData.currencySymbol = cartResponse.body().getProductList().get(0).getProduct().getPrices().getCurrency();
                    itemTotalAmount.setText(GlobalData.currencySymbol
                            + " " + priceAmount);
                    if ((cartResponse.body().getProductList().get(0).getProduct().getShop().getOfferMinAmount() != null) && (cartResponse.body().getProductList().get(0).getProduct().getShop().getOfferMinAmount() < priceAmount)) {
                        i = cartResponse.body().getProductList().get(0).getProduct().getShop().getOfferPercent();
                        int d1 = priceAmount;
                        int d2 = i;
                        discount = ((int) (d1 * (d2 * 0.01D)));
                    }
                    discountAmount.setText("- " + GlobalData.currencySymbol + "" + discount);
                    i = priceAmount - discount;
                    double d1 = i;
                    double d2 = cartResponse.body().getTaxPercentage();
                    int j = (int) Math.round(d1 * (d2 * 0.01D));
                    serviceTax.setText(GlobalData.currencySymbol + "" + j);
                    int k = cartResponse.body().getDeliveryCharges();
                    payAmount.setText(GlobalData.currencySymbol + "" + (i + j + k));
                    restaurantName.setText(cartResponse.body().getProductList().get(0).getProduct().getShop().getName());
                    restaurantDescription.setText(cartResponse.body().getProductList().get(0).getProduct().getShop().getDescription());
                    String avatarimageURL = cartResponse.body().getProductList().get(0).getProduct().getShop().getAvatar();
                    Glide.with(context).load(avatarimageURL).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_restaurant_place_holder).error(R.drawable.ic_restaurant_place_holder)).into(restaurantImage);
                    deliveryChargeValue = cartResponse.body().getDeliveryCharges();
                    deliveryCharges.setText(GlobalData.currencySymbol + "" + cartResponse.body().getDeliveryCharges().toString());
                    viewCartItemList.clear();
                    viewCartItemList = cartResponse.body().getProductList();
                    viewCartAdapter = new ViewCartAdapter(viewCartItemList, context);
                    orderItemRv.setAdapter(viewCartAdapter);
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        priceAmount = 0;
        discount = 0;
        itemCount = 0;
        itemQuantity = 0;
        if (GlobalData.profileModel != null) {
            int intValue = GlobalData.profileModel.getWalletBalance();
            dataLayout.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            skeleton.show();
            errorLayoutDescription.setText(getResources().getString(R.string.cart_error_description));
            if (connectionHelper.isConnectingToInternet()) {
                getViewCart();
            } else {
                Utils.displayMessage(activity, context, getString(R.string.oops_connect_your_internet));
            }
            if (intValue > 0) {
                String stringBuilder = GlobalData.currencySymbol +
                        " " +
                        intValue;
                amountTxt.setText(stringBuilder);
                walletLayout.setVisibility(View.VISIBLE);
            } else {
                walletLayout.setVisibility(View.INVISIBLE);
            }
        } else {
            dataLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            errorLayoutDescription.setText(getResources().getString(R.string.please_login_and_order_dishes));
        }
        if (ViewCartAdapter.bottomSheetDialogFragment != null) {
            ViewCartAdapter.bottomSheetDialogFragment.dismiss();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        context = context;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (toolbar != null) {
            toolbar.removeView(toolbarLayout);
        }
        unbinder.unbind();
    }

    public void FeedbackDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.feedback);
        EditText editText = dialog.findViewById(R.id.comment);
        dialog.findViewById(R.id.submit).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        System.out.println("CartFragment");
        toolbar = getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);
            dummyImageView.setVisibility(View.VISIBLE);
            return;
        }
        dummyImageView.setVisibility(View.GONE);
    }

    @OnClick({R.id.add_address_btn, R.id.add_address_txt, R.id.proceed_to_pay_btn, R.id.selected_address_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address_btn:
                startActivityForResult(new Intent(getActivity(), SaveDeliveryLocationActivity.class).putExtra("get_address", true), ADDRESS_SELECTION);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                return;
            case R.id.add_address_txt:
                if (!addAddressTxt.getText().toString().equalsIgnoreCase(getResources().getString(R.string.change_address))) {
                    startActivityForResult(new Intent(getActivity(), SetDeliveryLocationActivity.class).putExtra("get_address", true), ADDRESS_SELECTION);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                    return;
                } else if (!addAddressTxt.getText().toString().equalsIgnoreCase(getResources().getString(R.string.add_address))) {
                    startActivityForResult(new Intent(getActivity(), SaveDeliveryLocationActivity.class).putExtra("get_address", true), ADDRESS_SELECTION);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                    return;
                } else {
                    return;
                }
            case R.id.proceed_to_pay_btn:
                if (!connectionHelper.isConnectingToInternet()) {
                    checkoutMap = new HashMap<>();
                    StringBuilder stringBuilder = new StringBuilder();
                    GlobalData.getInstance();
                    stringBuilder.append(GlobalData.selectedAddress.getId());
                    checkoutMap.put("user_address_id", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(customNotes.getText());
                    checkoutMap.put("note", stringBuilder.toString());
                    if (!useWalletChkBox.isChecked()) {
                        checkoutMap.put("wallet", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                    } else {
                        checkoutMap.put("wallet", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    }
                    startActivity(new Intent(context, AccountPaymentActivity.class).putExtra("is_show_wallet", false).putExtra("is_show_cash", true));
                    activity.overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
                    return;
                }
                Utils.displayMessage(activity, context, getString(R.string.oops_connect_your_internet));
                return;
            case R.id.selected_address_btn:
                startActivityForResult(new Intent(getActivity(), SetDeliveryLocationActivity.class).putExtra("get_address", true), ADDRESS_SELECTION);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                return;
            default:
                return;
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        System.out.print("CartFragment");
        if (i == ADDRESS_SELECTION && i2 == -1) {
            System.out.print("CartFragment : Success");
            GlobalData.getInstance();
            if (GlobalData.selectedAddress != null) {
                locationErrorLayout.setVisibility(View.GONE);
                locationInfoLayout.setVisibility(View.VISIBLE);
                GlobalData.getInstance();
                if (GlobalData.selectedAddress != null) {
                    GlobalData.getInstance();
                    if (GlobalData.selectedAddress.getLandmark() != null) {
                        GlobalData.getInstance();
                        if (GlobalData.addressList.getAddresses().size() == 1) {
                            addAddressTxt.setText(getString(R.string.add_address));
                        } else {
                            addAddressTxt.setText(getString(R.string.change_address));
                        }
                    }
                }
                GlobalData.getInstance();
                addressHeader.setText(GlobalData.selectedAddress.getType());
                addressDetail.setText(GlobalData.selectedAddress.getMapAddress());
                addressDeliveryTime.setText(viewCartItemList.get(View.VISIBLE).getProduct().getShop().getEstimatedDeliveryTime().toString() + " Mins");
                return;
            }
            locationErrorLayout.setVisibility(View.VISIBLE);
            locationInfoLayout.setVisibility(View.GONE);
        } else if (i == ADDRESS_SELECTION && i2 == 0) {
            System.out.print("CartFragment : Failure");
        }
    }

    @OnClick(R.id.custom_notes)
    public void onAddCustomNotesClicked() {
        try {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            View frameLayout = getLayoutInflater().inflate(R.layout.custom_note_popup, null);
            dialogBuilder.setView(frameLayout);
            final EditText editText = frameLayout.findViewById(R.id.notes);
            editText.setText(customNotes.getText());
            final AlertDialog alertDialog = dialogBuilder.create();
            frameLayout.findViewById(R.id.custom_note_submit).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    customNotes.setText(editText.getText());
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
