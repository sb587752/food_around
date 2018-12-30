package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.AddOnsAdapter;
import com.opalfire.orderaround.adapter.SliderPagerAdapter;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.AddCart;
import com.opalfire.orderaround.models.Addon;
import com.opalfire.orderaround.models.Cart;
import com.opalfire.orderaround.models.ClearCart;
import com.opalfire.orderaround.models.Image;
import com.opalfire.orderaround.models.Product;
import com.opalfire.orderaround.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductDetailActivity extends AppCompatActivity {
    public static RelativeLayout addItemLayout;
    public static TextView addOnsTxt;
    public static TextView itemText;
    public static TextView viewCart;
    @BindView(2131296301)
    RecyclerView addOnsRv;
    List<Addon> addonList;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    int cartId = 0;
    Context context;
    CustomDialog customDialog;
    int page_position = 0;
    Product product;
    @BindView(2131296742)
    TextView productDescription;
    @BindView(2131296744)
    TextView productName;
    @BindView(2131296747)
    ViewPager productSlider;
    @BindView(2131296748)
    LinearLayout productSliderDots;
    int quantity = 0;
    SliderPagerAdapter sliderPagerAdapter;
    List<Image> slider_image_list;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_product_detail);
        ButterKnife.bind((Activity) this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new C07521());
        this.context = this;
        this.customDialog = new CustomDialog(this.context);
        addOnsTxt = (TextView) findViewById(R.id.add_ons_txt);
        itemText = (TextView) findViewById(R.id.item_text);
        viewCart = (TextView) findViewById(R.id.view_cart);
        addItemLayout = (RelativeLayout) findViewById(R.id.view_cart_layout);
        this.product = GlobalData.isSelectedProduct;
        if (!(GlobalData.addCart == null || GlobalData.addCart.getProductList().size() == null)) {
            for (bundle = null; bundle < GlobalData.addCart.getProductList().size(); bundle++) {
                if (((Cart) GlobalData.addCart.getProductList().get(bundle)).getProductId().equals(this.product.getId())) {
                    this.cartId = ((Cart) GlobalData.addCart.getProductList().get(bundle)).getId().intValue();
                    this.quantity = ((Cart) GlobalData.addCart.getProductList().get(bundle)).getQuantity().intValue();
                }
            }
        }
        bundle = this.productName;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.product.getName());
        stringBuilder.append("\n");
        stringBuilder.append(this.product.getPrices().getCurrency());
        stringBuilder.append(this.product.getPrices().getPrice());
        bundle.setText(stringBuilder.toString());
        bundle = itemText;
        stringBuilder = new StringBuilder();
        stringBuilder.append("1 Item | ");
        stringBuilder.append(this.product.getPrices().getCurrency());
        stringBuilder.append(this.product.getPrices().getPrice());
        bundle.setText(stringBuilder.toString());
        this.productDescription.setText(this.product.getDescription());
        this.slider_image_list = new ArrayList();
        this.addonList = new ArrayList();
        this.addonList.addAll(this.product.getAddons());
        if (this.addonList.size() == null) {
            addOnsTxt.setVisibility(View.GONE);
        } else {
            addOnsTxt.setVisibility(View.VISIBLE);
        }
        this.addOnsRv.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
        this.addOnsRv.setHasFixedSize(false);
        this.addOnsRv.setNestedScrollingEnabled(false);
        this.addOnsRv.setAdapter(new AddOnsAdapter(this.addonList, this.context));
        this.slider_image_list.addAll(this.product.getImages());
        this.sliderPagerAdapter = new SliderPagerAdapter(this, this.slider_image_list, Boolean.valueOf(true));
        this.productSlider.setAdapter(this.sliderPagerAdapter);
        addBottomDots(View.VISIBLE);
        addItemLayout.setOnClickListener(new C07552());
        this.productSlider.addOnPageChangeListener(new C13373());
    }

    private void clearCart() {
        this.apiInterface.clearCart().enqueue(new C13384());
    }

    private void addItem(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postAddCart(hashMap).enqueue(new C13395());
    }

    private void addBottomDots(int i) {
        TextView[] textViewArr = new TextView[this.slider_image_list.size()];
        this.productSliderDots.removeAllViews();
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            textViewArr[i2] = new TextView(this);
            textViewArr[i2].setText(Html.fromHtml("&#8226;"));
            textViewArr[i2].setTextSize(35.0f);
            textViewArr[i2].setTextColor(Color.parseColor("#000000"));
            this.productSliderDots.addView(textViewArr[i2]);
        }
        if (textViewArr.length > 0) {
            textViewArr[i].setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
        finish();
    }

    /* renamed from: com.entriver.orderaround.activities.ProductDetailActivity$1 */
    class C07521 implements OnClickListener {
        C07521() {
        }

        public void onClick(View view) {
            ProductDetailActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.ProductDetailActivity$2 */
    class C07552 implements OnClickListener {

        C07552() {
        }

        public void onClick(View view) {
            int i = 0;
            if (GlobalData.profileModel == null) {
                Toast.makeText(ProductDetailActivity.this.context, "Please login", 0).show();
                return;
            }
            view = new HashMap();
            view.put("product_id", ProductDetailActivity.this.product.getId().toString());
            if (ProductDetailActivity.this.product.getCart() != null && ProductDetailActivity.this.product.getCart().size() == 1 && ProductDetailActivity.this.product.getAddons().isEmpty()) {
                view.put(Param.QUANTITY, String.valueOf(((Cart) ProductDetailActivity.this.product.getCart().get(0)).getQuantity().intValue() + 1));
                view.put("cart_id", String.valueOf(((Cart) ProductDetailActivity.this.product.getCart().get(0)).getId()));
            } else if (!ProductDetailActivity.this.product.getAddons().isEmpty() || ProductDetailActivity.this.cartId == 0) {
                view.put(Param.QUANTITY, AppEventsConstants.EVENT_PARAM_VALUE_YES);
                if (!AddOnsAdapter.list.isEmpty()) {
                    while (i < AddOnsAdapter.list.size()) {
                        Addon addon = (Addon) AddOnsAdapter.list.get(i);
                        if (addon.getAddon().getChecked()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("product_addons[");
                            stringBuilder.append(i);
                            stringBuilder.append("]");
                            view.put(stringBuilder.toString(), addon.getId().toString());
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("addons_qty[");
                            stringBuilder.append(i);
                            stringBuilder.append("]");
                            view.put(stringBuilder.toString(), addon.getQuantity().toString());
                        }
                        i++;
                    }
                }
            } else {
                view.put(Param.QUANTITY, String.valueOf(ProductDetailActivity.this.quantity + 1));
                view.put("cart_id", String.valueOf(ProductDetailActivity.this.cartId));
            }
            Log.e("AddCart_add", view.toString());
            if (Utils.isShopChanged(ProductDetailActivity.this.product.getShopId().intValue())) {
                Builder builder = new Builder(ProductDetailActivity.this.context);
                builder.setTitle(ProductDetailActivity.this.context.getResources().getString(R.string.replace_cart_item)).setMessage(ProductDetailActivity.this.context.getResources().getString(R.string.do_you_want_to_discart_the_selection_and_add_dishes_from_the_restaurant)).setPositiveButton(ProductDetailActivity.this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProductDetailActivity.this.clearCart();
                        ProductDetailActivity.this.addItem(view);
                    }
                }).setNegativeButton(ProductDetailActivity.this.context.getResources().getString(R.string.no), new C07531());
                view = builder.create();
                view.show();
                Button button = view.getButton(-2);
                button.setTextColor(ContextCompat.getColor(ProductDetailActivity.this.context, R.color.theme));
                button.setTypeface(button.getTypeface(), 1);
                view = view.getButton(-1);
                view.setTextColor(ContextCompat.getColor(ProductDetailActivity.this.context, R.color.theme));
                view.setTypeface(view.getTypeface(), 1);
                return;
            }
            ProductDetailActivity.this.addItem(view);
        }

        /* renamed from: com.entriver.orderaround.activities.ProductDetailActivity$2$1 */
        class C07531 implements DialogInterface.OnClickListener {
            C07531() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.ProductDetailActivity$3 */
    class C13373 implements OnPageChangeListener {
        C13373() {
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            ProductDetailActivity.this.addBottomDots(i);
        }
    }

    /* renamed from: com.entriver.orderaround.activities.ProductDetailActivity$4 */
    class C13384 implements Callback<ClearCart> {
        C13384() {
        }

        public void onResponse(Call<ClearCart> call, Response<ClearCart> response) {
            if (response != null && response.isSuccessful() == null && response.errorBody() != null) {
                try {
                    Toast.makeText(ProductDetailActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<ClearCart> response2) {
                    Toast.makeText(ProductDetailActivity.this.context, response2.getMessage(), 1).show();
                }
            } else if (response2.isSuccessful() != null) {
                GlobalData.selectedShop = HotelViewActivity.shops;
                GlobalData.addCart.getProductList().clear();
                GlobalData.notificationCount = null;
            }
        }

        public void onFailure(Call<ClearCart> call, Throwable th) {
            Toast.makeText(ProductDetailActivity.this.context, "Something went wrong", 0).show();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.ProductDetailActivity$5 */
    class C13395 implements Callback<AddCart> {
        C13395() {
        }

        public void onResponse(@NonNull Call<AddCart> call, @NonNull Response<AddCart> response) {
            ProductDetailActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.addCart = (AddCart) response.body();
                ProductDetailActivity.this.finish();
                return;
            }
            try {
                Toast.makeText(ProductDetailActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<AddCart> response2) {
                Toast.makeText(ProductDetailActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<AddCart> call, @NonNull Throwable th) {
            Toast.makeText(ProductDetailActivity.this, "ProductDetail : Something went wrong", 0).show();
            ProductDetailActivity.this.customDialog.dismiss();
        }
    }
}
