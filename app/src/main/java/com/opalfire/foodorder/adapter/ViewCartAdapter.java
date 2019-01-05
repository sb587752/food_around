package com.opalfire.foodorder.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.fragments.AddonBottomSheetFragment;
import com.opalfire.foodorder.fragments.CartChoiceModeFragment;
import com.opalfire.foodorder.fragments.CartFragment;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.Cart;
import com.opalfire.foodorder.models.CartAddon;
import com.opalfire.foodorder.models.Product;
import com.opalfire.foodorder.models.Shop;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartAdapter extends Adapter<MyViewHolder> {
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    public static Runnable action = null;
    public static AddCart addCart = null;
    public static ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    public static AnimatedVectorDrawableCompat avdProgress = null;
    public static CartChoiceModeFragment bottomSheetDialogFragment = null;
    public static Context context = null;
    public static boolean dataResponse = false;
    public static Dialog dialog;
    public static int discount;
    public static int itemCount;
    public static int itemQuantity;
    public static int priceAmount;
    public static Product product;
    public static Cart productList;
    public static Shop selectedShop = GlobalData.selectedShop;
    private List<Cart> list;

    public ViewCartAdapter(List<Cart> list, Context context) {
        this.list = list;
        context = context;
    }

    public static void addCart(HashMap<String, String> hashMap) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.empty_dialog);
        dialog.setCancelable(false);
        dataResponse = false;
        dialog.show();
        apiInterface.postAddCart(hashMap).enqueue(new C13864());
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_product_item, viewGroup, false));
    }

    public void add(Cart cart, int i) {
        this.list.add(i, cart);
        notifyItemInserted(i);
    }

    public void remove(Cart cart) {
        cart = this.list.indexOf(cart);
        this.list.remove(cart);
        notifyItemRemoved(cart);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.cardAddTextLayout.setVisibility(View.GONE);
        int i2 = 0;
        myViewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
        product = ((Cart) this.list.get(i)).getProduct();
        myViewHolder.cardTextValueTicker.setCharacterList(NUMBER_LIST);
        myViewHolder.dishNameTxt.setText(product.getName());
        myViewHolder.cardTextValue.setText(((Cart) this.list.get(i)).getQuantity().toString());
        myViewHolder.cardTextValueTicker.setText(((Cart) this.list.get(i)).getQuantity().toString());
        priceAmount = ((Cart) this.list.get(i)).getQuantity().intValue() * product.getPrices().getPrice().intValue();
        if (!(((Cart) this.list.get(i)).getCartAddons() == null || ((Cart) this.list.get(i)).getCartAddons().isEmpty())) {
            for (int i3 = 0; i3 < ((Cart) this.list.get(i)).getCartAddons().size(); i3++) {
                priceAmount += ((Cart) this.list.get(i)).getQuantity().intValue() * (((CartAddon) ((Cart) this.list.get(i)).getCartAddons().get(i3)).getQuantity().intValue() * ((CartAddon) ((Cart) this.list.get(i)).getCartAddons().get(i3)).getAddonProduct().getPrice().intValue());
            }
        }
        TextView access$300 = myViewHolder.priceTxt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(product.getPrices().getCurrency());
        stringBuilder.append(" ");
        stringBuilder.append(priceAmount);
        access$300.setText(stringBuilder.toString());
        if (product.getFoodType().equalsIgnoreCase("veg")) {
            myViewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_veg));
        } else {
            myViewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_nonveg));
        }
        selectedShop = product.getShop();
        if (product.getAddons().size() > 0) {
            myViewHolder.customize.setVisibility(View.VISIBLE);
            myViewHolder.addons.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.customize.setVisibility(View.GONE);
            myViewHolder.addons.setVisibility(View.GONE);
        }
        List cartAddons = ((Cart) this.list.get(i)).getCartAddons();
        if (cartAddons.isEmpty()) {
            myViewHolder.addons.setText("");
        } else {
            while (i2 < cartAddons.size()) {
                if (i2 == 0) {
                    myViewHolder.addons.setText(((CartAddon) cartAddons.get(i2)).getAddonProduct().getAddon().getName());
                } else {
                    TextView access$600 = myViewHolder.addons;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(", ");
                    stringBuilder.append(((CartAddon) cartAddons.get(i2)).getAddonProduct().getAddon().getName());
                    access$600.append(stringBuilder.toString());
                }
                i2++;
            }
        }
        myViewHolder.cardAddBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                Log.e("access_token2", GlobalData.accessToken);
                int i = 0;
                myViewHolder.animationLineCartAdd.setVisibility(View.VISIBLE);
                ViewCartAdapter.avdProgress = AnimatedVectorDrawableCompat.create(ViewCartAdapter.context, R.drawable.add_cart_avd_line);
                myViewHolder.animationLineCartAdd.setBackground(ViewCartAdapter.avdProgress);
                ViewCartAdapter.avdProgress.start();
                ViewCartAdapter.action = new C08291();
                myViewHolder.animationLineCartAdd.postDelayed(ViewCartAdapter.action, 3000);
                ViewCartAdapter.product = ((Cart) ViewCartAdapter.this.list.get(i)).getProduct();
                if (ViewCartAdapter.product.getAddons() == null || ViewCartAdapter.product.getAddons().isEmpty() != null) {
                    view = Integer.parseInt(myViewHolder.cardTextValue.getText().toString()) + 1;
                    TextView access$200 = myViewHolder.cardTextValue;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    access$200.setText(stringBuilder.toString());
                    TickerView tickerView = myViewHolder.cardTextValueTicker;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    tickerView.setText(stringBuilder.toString());
                    view = new HashMap();
                    view.put("product_id", ViewCartAdapter.product.getId().toString());
                    view.put(Param.QUANTITY, myViewHolder.cardTextValue.getText().toString());
                    view.put("cart_id", String.valueOf(((Cart) ViewCartAdapter.this.list.get(i)).getId()));
                    Log.e("AddCart_add", view.toString());
                    ViewCartAdapter.addCart(view);
                    ViewCartAdapter.priceAmount = Integer.parseInt(myViewHolder.cardTextValue.getText().toString()) * ViewCartAdapter.product.getPrices().getPrice().intValue();
                    if (((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons() != null && ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().isEmpty() == null) {
                        while (i < ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().size()) {
                            ViewCartAdapter.priceAmount += ((Cart) ViewCartAdapter.this.list.get(i)).getQuantity().intValue() * (((CartAddon) ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().get(i)).getQuantity().intValue() * ((CartAddon) ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().get(i)).getAddonProduct().getPrice().intValue());
                            i++;
                        }
                    }
                    view = myViewHolder.priceTxt;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(ViewCartAdapter.product.getPrices().getCurrency());
                    stringBuilder2.append(" ");
                    stringBuilder2.append(ViewCartAdapter.priceAmount);
                    view.setText(stringBuilder2.toString());
                    return;
                }
                GlobalData.isSelectedProduct = ViewCartAdapter.product;
                CartChoiceModeFragment.lastCart = (Cart) ViewCartAdapter.this.list.get(i);
                ViewCartAdapter.bottomSheetDialogFragment = new CartChoiceModeFragment();
                ViewCartAdapter.bottomSheetDialogFragment.show(((AppCompatActivity) ViewCartAdapter.context).getSupportFragmentManager(), ViewCartAdapter.bottomSheetDialogFragment.getTag());
                CartChoiceModeFragment.isViewcart = true;
                CartChoiceModeFragment.isSearch = false;
            }

            /* renamed from: com.entriver.foodorder.adapter.ViewCartAdapter$1$1 */
            class C08291 implements Runnable {
                C08291() {
                }

                public void run() {
                    if (!ViewCartAdapter.dataResponse) {
                        ViewCartAdapter.avdProgress.start();
                        myViewHolder.animationLineCartAdd.postDelayed(ViewCartAdapter.action, 3000);
                    }
                }
            }
        });
        myViewHolder.cardMinusBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                int i = 0;
                myViewHolder.animationLineCartAdd.setVisibility(View.VISIBLE);
                ViewCartAdapter.avdProgress = AnimatedVectorDrawableCompat.create(ViewCartAdapter.context, R.drawable.add_cart_avd_line);
                myViewHolder.animationLineCartAdd.setBackground(ViewCartAdapter.avdProgress);
                ViewCartAdapter.avdProgress.start();
                ViewCartAdapter.action = new C08311();
                myViewHolder.animationLineCartAdd.postDelayed(ViewCartAdapter.action, 3000);
                ViewCartAdapter.product = ((Cart) ViewCartAdapter.this.list.get(i)).getProduct();
                ViewCartAdapter.priceAmount = Integer.parseInt(myViewHolder.cardTextValue.getText().toString()) * ViewCartAdapter.product.getPrices().getPrice().intValue();
                if (((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons() != null && ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().isEmpty() == null) {
                    for (view = null; view < ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().size(); view++) {
                        ViewCartAdapter.priceAmount += ((Cart) ViewCartAdapter.this.list.get(i)).getQuantity().intValue() * (((CartAddon) ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().get(view)).getQuantity().intValue() * ((CartAddon) ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons().get(view)).getAddonProduct().getPrice().intValue());
                    }
                }
                view = myViewHolder.priceTxt;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ViewCartAdapter.product.getPrices().getCurrency());
                stringBuilder.append(" ");
                stringBuilder.append(ViewCartAdapter.priceAmount);
                view.setText(stringBuilder.toString());
                HashMap hashMap;
                if (myViewHolder.cardTextValue.getText().toString().equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_YES) != null) {
                    view = Integer.parseInt(myViewHolder.cardTextValue.getText().toString()) - 1;
                    TextView access$200 = myViewHolder.cardTextValue;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(view);
                    access$200.setText(stringBuilder2.toString());
                    TickerView tickerView = myViewHolder.cardTextValueTicker;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(view);
                    tickerView.setText(stringBuilder2.toString());
                    ViewCartAdapter.productList = (Cart) ViewCartAdapter.this.list.get(i);
                    hashMap = new HashMap();
                    hashMap.put("product_id", ViewCartAdapter.product.getId().toString());
                    hashMap.put(Param.QUANTITY, String.valueOf(view));
                    hashMap.put("cart_id", String.valueOf(((Cart) ViewCartAdapter.this.list.get(i)).getId()));
                    view = ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons();
                    while (i < view.size()) {
                        CartAddon cartAddon = (CartAddon) view.get(i);
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("product_addons[");
                        stringBuilder3.append(i);
                        stringBuilder3.append("]");
                        hashMap.put(stringBuilder3.toString(), cartAddon.getAddonProduct().getId().toString());
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("addons_qty[");
                        stringBuilder3.append(i);
                        stringBuilder3.append("]");
                        hashMap.put(stringBuilder3.toString(), cartAddon.getQuantity().toString());
                        i++;
                    }
                    Log.e("AddCart_Minus", hashMap.toString());
                    ViewCartAdapter.addCart(hashMap);
                    ViewCartAdapter.this.remove(ViewCartAdapter.productList);
                    return;
                }
                view = Integer.parseInt(myViewHolder.cardTextValue.getText().toString()) - 1;
                access$200 = myViewHolder.cardTextValue;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(view);
                access$200.setText(stringBuilder2.toString());
                tickerView = myViewHolder.cardTextValueTicker;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(view);
                tickerView.setText(stringBuilder2.toString());
                hashMap = new HashMap();
                hashMap.put("product_id", ViewCartAdapter.product.getId().toString());
                hashMap.put(Param.QUANTITY, String.valueOf(view));
                hashMap.put("cart_id", String.valueOf(((Cart) ViewCartAdapter.this.list.get(i)).getId()));
                view = ((Cart) ViewCartAdapter.this.list.get(i)).getCartAddons();
                while (i < view.size()) {
                    cartAddon = (CartAddon) view.get(i);
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("product_addons[");
                    stringBuilder3.append(i);
                    stringBuilder3.append("]");
                    hashMap.put(stringBuilder3.toString(), cartAddon.getAddonProduct().getId().toString());
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("addons_qty[");
                    stringBuilder3.append(i);
                    stringBuilder3.append("]");
                    hashMap.put(stringBuilder3.toString(), cartAddon.getQuantity().toString());
                    i++;
                }
                Log.e("AddCart_Minus", hashMap.toString());
                ViewCartAdapter.addCart(hashMap);
            }

            /* renamed from: com.entriver.foodorder.adapter.ViewCartAdapter$2$1 */
            class C08311 implements Runnable {
                C08311() {
                }

                public void run() {
                    if (!ViewCartAdapter.dataResponse) {
                        ViewCartAdapter.avdProgress.start();
                        myViewHolder.animationLineCartAdd.postDelayed(ViewCartAdapter.action, 3000);
                    }
                }
            }
        });
        myViewHolder.customize.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                myViewHolder.animationLineCartAdd.setVisibility(View.VISIBLE);
                ViewCartAdapter.avdProgress = AnimatedVectorDrawableCompat.create(ViewCartAdapter.context, R.drawable.add_cart_avd_line);
                myViewHolder.animationLineCartAdd.setBackground(ViewCartAdapter.avdProgress);
                ViewCartAdapter.avdProgress.start();
                ViewCartAdapter.action = new C08331();
                myViewHolder.animationLineCartAdd.postDelayed(ViewCartAdapter.action, 3000);
                ViewCartAdapter.productList = (Cart) ViewCartAdapter.this.list.get(i);
                GlobalData.isSelectedProduct = ViewCartAdapter.product;
                GlobalData.isSelctedCart = ViewCartAdapter.productList;
                GlobalData.cartAddons = ViewCartAdapter.productList.getCartAddons();
                view = new AddonBottomSheetFragment();
                view.show(((AppCompatActivity) ViewCartAdapter.context).getSupportFragmentManager(), view.getTag());
                AddonBottomSheetFragment.selectedCart = (Cart) ViewCartAdapter.this.list.get(i);
            }

            /* renamed from: com.entriver.foodorder.adapter.ViewCartAdapter$3$1 */
            class C08331 implements Runnable {
                C08331() {
                }

                public void run() {
                    if (!ViewCartAdapter.dataResponse) {
                        ViewCartAdapter.avdProgress.start();
                        myViewHolder.animationLineCartAdd.postDelayed(ViewCartAdapter.action, 3000);
                    }
                }
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }

    /* renamed from: com.entriver.foodorder.adapter.ViewCartAdapter$4 */
    static class C13864 implements Callback<AddCart> {
        C13864() {
        }

        public void onFailure(Call<AddCart> call, Throwable th) {
        }

        public void onResponse(Call<AddCart> call, Response<AddCart> response) {
            ViewCartAdapter.avdProgress.stop();
            ViewCartAdapter.dialog.dismiss();
            ViewCartAdapter.dataResponse = true;
            if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                try {
                    Toast.makeText(ViewCartAdapter.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<AddCart> response2) {
                    Toast.makeText(ViewCartAdapter.context, response2.getMessage(), 1).show();
                }
            } else if (response2.isSuccessful() != null) {
                ViewCartAdapter.addCart = (AddCart) response2.body();
                GlobalData.addCart = new AddCart();
                GlobalData.addCart = (AddCart) response2.body();
                CartFragment.viewCartItemList.clear();
                CartFragment.viewCartItemList.addAll(((AddCart) response2.body()).getProductList());
                CartFragment.viewCartAdapter.notifyDataSetChanged();
                ViewCartAdapter.priceAmount = 0;
                ViewCartAdapter.discount = 0;
                ViewCartAdapter.itemQuantity = 0;
                ViewCartAdapter.itemCount = 0;
                ViewCartAdapter.itemCount = ViewCartAdapter.addCart.getProductList().size();
                if (ViewCartAdapter.itemCount != 0) {
                    int i;
                    int i2 = 0;
                    while (i2 < ViewCartAdapter.itemCount) {
                        ViewCartAdapter.itemQuantity += ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getQuantity().intValue();
                        if (((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getProduct().getPrices().getPrice() != null) {
                            ViewCartAdapter.priceAmount += ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getQuantity().intValue() * ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getProduct().getPrices().getPrice().intValue();
                        }
                        if (!(((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getCartAddons() == null || ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getCartAddons().isEmpty())) {
                            for (i = 0; i < ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getCartAddons().size(); i++) {
                                ViewCartAdapter.priceAmount += ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getQuantity().intValue() * (((CartAddon) ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getCartAddons().get(i)).getQuantity().intValue() * ((CartAddon) ((Cart) ViewCartAdapter.addCart.getProductList().get(i2)).getCartAddons().get(i)).getAddonProduct().getPrice().intValue());
                            }
                        }
                        i2++;
                    }
                    if (((Cart) ((AddCart) response2.body()).getProductList().get(0)).getProduct().getShop().getOfferMinAmount() != null && ((Cart) ((AddCart) response2.body()).getProductList().get(0)).getProduct().getShop().getOfferMinAmount().doubleValue() < ((double) ViewCartAdapter.priceAmount)) {
                        double d = (double) ViewCartAdapter.priceAmount;
                        double intValue = (double) ((Cart) ((AddCart) response2.body()).getProductList().get(0)).getProduct().getShop().getOfferPercent().intValue();
                        Double.isNaN(intValue);
                        intValue *= 0.01d;
                        Double.isNaN(d);
                        ViewCartAdapter.discount = (int) (d * intValue);
                    }
                    GlobalData.notificationCount = ViewCartAdapter.itemQuantity;
                    String currency = ((Cart) ViewCartAdapter.addCart.getProductList().get(0)).getProduct().getPrices().getCurrency();
                    TextView textView = CartFragment.itemTotalAmount;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(currency);
                    stringBuilder.append("");
                    stringBuilder.append(ViewCartAdapter.priceAmount);
                    textView.setText(stringBuilder.toString());
                    textView = CartFragment.discountAmount;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("- ");
                    stringBuilder.append(currency);
                    stringBuilder.append("");
                    stringBuilder.append(ViewCartAdapter.discount);
                    textView.setText(stringBuilder.toString());
                    int i3 = ViewCartAdapter.priceAmount - ViewCartAdapter.discount;
                    double d2 = (double) i3;
                    double intValue2 = (double) ((AddCart) response2.body()).getTaxPercentage().intValue();
                    Double.isNaN(intValue2);
                    intValue2 *= 0.01d;
                    Double.isNaN(d2);
                    i = (int) Math.round(d2 * intValue2);
                    i3 = (i3 + i) + ((AddCart) response2.body()).getDeliveryCharges().intValue();
                    TextView textView2 = CartFragment.serviceTax;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(((Cart) ((AddCart) response2.body()).getProductList().get(0)).getProduct().getPrices().getCurrency());
                    stringBuilder.append("");
                    stringBuilder.append(String.valueOf(i));
                    textView2.setText(stringBuilder.toString());
                    call = CartFragment.payAmount;
                    response2 = new StringBuilder();
                    response2.append(currency);
                    response2.append("");
                    response2.append(i3);
                    call.setText(response2.toString());
                    return;
                }
                GlobalData.notificationCount = ViewCartAdapter.itemQuantity;
                CartFragment.errorLayout.setVisibility(View.VISIBLE);
                CartFragment.dataLayout.setVisibility(View.GONE);
                Toast.makeText(ViewCartAdapter.context, "Cart is empty", 0).show();
            }
        }
    }

    public class MyViewHolder extends ViewHolder {
        RelativeLayout cardAddDetailLayout;
        RelativeLayout cardAddTextLayout;
        RelativeLayout cardInfoLayout;
        TickerView cardTextValueTicker;
        private TextView addons;
        private ImageView animationLineCartAdd;
        private ImageView cardAddBtn;
        private TextView cardAddInfoText;
        private TextView cardAddOutOfStock;
        private ImageView cardMinusBtn;
        private TextView cardTextValue;
        private TextView customizableTxt;
        private TextView customize;
        private ImageView dishImg;
        private TextView dishNameTxt;
        private ImageView foodImageType;
        private TextView priceTxt;

        private MyViewHolder(View view) {
            super(view);
            this.foodImageType = (ImageView) this.itemView.findViewById(R.id.food_type_image);
            this.animationLineCartAdd = (ImageView) this.itemView.findViewById(R.id.animation_line_cart_add);
            this.dishNameTxt = (TextView) this.itemView.findViewById(R.id.dish_name_text);
            this.priceTxt = (TextView) this.itemView.findViewById(R.id.price_text);
            this.customizableTxt = (TextView) this.itemView.findViewById(R.id.customizable_txt);
            this.addons = (TextView) this.itemView.findViewById(R.id.addons);
            this.customize = (TextView) this.itemView.findViewById(R.id.customize);
            this.cardAddDetailLayout = (RelativeLayout) this.itemView.findViewById(R.id.add_card_layout);
            this.cardAddTextLayout = (RelativeLayout) this.itemView.findViewById(R.id.add_card_text_layout);
            this.cardAddInfoText = (TextView) this.itemView.findViewById(R.id.avialablity_time);
            this.cardAddOutOfStock = (TextView) this.itemView.findViewById(R.id.out_of_stock);
            this.cardAddBtn = (ImageView) this.itemView.findViewById(R.id.card_add_btn);
            this.cardMinusBtn = (ImageView) this.itemView.findViewById(R.id.card_minus_btn);
            this.cardTextValue = (TextView) this.itemView.findViewById(R.id.card_value);
            this.cardTextValueTicker = (TickerView) this.itemView.findViewById(R.id.card_value_ticker);
        }
    }
}
