package com.opalfire.foodorder.adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
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
import com.opalfire.foodorder.models.CartAddon;
import com.opalfire.foodorder.models.Category;
import com.opalfire.foodorder.models.ClearCart;
import com.opalfire.foodorder.models.FeaturedImage;
import com.opalfire.foodorder.models.Product;
import com.opalfire.foodorder.models.ShopDetail;
import com.opalfire.foodorder.utils.Utils;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelCatagoeryAdapter extends SectionedRecyclerViewAdapter<ViewHolder> {
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    public static Runnable action;
    public static AddCart addCart;
    public static ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    public static AnimatedVectorDrawableCompat avdProgress;
    public static CartChoiceModeFragment bottomSheetDialogFragment;
    public static Context context;
    public static boolean dataResponse = false;
    public static Dialog dialog;
    public static int itemCount;
    public static int itemQuantity;
    public static int priceAmount;
    public static Product product;
    public static Animation slide_down;
    public static Animation slide_up;
    Activity activity;
    int lastPosition = -1;
    List<Product> productList;
    private LayoutInflater inflater;
    private List<Category> list = new ArrayList();

    public HotelCatagoeryAdapter(Context context, Activity activity, List<Category> list) {
        context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.activity = activity;
        if (GlobalData.addCart != null && GlobalData.addCart.getProductList().size() != null) {
            addCart = GlobalData.addCart;
        }
    }

    public static void addCart(HashMap<String, String> hashMap) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.empty_dialog);
        dialog.setCancelable(false);
        dataResponse = false;
        dialog.show();
        apiInterface.postAddCart(hashMap).enqueue(new C13807());
    }

    public static void getCategories(HashMap<String, String> hashMap) {
        apiInterface.getCategories(hashMap).enqueue(new C13818());
    }

    private static void setViewcartBottomLayout(AddCart addCart) {
        priceAmount = 0;
        itemQuantity = 0;
        itemCount = 0;
        itemCount = addCart.getProductList().size();
        int i = 0;
        while (i < itemCount) {
            itemQuantity += ((Cart) addCart.getProductList().get(i)).getQuantity().intValue();
            if (((Cart) addCart.getProductList().get(i)).getProduct().getPrices().getPrice() != null) {
                priceAmount += ((Cart) addCart.getProductList().get(i)).getQuantity().intValue() * ((Cart) addCart.getProductList().get(i)).getProduct().getPrices().getPrice().intValue();
            }
            if (!(((Cart) addCart.getProductList().get(i)).getCartAddons() == null || ((Cart) addCart.getProductList().get(i)).getCartAddons().isEmpty())) {
                for (int i2 = 0; i2 < ((Cart) addCart.getProductList().get(i)).getCartAddons().size(); i2++) {
                    priceAmount += ((Cart) addCart.getProductList().get(i)).getQuantity().intValue() * (((CartAddon) ((Cart) addCart.getProductList().get(i)).getCartAddons().get(i2)).getQuantity().intValue() * ((CartAddon) ((Cart) addCart.getProductList().get(i)).getCartAddons().get(i2)).getAddonProduct().getPrice().intValue());
                }
            }
            i++;
        }
        GlobalData.notificationCount = itemQuantity;
        if (addCart.getProductList().isEmpty()) {
            HotelViewActivity.viewCartLayout.setVisibility(View.GONE);
            HotelViewActivity.viewCartLayout.startAnimation(slide_down);
            return;
        }
        StringBuilder stringBuilder;
        if (Objects.equals(HotelViewActivity.shops.getId(), ((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShopId())) {
            HotelViewActivity.viewCartShopName.setVisibility(View.GONE);
        } else {
            HotelViewActivity.viewCartShopName.setVisibility(View.VISIBLE);
            TextView textView = HotelViewActivity.viewCartShopName;
            stringBuilder = new StringBuilder();
            stringBuilder.append("From : ");
            stringBuilder.append(((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShop().getName());
            textView.setText(stringBuilder.toString());
        }
        addCart = ((Cart) addCart.getProductList().get(0)).getProduct().getPrices().getCurrency();
        String quantityString = context.getResources().getQuantityString(R.plurals.item, itemQuantity, new Object[]{Integer.valueOf(itemQuantity)});
        stringBuilder = new StringBuilder();
        stringBuilder.append(quantityString);
        stringBuilder.append(" | ");
        stringBuilder.append(addCart);
        stringBuilder.append(String.valueOf(priceAmount));
        addCart = stringBuilder.toString();
        Log.d("itemMessage", addCart);
        HotelViewActivity.itemText.setText(addCart);
        if (HotelViewActivity.viewCartLayout.getVisibility() == 8) {
            HotelViewActivity.viewCartLayout.setVisibility(View.VISIBLE);
            HotelViewActivity.viewCartLayout.startAnimation(slide_up);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case -2:
                return new ViewHolder(this.inflater.inflate(R.layout.category_header, viewGroup, false), true);
            case -1:
                return new ViewHolder(this.inflater.inflate(R.layout.accompainment_list_item, viewGroup, false), false);
            default:
                return new ViewHolder(this.inflater.inflate(R.layout.accompainment_list_item, viewGroup, false), false);
        }
    }

    public int getSectionCount() {
        return this.list.size();
    }

    public int getItemCount(int i) {
        return ((Category) this.list.get(i)).getProducts().size();
    }

    public void onBindHeaderViewHolder(ViewHolder viewHolder, final int i) {
        if (((Category) this.list.get(i)).getName().equalsIgnoreCase(context.getResources().getString(R.string.featured_products))) {
            viewHolder.featureProductsTitle.setVisibility(View.VISIBLE);
            viewHolder.categoryHeaderLayout.setVisibility(View.GONE);
        } else {
            viewHolder.featureProductsTitle.setVisibility(View.GONE);
            viewHolder.categoryHeaderLayout.setVisibility(View.VISIBLE);
            viewHolder.headerTxt.setText(((Category) this.list.get(i)).getName());
        }
        viewHolder.headerTxt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                System.out.println(((Category) HotelCatagoeryAdapter.this.list.get(i)).getName());
            }
        });
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i, final int i2, int i3) {
        Category category = (Category) this.list.get(i);
        product = (Product) ((Category) this.list.get(i)).getProducts().get(i2);
        this.productList = ((Category) this.list.get(i)).getProducts();
        viewHolder.cardTextValueTicker.setCharacterList(NUMBER_LIST);
        viewHolder.dishNameTxt.setText(product.getName());
        viewHolder.cardTextValueTicker.setVisibility(View.GONE);
        viewHolder.cardTextValue.setVisibility(View.VISIBLE);
        if (category.getName().equalsIgnoreCase(context.getResources().getString(R.string.featured_products)) != 0) {
            viewHolder.featuredImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(((FeaturedImage) product.getFeaturedImages().get(0)).getUrl()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.ic_banner).error((int) R.drawable.ic_banner)).into(viewHolder.featuredImage);
        } else {
            viewHolder.featuredImage.setVisibility(View.GONE);
        }
        if (product.getCart() == 0 || product.getCart().size() == 0) {
            viewHolder.cardAddTextLayout.setVisibility(View.VISIBLE);
            viewHolder.cardAddDetailLayout.setVisibility(View.GONE);
            viewHolder.cardTextValueTicker.setText(String.valueOf(1));
            viewHolder.cardTextValue.setText(String.valueOf(1));
        } else {
            GlobalData.selectedShop = HotelViewActivity.shops;
            viewHolder.cardAddTextLayout.setVisibility(View.GONE);
            viewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
            i3 = Integer.valueOf(View.VISIBLE);
            for (Cart quantity : product.getCart()) {
                i3 = Integer.valueOf(i3.intValue() + quantity.getQuantity().intValue());
            }
            if (!viewHolder.cardTextValue.getText().toString().equalsIgnoreCase(String.valueOf(i3))) {
                viewHolder.cardTextValueTicker.setText(String.valueOf(i3));
                viewHolder.cardTextValue.setText(String.valueOf(i3));
            }
        }
        if (product.getAddons() == 0 || product.getAddons().size() == 0) {
            viewHolder.customizableTxt.setVisibility(View.GONE);
            viewHolder.addOnsIconImg.setVisibility(View.GONE);
        } else {
            viewHolder.customizableTxt.setVisibility(View.VISIBLE);
            viewHolder.addOnsIconImg.setVisibility(View.VISIBLE);
        }
        viewHolder.rootLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GlobalData.isSelectedProduct = (Product) ((Category) HotelCatagoeryAdapter.this.list.get(i)).getProducts().get(i2);
                HotelCatagoeryAdapter.context.startActivity(new Intent(HotelCatagoeryAdapter.context, ProductDetailActivity.class));
                HotelCatagoeryAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            }
        });
        if (product.getPrices().getCurrency() != 0) {
            i3 = viewHolder.priceTxt;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(product.getPrices().getCurrency());
            stringBuilder.append(" ");
            stringBuilder.append(product.getPrices().getPrice());
            i3.setText(stringBuilder.toString());
        }
        if (product.getFoodType().equalsIgnoreCase("veg") == 0) {
            viewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_nonveg));
        } else {
            viewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_veg));
        }
        viewHolder.cardAddBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                HotelCatagoeryAdapter.product = (Product) ((Category) HotelCatagoeryAdapter.this.list.get(i)).getProducts().get(i2);
                if (Utils.isShopChanged(HotelViewActivity.shops.getId().intValue()) == null) {
                    HotelCatagoeryAdapter.avdProgress = AnimatedVectorDrawableCompat.create(HotelCatagoeryAdapter.context, R.drawable.add_cart_avd_line);
                    viewHolder.animationLineCartAdd.setBackground(HotelCatagoeryAdapter.avdProgress);
                    HotelCatagoeryAdapter.avdProgress.start();
                    HotelCatagoeryAdapter.action = new C07931();
                    viewHolder.animationLineCartAdd.postDelayed(HotelCatagoeryAdapter.action, 3000);
                    Log.e("access_token2", GlobalData.accessToken);
                    int i = 0;
                    if (HotelCatagoeryAdapter.product.getAddons() == null || HotelCatagoeryAdapter.product.getAddons().isEmpty() != null) {
                        viewHolder.cardTextValueTicker.setVisibility(View.VISIBLE);
                        viewHolder.cardTextValue.setVisibility(View.GONE);
                        viewHolder.animationLineCartAdd.setVisibility(View.VISIBLE);
                        view = null;
                        while (i < HotelCatagoeryAdapter.addCart.getProductList().size()) {
                            if (((Cart) HotelCatagoeryAdapter.addCart.getProductList().get(i)).getProductId().equals(HotelCatagoeryAdapter.product.getId())) {
                                view = ((Cart) HotelCatagoeryAdapter.addCart.getProductList().get(i)).getId().intValue();
                            }
                            i++;
                        }
                        i = Integer.parseInt(viewHolder.cardTextValue.getText().toString()) + 1;
                        TextView access$200 = viewHolder.cardTextValue;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(i);
                        access$200.setText(stringBuilder.toString());
                        TickerView tickerView = viewHolder.cardTextValueTicker;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(i);
                        tickerView.setText(stringBuilder.toString());
                        HashMap hashMap = new HashMap();
                        hashMap.put("product_id", HotelCatagoeryAdapter.product.getId().toString());
                        hashMap.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                        hashMap.put("cart_id", String.valueOf(view));
                        Log.e("AddCart_add", hashMap.toString());
                        HotelCatagoeryAdapter.addCart(hashMap);
                        return;
                    }
                    GlobalData.isSelectedProduct = HotelCatagoeryAdapter.product;
                    HotelCatagoeryAdapter.bottomSheetDialogFragment = new CartChoiceModeFragment();
                    HotelCatagoeryAdapter.bottomSheetDialogFragment.show(((AppCompatActivity) HotelCatagoeryAdapter.context).getSupportFragmentManager(), HotelCatagoeryAdapter.bottomSheetDialogFragment.getTag());
                    CartChoiceModeFragment.isViewcart = false;
                    CartChoiceModeFragment.isSearch = false;
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$3$1 */
            class C07931 implements Runnable {
                C07931() {
                }

                public void run() {
                    if (!HotelCatagoeryAdapter.dataResponse) {
                        HotelCatagoeryAdapter.avdProgress.start();
                        viewHolder.animationLineCartAdd.postDelayed(HotelCatagoeryAdapter.action, 3000);
                    }
                }
            }
        });
        viewHolder.cardAddDetailLayout.setClickable(false);
        viewHolder.cardAddDetailLayout.setEnabled(false);
        viewHolder.cardMinusBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                int i = 0;
                viewHolder.animationLineCartAdd.setVisibility(View.VISIBLE);
                viewHolder.cardTextValueTicker.setVisibility(View.VISIBLE);
                viewHolder.cardTextValue.setVisibility(View.GONE);
                HotelCatagoeryAdapter.avdProgress = AnimatedVectorDrawableCompat.create(HotelCatagoeryAdapter.context, R.drawable.add_cart_avd_line);
                viewHolder.animationLineCartAdd.setBackground(HotelCatagoeryAdapter.avdProgress);
                HotelCatagoeryAdapter.avdProgress.start();
                HotelCatagoeryAdapter.action = new C07951();
                viewHolder.animationLineCartAdd.postDelayed(HotelCatagoeryAdapter.action, 3000);
                HotelCatagoeryAdapter.product = (Product) ((Category) HotelCatagoeryAdapter.this.list.get(i)).getProducts().get(i2);
                int i2 = 0;
                for (view = null; view < HotelCatagoeryAdapter.addCart.getProductList().size(); view++) {
                    if (((Cart) HotelCatagoeryAdapter.addCart.getProductList().get(view)).getProductId().equals(HotelCatagoeryAdapter.product.getId())) {
                        i2 = ((Cart) HotelCatagoeryAdapter.addCart.getProductList().get(view)).getId().intValue();
                    }
                }
                if (viewHolder.cardTextValue.getText().toString().equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_YES) != null) {
                    view = Integer.parseInt(viewHolder.cardTextValue.getText().toString()) - 1;
                    TextView access$200 = viewHolder.cardTextValue;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    access$200.setText(stringBuilder.toString());
                    TickerView tickerView = viewHolder.cardTextValueTicker;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    tickerView.setText(stringBuilder.toString());
                    viewHolder.cardAddDetailLayout.setVisibility(View.GONE);
                    if (HotelCatagoeryAdapter.addCart.getProductList().size() == null && HotelCatagoeryAdapter.addCart != null) {
                        HotelViewActivity.viewCartLayout.setVisibility(View.GONE);
                    }
                    viewHolder.cardAddTextLayout.setVisibility(View.VISIBLE);
                    view = new HashMap();
                    view.put("product_id", HotelCatagoeryAdapter.product.getId().toString());
                    view.put(Param.QUANTITY, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    view.put("cart_id", String.valueOf(i2));
                    Log.e("AddCart_Minus", view.toString());
                    HotelCatagoeryAdapter.addCart(view);
                } else if (HotelCatagoeryAdapter.product.getCart().size() == 1) {
                    view = Integer.parseInt(viewHolder.cardTextValue.getText().toString()) - 1;
                    TextView access$2002 = viewHolder.cardTextValue;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(view);
                    access$2002.setText(stringBuilder2.toString());
                    TickerView tickerView2 = viewHolder.cardTextValueTicker;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(view);
                    tickerView2.setText(stringBuilder2.toString());
                    view = new HashMap();
                    view.put("product_id", HotelCatagoeryAdapter.product.getId().toString());
                    view.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                    view.put("cart_id", String.valueOf(i2));
                    List cartAddons = ((Cart) HotelCatagoeryAdapter.product.getCart().get(0)).getCartAddons();
                    while (i < cartAddons.size()) {
                        CartAddon cartAddon = (CartAddon) cartAddons.get(i);
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("product_addons[");
                        stringBuilder2.append(i);
                        stringBuilder2.append("]");
                        view.put(stringBuilder2.toString(), cartAddon.getAddonProduct().getId().toString());
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("addons_qty[");
                        stringBuilder2.append(i);
                        stringBuilder2.append("]");
                        view.put(stringBuilder2.toString(), cartAddon.getQuantity().toString());
                        i++;
                    }
                    Log.e("AddCart_Minus", view.toString());
                    HotelCatagoeryAdapter.addCart(view);
                } else {
                    view = new Builder(HotelCatagoeryAdapter.context);
                    view.setTitle(HotelCatagoeryAdapter.context.getResources().getString(R.string.remove_item_from_cart)).setMessage(HotelCatagoeryAdapter.context.getResources().getString(R.string.remove_item_from_cart_description)).setPositiveButton(HotelCatagoeryAdapter.context.getResources().getString(R.string.yes), new C07973()).setNegativeButton(HotelCatagoeryAdapter.context.getResources().getString(R.string.no), new C07962());
                    view = view.create();
                    view.show();
                    Button button = view.getButton(-2);
                    button.setTextColor(ContextCompat.getColor(HotelCatagoeryAdapter.context, R.color.theme));
                    button.setTypeface(button.getTypeface(), 1);
                    view = view.getButton(-1);
                    view.setTextColor(ContextCompat.getColor(HotelCatagoeryAdapter.context, R.color.theme));
                    view.setTypeface(view.getTypeface(), 1);
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$4$1 */
            class C07951 implements Runnable {
                C07951() {
                }

                public void run() {
                    if (!HotelCatagoeryAdapter.dataResponse) {
                        HotelCatagoeryAdapter.avdProgress.start();
                        viewHolder.animationLineCartAdd.postDelayed(HotelCatagoeryAdapter.action, 3000);
                    }
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$4$2 */
            class C07962 implements DialogInterface.OnClickListener {
                C07962() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$4$3 */
            class C07973 implements DialogInterface.OnClickListener {
                C07973() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    HotelCatagoeryAdapter.context.startActivity(new Intent(HotelCatagoeryAdapter.context, ViewCartActivity.class));
                    HotelCatagoeryAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                }
            }
        });
        viewHolder.cardAddTextLayout.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                HotelCatagoeryAdapter.product = (Product) ((Category) HotelCatagoeryAdapter.this.list.get(i)).getProducts().get(i2);
                if (GlobalData.profileModel == null) {
                    HotelCatagoeryAdapter.this.activity.startActivity(new Intent(HotelCatagoeryAdapter.context, LoginActivity.class).setFlags(268468224));
                    HotelCatagoeryAdapter.this.activity.overridePendingTransition(R.anim.slide_in_left, R.anim.anim_nothing);
                    HotelCatagoeryAdapter.this.activity.finish();
                    Toast.makeText(HotelCatagoeryAdapter.context, HotelCatagoeryAdapter.context.getResources().getString(R.string.please_login_and_order_dishes), 0).show();
                } else if (Utils.isShopChanged(HotelViewActivity.shops.getId().intValue()) == null) {
                    viewHolder.animationLineCartAdd.setVisibility(View.VISIBLE);
                    HotelCatagoeryAdapter.avdProgress = AnimatedVectorDrawableCompat.create(HotelCatagoeryAdapter.context, R.drawable.add_cart_avd_line);
                    viewHolder.animationLineCartAdd.setBackground(HotelCatagoeryAdapter.avdProgress);
                    HotelCatagoeryAdapter.avdProgress.start();
                    HotelCatagoeryAdapter.action = new C07991();
                    viewHolder.animationLineCartAdd.postDelayed(HotelCatagoeryAdapter.action, 3000);
                    if (HotelCatagoeryAdapter.product.getAddons() == null || HotelCatagoeryAdapter.product.getAddons().size() == null) {
                        viewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
                        viewHolder.cardAddTextLayout.setVisibility(View.GONE);
                        viewHolder.cardTextValue.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        viewHolder.cardTextValueTicker.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        view = new HashMap();
                        view.put("product_id", HotelCatagoeryAdapter.product.getId().toString());
                        view.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                        Log.e("AddCart_Text", view.toString());
                        HotelCatagoeryAdapter.addCart(view);
                        return;
                    }
                    GlobalData.isSelectedProduct = HotelCatagoeryAdapter.product;
                    HotelCatagoeryAdapter.context.startActivity(new Intent(HotelCatagoeryAdapter.context, ProductDetailActivity.class));
                    HotelCatagoeryAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                } else {
                    view = new Builder(HotelCatagoeryAdapter.context);
                    view.setTitle(HotelCatagoeryAdapter.context.getResources().getString(R.string.replace_cart_item)).setMessage(HotelCatagoeryAdapter.context.getResources().getString(R.string.do_you_want_to_discart_the_selection_and_add_dishes_from_the_restaurant)).setPositiveButton(HotelCatagoeryAdapter.context.getResources().getString(R.string.yes), new C08013()).setNegativeButton(HotelCatagoeryAdapter.context.getResources().getString(R.string.no), new C08002());
                    view = view.create();
                    view.show();
                    Button button = view.getButton(-2);
                    button.setTextColor(ContextCompat.getColor(HotelCatagoeryAdapter.context, R.color.theme));
                    button.setTypeface(button.getTypeface(), 1);
                    view = view.getButton(-1);
                    view.setTextColor(ContextCompat.getColor(HotelCatagoeryAdapter.context, R.color.theme));
                    view.setTypeface(view.getTypeface(), 1);
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$5$1 */
            class C07991 implements Runnable {
                C07991() {
                }

                public void run() {
                    if (!HotelCatagoeryAdapter.dataResponse) {
                        HotelCatagoeryAdapter.avdProgress.start();
                        viewHolder.animationLineCartAdd.postDelayed(HotelCatagoeryAdapter.action, 3000);
                    }
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$5$2 */
            class C08002 implements DialogInterface.OnClickListener {
                C08002() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$5$3 */
            class C08013 implements DialogInterface.OnClickListener {
                C08013() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    HotelCatagoeryAdapter.this.clearCart();
                    if (HotelCatagoeryAdapter.product.getAddons() == null || HotelCatagoeryAdapter.product.getAddons().size() == null) {
                        GlobalData.selectedShop = HotelViewActivity.shops;
                        HotelCatagoeryAdapter.product = (Product) ((Category) HotelCatagoeryAdapter.this.list.get(i)).getProducts().get(i2);
                        viewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
                        viewHolder.cardAddTextLayout.setVisibility(View.GONE);
                        viewHolder.cardTextValue.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        viewHolder.cardTextValueTicker.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                        dialogInterface = new HashMap();
                        dialogInterface.put("product_id", HotelCatagoeryAdapter.product.getId().toString());
                        dialogInterface.put(Param.QUANTITY, viewHolder.cardTextValue.getText().toString());
                        Log.e("AddCart_Text", dialogInterface.toString());
                        HotelCatagoeryAdapter.addCart(dialogInterface);
                        return;
                    }
                    GlobalData.isSelectedProduct = HotelCatagoeryAdapter.product;
                    HotelCatagoeryAdapter.context.startActivity(new Intent(HotelCatagoeryAdapter.context, ProductDetailActivity.class));
                    HotelCatagoeryAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                }
            }
        });
    }

    private void clearCart() {
        apiInterface.clearCart().enqueue(new C13796());
    }

    /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$7 */
    static class C13807 implements Callback<AddCart> {
        C13807() {
        }

        public void onResponse(Call<AddCart> call, Response<AddCart> response) {
            GlobalData.selectedShop = HotelViewActivity.shops;
            if (response != null && response.isSuccessful() == null && response.errorBody() != null) {
                HotelCatagoeryAdapter.dialog.dismiss();
                HotelCatagoeryAdapter.dataResponse = true;
                try {
                    Toast.makeText(HotelCatagoeryAdapter.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<AddCart> response2) {
                    Toast.makeText(HotelCatagoeryAdapter.context, response2.getMessage(), 1).show();
                }
            } else if (response2.isSuccessful() != null) {
                if (GlobalData.selectedShop != null) {
                    GlobalData.addCartShopId = GlobalData.selectedShop.getId().intValue();
                }
                HotelCatagoeryAdapter.addCart = (AddCart) response2.body();
                GlobalData.addCart = (AddCart) response2.body();
                HotelCatagoeryAdapter.setViewcartBottomLayout(HotelCatagoeryAdapter.addCart);
                if (GlobalData.profileModel != null) {
                    call = new HashMap();
                    call.put("shop", String.valueOf(HotelViewActivity.shops.getId()));
                    call.put(AccessToken.USER_ID_KEY, String.valueOf(GlobalData.profileModel.getId()));
                    HotelCatagoeryAdapter.getCategories(call);
                    return;
                }
                call = new HashMap();
                call.put("shop", String.valueOf(HotelViewActivity.shops.getId()));
                HotelCatagoeryAdapter.getCategories(call);
            }
        }

        public void onFailure(Call<AddCart> call, Throwable th) {
            HotelCatagoeryAdapter.dialog.dismiss();
            HotelCatagoeryAdapter.dataResponse = true;
            Toast.makeText(HotelCatagoeryAdapter.context, "Something went wrong", 0).show();
        }
    }

    /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$8 */
    static class C13818 implements Callback<ShopDetail> {
        C13818() {
        }

        public void onResponse(@NonNull Call<ShopDetail> call, @NonNull Response<ShopDetail> response) {
            HotelCatagoeryAdapter.dataResponse = true;
            HotelCatagoeryAdapter.dialog.dismiss();
            HotelViewActivity.categoryList.clear();
            call = new Category();
            call.setName(HotelCatagoeryAdapter.context.getResources().getString(R.string.featured_products));
            call.setProducts(((ShopDetail) response.body()).getFeaturedProducts());
            HotelViewActivity.categoryList.add(call);
            HotelViewActivity.categoryList.addAll(((ShopDetail) response.body()).getCategories());
            GlobalData.categoryList = HotelViewActivity.categoryList;
            GlobalData.selectedShop.setCategories(HotelViewActivity.categoryList);
            HotelViewActivity.catagoeryAdapter.notifyDataSetChanged();
        }

        public void onFailure(@NonNull Call<ShopDetail> call, @NonNull Throwable th) {
            Toast.makeText(HotelCatagoeryAdapter.context, "Some thing went wrong", 0).show();
            HotelCatagoeryAdapter.dataResponse = true;
            HotelCatagoeryAdapter.dialog.dismiss();
        }
    }

    /* renamed from: com.entriver.orderaround.adapter.HotelCatagoeryAdapter$6 */
    class C13796 implements Callback<ClearCart> {
        C13796() {
        }

        public void onResponse(Call<ClearCart> call, Response<ClearCart> response) {
            if (response != null && response.isSuccessful() == null && response.errorBody() != null) {
                try {
                    Toast.makeText(HotelCatagoeryAdapter.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<ClearCart> response2) {
                    Toast.makeText(HotelCatagoeryAdapter.context, response2.getMessage(), 1).show();
                }
            } else if (response2.isSuccessful() != null) {
                GlobalData.selectedShop = HotelViewActivity.shops;
                GlobalData.addCart.getProductList().clear();
                GlobalData.notificationCount = null;
            }
        }

        public void onFailure(Call<ClearCart> call, Throwable th) {
            Toast.makeText(HotelCatagoeryAdapter.context, "Something went wrong", 0).show();
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        RelativeLayout cardAddDetailLayout;
        RelativeLayout cardAddTextLayout;
        RelativeLayout cardInfoLayout;
        TickerView cardTextValueTicker;
        LinearLayout categoryHeaderLayout;
        TextView featureProductsTitle;
        TextView headerTxt;
        LinearLayout rootLayout;
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
        private ImageView featuredImage;
        private ImageView foodImageType;
        private TextView priceTxt;

        public ViewHolder(View view, boolean z) {
            super(view);
            if (z) {
                this.headerTxt = (TextView) view.findViewById(R.id.category_header);
                this.featureProductsTitle = (TextView) view.findViewById(R.id.featured_product_title);
                this.categoryHeaderLayout = (LinearLayout) view.findViewById(R.id.category_header_layout);
                return;
            }
            this.dishImg = (ImageView) view.findViewById(R.id.dishImg);
            this.foodImageType = (ImageView) view.findViewById(R.id.food_type_image);
            this.featuredImage = (ImageView) view.findViewById(R.id.featured_image);
            this.addOnsIconImg = (ImageView) view.findViewById(R.id.add_ons_icon);
            this.animationLineCartAdd = (ImageView) view.findViewById(R.id.animation_line_cart_add);
            this.dishNameTxt = (TextView) view.findViewById(R.id.dish_name_text);
            this.customizableTxt = (TextView) view.findViewById(R.id.customizable_txt);
            this.priceTxt = (TextView) view.findViewById(R.id.price_text);
            this.cardAddDetailLayout = (RelativeLayout) view.findViewById(R.id.add_card_layout);
            this.rootLayout = (LinearLayout) view.findViewById(R.id.root_layout);
            this.cardAddTextLayout = (RelativeLayout) view.findViewById(R.id.add_card_text_layout);
            this.cardInfoLayout = (RelativeLayout) view.findViewById(R.id.add_card_info_layout);
            this.cardAddInfoText = (TextView) view.findViewById(R.id.avialablity_time);
            this.cardAddOutOfStock = (TextView) view.findViewById(R.id.out_of_stock);
            this.cardAddBtn = (ImageView) view.findViewById(R.id.card_add_btn);
            this.cardMinusBtn = (ImageView) view.findViewById(R.id.card_minus_btn);
            this.cardTextValue = (TextView) view.findViewById(R.id.card_value);
            this.cardTextValueTicker = (TickerView) view.findViewById(R.id.card_value_ticker);
            HotelCatagoeryAdapter.slide_down = AnimationUtils.loadAnimation(HotelCatagoeryAdapter.context, R.anim.slide_down);
            HotelCatagoeryAdapter.slide_up = AnimationUtils.loadAnimation(HotelCatagoeryAdapter.context, R.anim.slide_up);
        }
    }
}
