package com.opalfire.foodorder.adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.HotelViewActivity;
import com.opalfire.foodorder.activities.LoginActivity;
import com.opalfire.foodorder.activities.ProductDetailActivity;
import com.opalfire.foodorder.activities.ViewCartActivity;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.fragments.CartChoiceModeFragment;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.Cart;
import com.opalfire.foodorder.models.ClearCart;
import com.opalfire.foodorder.models.Product;
import com.opalfire.foodorder.models.Shop;
import com.opalfire.foodorder.utils.Utils;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsAdapter extends SectionedRecyclerViewAdapter<ViewHolder> {
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    public static Activity activity = null;
    public static AddCart addCart = null;
    public static ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    public static CartChoiceModeFragment bottomSheetDialogFragment = null;
    public static Context context = null;
    public static Shop currentShop = new Shop();
    public static boolean isShopIsChanged = true;
    public static int itemCount = 0;
    public static int itemQuantity = 0;
    public static int lastPosition = -1;
    public static int priceAmount;
    public static Product product;
    public static List<Product> productList;
    public static Animation slide_down;
    public static Animation slide_up;
    List<Product> list = new ArrayList();
    private LayoutInflater inflater;

    public ProductsAdapter(Context context, Activity activity, List<Product> list) {
        context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        activity = activity;
    }

    public static void addCart(HashMap<String, String> hashMap) {
        apiInterface.postAddCart(hashMap).enqueue(new C13858());
    }

    public int getItemCount(int i) {
        return 1;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case -2:
                return new ViewHolder(this.inflater.inflate(R.layout.product_header, viewGroup, false), true);
            case -1:
                return new ViewHolder(this.inflater.inflate(R.layout.product_search_list_item, viewGroup, false), false);
            default:
                return new ViewHolder(this.inflater.inflate(R.layout.product_search_list_item, viewGroup, false), false);
        }
    }

    public int getSectionCount() {
        return this.list.size();
    }

    public void onBindHeaderViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.headerTxt.setText(((Product) this.list.get(i)).getShop().getName());
        viewHolder.headerTxt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                System.out.println(((Product) ProductsAdapter.this.list.get(i)).getShop().getName());
            }
        });
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i, int i2, int i3) {
        product = (Product) this.list.get(i);
        viewHolder.cardTextValueTicker.setCharacterList(NUMBER_LIST);
        viewHolder.dishNameTxt.setText(product.getName());
        addCart = GlobalData.addCart;
        if (product.getCart().isEmpty() == 0) {
            GlobalData.selectedShop = HotelViewActivity.shops;
            viewHolder.cardAddTextLayout.setVisibility(View.GONE);
            viewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
            i2 = Integer.valueOf(View.VISIBLE);
            for (Cart quantity : product.getCart()) {
                i2 = Integer.valueOf(i2.intValue() + quantity.getQuantity().intValue());
            }
            viewHolder.cardTextValueTicker.setText(String.valueOf(i2));
            viewHolder.cardTextValue.setText(String.valueOf(i2));
        } else {
            viewHolder.cardAddTextLayout.setVisibility(View.VISIBLE);
            viewHolder.cardAddDetailLayout.setVisibility(View.GONE);
            viewHolder.cardTextValueTicker.setText(String.valueOf(1));
            viewHolder.cardTextValue.setText(String.valueOf(1));
        }
        if (product.getAddons() == 0 || product.getAddons().size() == 0) {
            viewHolder.customizableTxt.setVisibility(View.GONE);
            viewHolder.addOnsIconImg.setVisibility(View.GONE);
        } else {
            viewHolder.customizableTxt.setVisibility(View.VISIBLE);
            viewHolder.addOnsIconImg.setVisibility(View.VISIBLE);
        }
        i2 = viewHolder.priceTxt;
        i3 = new StringBuilder();
        i3.append(product.getPrices().getCurrency());
        i3.append(" ");
        i3.append(product.getPrices().getPrice());
        i2.setText(i3.toString());
        if (product.getFoodType().equalsIgnoreCase("veg") == 0) {
            viewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_nonveg));
        } else {
            viewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_veg));
        }
        viewHolder.cardAddBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.e("access_token2", GlobalData.accessToken);
                ProductsAdapter.product = (Product) ProductsAdapter.this.list.get(i);
                ProductsAdapter.currentShop = ((Product) ProductsAdapter.this.list.get(i)).getShop();
                int i = 0;
                if (ProductsAdapter.product.getAddons() == null || ProductsAdapter.product.getAddons().isEmpty() != null) {
                    view = null;
                    while (i < ProductsAdapter.addCart.getProductList().size()) {
                        if (((Cart) ProductsAdapter.addCart.getProductList().get(i)).getProductId().equals(ProductsAdapter.product.getId())) {
                            view = ((Cart) ProductsAdapter.addCart.getProductList().get(i)).getId().intValue();
                        }
                        i++;
                    }
                    i = Integer.parseInt(viewHolder.cardTextValue.getText().toString()) + 1;
                    TextView access$100 = viewHolder.cardTextValue;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(i);
                    access$100.setText(stringBuilder.toString());
                    TickerView tickerView = viewHolder.cardTextValueTicker;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(i);
                    tickerView.setText(stringBuilder.toString());
                    HashMap hashMap = new HashMap();
                    hashMap.put("product_id", ProductsAdapter.product.getId().toString());
                    hashMap.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                    hashMap.put("cart_id", String.valueOf(view));
                    Log.e("AddCart_add", hashMap.toString());
                    ProductsAdapter.addCart(hashMap);
                    return;
                }
                GlobalData.isSelectedProduct = ProductsAdapter.product;
                ProductsAdapter.bottomSheetDialogFragment = new CartChoiceModeFragment();
                ProductsAdapter.bottomSheetDialogFragment.show(((AppCompatActivity) ProductsAdapter.context).getSupportFragmentManager(), ProductsAdapter.bottomSheetDialogFragment.getTag());
                CartChoiceModeFragment.isViewcart = false;
                CartChoiceModeFragment.isSearch = true;
            }
        });
        viewHolder.cardMinusBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                int i;
                ProductsAdapter.product = (Product) ProductsAdapter.this.list.get(i);
                ProductsAdapter.currentShop = ((Product) ProductsAdapter.this.list.get(i)).getShop();
                int i2 = 0;
                for (i = 0; i < ProductsAdapter.addCart.getProductList().size(); i++) {
                    if (((Cart) ProductsAdapter.addCart.getProductList().get(i)).getProductId().equals(ProductsAdapter.product.getId())) {
                        i2 = ((Cart) ProductsAdapter.addCart.getProductList().get(i)).getId().intValue();
                    }
                }
                if (viewHolder.cardTextValue.getText().toString().equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                    i = Integer.parseInt(viewHolder.cardTextValue.getText().toString()) - 1;
                    TextView access$100 = viewHolder.cardTextValue;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(i);
                    access$100.setText(stringBuilder.toString());
                    TickerView tickerView = viewHolder.cardTextValueTicker;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(i);
                    tickerView.setText(stringBuilder.toString());
                    viewHolder.cardAddDetailLayout.setVisibility(View.GONE);
                    if (ProductsAdapter.addCart.getProductList().size() == 0 && ProductsAdapter.addCart != null) {
                        HotelViewActivity.viewCartLayout.setVisibility(View.GONE);
                    }
                    viewHolder.cardAddTextLayout.setVisibility(null);
                    view = new HashMap();
                    view.put("product_id", ProductsAdapter.product.getId().toString());
                    view.put(Param.QUANTITY, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    view.put("cart_id", String.valueOf(i2));
                    Log.e("AddCart_Minus", view.toString());
                    ProductsAdapter.addCart(view);
                } else if (ProductsAdapter.product.getCart().size() == 1) {
                    view = Integer.parseInt(viewHolder.cardTextValue.getText().toString()) - 1;
                    TextView access$1002 = viewHolder.cardTextValue;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(view);
                    access$1002.setText(stringBuilder2.toString());
                    TickerView tickerView2 = viewHolder.cardTextValueTicker;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(view);
                    tickerView2.setText(stringBuilder2.toString());
                    view = new HashMap();
                    view.put("product_id", ProductsAdapter.product.getId().toString());
                    view.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                    view.put("cart_id", String.valueOf(i2));
                    Log.e("AddCart_Minus", view.toString());
                    ProductsAdapter.addCart(view);
                } else {
                    view = new Builder(ProductsAdapter.context);
                    view.setTitle(ProductsAdapter.context.getResources().getString(R.string.remove_item_from_cart)).setMessage(ProductsAdapter.context.getResources().getString(R.string.remove_item_from_cart_description)).setPositiveButton(ProductsAdapter.context.getResources().getString(R.string.yes), new C08172()).setNegativeButton(ProductsAdapter.context.getResources().getString(R.string.no), new C08161());
                    view = view.create();
                    view.show();
                    Button button = view.getButton(-2);
                    button.setTextColor(ContextCompat.getColor(ProductsAdapter.context, R.color.theme));
                    button.setTypeface(button.getTypeface(), 1);
                    view = view.getButton(-1);
                    view.setTextColor(ContextCompat.getColor(ProductsAdapter.context, R.color.theme));
                    view.setTypeface(view.getTypeface(), 1);
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.ProductsAdapter$3$1 */
            class C08161 implements DialogInterface.OnClickListener {
                C08161() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.ProductsAdapter$3$2 */
            class C08172 implements DialogInterface.OnClickListener {
                C08172() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    ProductsAdapter.context.startActivity(new Intent(ProductsAdapter.context, ViewCartActivity.class));
                    ProductsAdapter.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                }
            }
        });
        viewHolder.viewFullMenu.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductsAdapter.context.startActivity(new Intent(ProductsAdapter.context, HotelViewActivity.class).putExtra("position", i));
                GlobalData.selectedShop = ((Product) ProductsAdapter.this.list.get(i)).getShop();
                ProductsAdapter.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            }
        });
        viewHolder.rootLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GlobalData.isSelectedProduct = (Product) ProductsAdapter.this.list.get(i);
                ProductsAdapter.context.startActivity(new Intent(ProductsAdapter.context, ProductDetailActivity.class));
                ProductsAdapter.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            }
        });
        viewHolder.cardAddTextLayout.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                ProductsAdapter.product = (Product) ProductsAdapter.this.list.get(i);
                if (GlobalData.profileModel == null) {
                    ProductsAdapter.activity.startActivity(new Intent(ProductsAdapter.context, LoginActivity.class).setFlags(268468224));
                    ProductsAdapter.activity.overridePendingTransition(R.anim.slide_in_left, R.anim.anim_nothing);
                    ProductsAdapter.activity.finish();
                    Toast.makeText(ProductsAdapter.context, ProductsAdapter.context.getResources().getString(R.string.please_login_and_order_dishes), 0).show();
                } else if (Utils.isShopChanged(ProductsAdapter.product.getShopId().intValue()) != null) {
                    view = String.format(ProductsAdapter.activity.getResources().getString(R.string.reorder_confirm_message), new Object[]{ProductsAdapter.product.getShop().getName(), ((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShop().getName()});
                    Builder builder = new Builder(ProductsAdapter.context);
                    builder.setTitle(ProductsAdapter.context.getResources().getString(R.string.replace_cart_item)).setMessage(view).setPositiveButton(ProductsAdapter.context.getResources().getString(R.string.yes), new C08222()).setNegativeButton(ProductsAdapter.context.getResources().getString(R.string.no), new C08211());
                    view = builder.create();
                    view.show();
                    Button button = view.getButton(-2);
                    button.setTextColor(ContextCompat.getColor(ProductsAdapter.context, R.color.theme));
                    button.setTypeface(button.getTypeface(), 1);
                    view = view.getButton(-1);
                    view.setTextColor(ContextCompat.getColor(ProductsAdapter.context, R.color.theme));
                    view.setTypeface(view.getTypeface(), 1);
                } else {
                    ProductsAdapter.currentShop = ((Product) ProductsAdapter.this.list.get(i)).getShop();
                    if (ProductsAdapter.product.getAddons() == null || ProductsAdapter.product.getAddons().size() == null) {
                        viewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
                        viewHolder.cardAddTextLayout.setVisibility(View.GONE);
                        viewHolder.cardTextValue.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        viewHolder.cardTextValueTicker.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        view = new HashMap();
                        view.put("product_id", ProductsAdapter.product.getId().toString());
                        view.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                        Log.e("AddCart_Text", view.toString());
                        ProductsAdapter.addCart(view);
                        return;
                    }
                    GlobalData.isSelectedProduct = ProductsAdapter.product;
                    ProductsAdapter.context.startActivity(new Intent(ProductsAdapter.context, ProductDetailActivity.class));
                    ProductsAdapter.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.ProductsAdapter$6$1 */
            class C08211 implements DialogInterface.OnClickListener {
                C08211() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.ProductsAdapter$6$2 */
            class C08222 implements DialogInterface.OnClickListener {
                C08222() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    ProductsAdapter.this.clearCart();
                    ProductsAdapter.isShopIsChanged = false;
                    if (ProductsAdapter.product.getAddons() == 0 || ProductsAdapter.product.getAddons().size() == 0) {
                        GlobalData.selectedShop = ProductsAdapter.product.getShop();
                        ProductsAdapter.product = (Product) ProductsAdapter.this.list.get(i);
                        viewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
                        viewHolder.cardAddTextLayout.setVisibility(View.GONE);
                        viewHolder.cardTextValue.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        viewHolder.cardTextValueTicker.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        dialogInterface = new HashMap();
                        dialogInterface.put("product_id", ProductsAdapter.product.getId().toString());
                        dialogInterface.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                        Log.e("AddCart_Text", dialogInterface.toString());
                        ProductsAdapter.addCart(dialogInterface);
                        return;
                    }
                    GlobalData.isSelectedProduct = ProductsAdapter.product;
                    ProductsAdapter.context.startActivity(new Intent(ProductsAdapter.context, ProductDetailActivity.class));
                    ProductsAdapter.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                }
            }
        });
    }

    private void clearCart() {
        apiInterface.clearCart().enqueue(new C13847());
    }

    /* renamed from: com.entriver.orderaround.adapter.ProductsAdapter$8 */
    static class C13858 implements Callback<AddCart> {
        C13858() {
        }

        public void onFailure(Call<AddCart> call, Throwable th) {
        }

        public void onResponse(Call<AddCart> call, Response<AddCart> response) {
            GlobalData.selectedShop = ProductsAdapter.currentShop;
            if (response != null && response.isSuccessful() == null && response.errorBody() != null) {
                try {
                    Toast.makeText(ProductsAdapter.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<AddCart> response2) {
                    Toast.makeText(ProductsAdapter.context, response2.getMessage(), 1).show();
                }
            } else if (response2.isSuccessful() != null) {
                GlobalData.addCartShopId = GlobalData.selectedShop.getId().intValue();
                ProductsAdapter.addCart = (AddCart) response2.body();
                GlobalData.addCart = (AddCart) response2.body();
                call = null;
                ProductsAdapter.priceAmount = 0;
                ProductsAdapter.itemQuantity = 0;
                ProductsAdapter.itemCount = 0;
                ProductsAdapter.itemCount = ProductsAdapter.addCart.getProductList().size();
                while (call < ProductsAdapter.itemCount) {
                    ProductsAdapter.itemQuantity += ((Cart) ProductsAdapter.addCart.getProductList().get(call)).getQuantity().intValue();
                    if (((Cart) ProductsAdapter.addCart.getProductList().get(call)).getProduct().getPrices().getPrice() != null) {
                        ProductsAdapter.priceAmount += ((Cart) ProductsAdapter.addCart.getProductList().get(call)).getQuantity().intValue() * ((Cart) ProductsAdapter.addCart.getProductList().get(call)).getProduct().getPrices().getPrice().intValue();
                    }
                    call++;
                }
                GlobalData.notificationCount = ProductsAdapter.itemQuantity;
                HomeActivity.updateNotificationCount(ProductsAdapter.context, GlobalData.notificationCount);
            }
        }
    }

    /* renamed from: com.entriver.orderaround.adapter.ProductsAdapter$7 */
    class C13847 implements Callback<ClearCart> {
        C13847() {
        }

        public void onResponse(Call<ClearCart> call, Response<ClearCart> response) {
            if (response != null && response.isSuccessful() == null && response.errorBody() != null) {
                try {
                    Toast.makeText(ProductsAdapter.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<ClearCart> response2) {
                    Toast.makeText(ProductsAdapter.context, response2.getMessage(), 1).show();
                }
            } else if (response2.isSuccessful() != null) {
                GlobalData.selectedShop = HotelViewActivity.shops;
                GlobalData.addCart.getProductList().clear();
                GlobalData.notificationCount = null;
            }
        }

        public void onFailure(Call<ClearCart> call, Throwable th) {
            Toast.makeText(ProductsAdapter.context, "Something went wrong", 0).show();
            GlobalData.addCartShopId = GlobalData.selectedShop.getId().intValue();
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        RelativeLayout cardAddDetailLayout;
        RelativeLayout cardAddTextLayout;
        RelativeLayout cardInfoLayout;
        TickerView cardTextValueTicker;
        TextView headerTxt;
        RelativeLayout rootLayout;
        private ImageView addOnsIconImg;
        private ImageView animationLineCartAdd;
        private ImageView cardAddBtn;
        private TextView cardAddInfoText;
        private TextView cardAddOutOfStock;
        private ImageView cardMinusBtn;
        private TextView cardTextValue;
        private TextView customizableTxt;
        private ImageView dishImg;
        private TextView dishNameTxt;
        private ImageView foodImageType;
        private TextView priceTxt;
        private TextView viewFullMenu;

        public ViewHolder(View view, boolean z) {
            super(view);
            if (z) {
                this.headerTxt = (TextView) view.findViewById(R.id.product_header);
                return;
            }
            this.dishImg = (ImageView) view.findViewById(R.id.dishImg);
            this.foodImageType = (ImageView) view.findViewById(R.id.food_type_image);
            this.animationLineCartAdd = (ImageView) view.findViewById(R.id.animation_line_cart_add);
            this.dishNameTxt = (TextView) view.findViewById(R.id.dish_name_text);
            this.priceTxt = (TextView) view.findViewById(R.id.price_text);
            this.addOnsIconImg = (ImageView) view.findViewById(R.id.add_ons_icon);
            this.customizableTxt = (TextView) view.findViewById(R.id.customizable_txt);
            this.cardAddDetailLayout = (RelativeLayout) view.findViewById(R.id.add_card_layout);
            this.rootLayout = (RelativeLayout) view.findViewById(R.id.root_layout);
            this.cardAddTextLayout = (RelativeLayout) view.findViewById(R.id.add_card_text_layout);
            this.cardInfoLayout = (RelativeLayout) view.findViewById(R.id.add_card_info_layout);
            this.cardAddInfoText = (TextView) view.findViewById(R.id.avialablity_time);
            this.cardAddOutOfStock = (TextView) view.findViewById(R.id.out_of_stock);
            this.cardAddBtn = (ImageView) view.findViewById(R.id.card_add_btn);
            this.cardMinusBtn = (ImageView) view.findViewById(R.id.card_minus_btn);
            this.cardTextValue = (TextView) view.findViewById(R.id.card_value);
            this.viewFullMenu = (TextView) view.findViewById(R.id.view_full_menu);
            this.cardTextValueTicker = (TickerView) view.findViewById(R.id.card_value_ticker);
            ProductsAdapter.slide_down = AnimationUtils.loadAnimation(ProductsAdapter.context, R.anim.slide_down);
            ProductsAdapter.slide_up = AnimationUtils.loadAnimation(ProductsAdapter.context, R.anim.slide_up);
        }
    }
}
