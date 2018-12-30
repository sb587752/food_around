package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.PaletteAsyncListener;
import android.support.v7.graphics.Palette.Swatch;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.facebook.AccessToken;
import com.opalfire.foodorder.HeaderView;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.HotelCatagoeryAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.ConnectionHelper;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.Cart;
import com.opalfire.foodorder.models.CartAddon;
import com.opalfire.foodorder.models.Category;
import com.opalfire.foodorder.models.Favorite;
import com.opalfire.foodorder.models.Product;
import com.opalfire.foodorder.models.Shop;
import com.opalfire.foodorder.models.ShopDetail;
import com.opalfire.foodorder.utils.Utils;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.sackcentury.shinebuttonlib.ShineButton.OnCheckedChangeListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HotelViewActivity extends AppCompatActivity implements OnOffsetChangedListener {
    public static HotelCatagoeryAdapter catagoeryAdapter;
    public static List<Category> categoryList;
    public static List<Product> featureProductList;
    public static TextView itemText;
    public static Shop shops;
    public static TextView viewCart;
    public static RelativeLayout viewCartLayout;
    public static TextView viewCartShopName;
    @BindView(2131296262)
    RecyclerView accompanimentDishesRv;
    Activity activity;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296324)
    AppBarLayout appBarLayout;
    @BindView(2131296420)
    CollapsingToolbarLayout collapsingToolbar;
    ConnectionHelper connectionHelper;
    Context context;
    @BindView(2131296469)
    TextView deliveryTime;
    @BindView(2131296553)
    HeaderView floatHeaderView;
    @BindView(R.id.header_view_sub_title)
    TextView headerViewSubTitle;
    @BindView(R.id.header_view_title)
    TextView headerViewTitle;
    @BindView(2131296578)
    ShineButton heartBtn;
    boolean isFavourite = false;
    boolean isShopIsChanged = true;
    int itemCount = 0;
    int itemQuantity = 0;
    @BindView(2131296677)
    TextView offer;
    int priceAmount = 0;
    @BindView(2131296763)
    TextView rating;
    @BindView(2131296769)
    RecyclerView recommendedDishesRv;
    @BindView(2131296781)
    ImageView restaurantImage;
    int restaurantPosition = 0;
    @BindView(2131296784)
    TextView restaurantSubtitle2;
    @BindView(2131296785)
    TextView restaurantTitle2;
    @BindView(2131296793)
    CoordinatorLayout rootLayout;
    @BindView(2131296805)
    NestedScrollView scroll;
    ViewSkeletonScreen skeleton;
    Animation slide_down;
    Animation slide_up;
    @BindView(2131296914)
    Toolbar toolbar;
    @BindView(2131296916)
    HeaderView toolbarHeaderView;
    @BindView(2131296960)
    View viewLine;
    private boolean isHideToolbarView = false;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_hotel_view);
        ButterKnife.bind((Activity) this);
        this.context = this;
        this.activity = this;
        this.connectionHelper = new ConnectionHelper(this.context);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationOnClickListener(new C07311());
        this.appBarLayout.addOnOffsetChangedListener((OnOffsetChangedListener) this);
        categoryList = new ArrayList();
        shops = GlobalData.selectedShop;
        if (shops != null) {
            StringBuilder stringBuilder;
            this.slide_down = AnimationUtils.loadAnimation(this.context, R.anim.slide_down);
            this.slide_up = AnimationUtils.loadAnimation(this.context, R.anim.slide_up);
            bundle = getIntent().getExtras();
            if (bundle != null) {
                this.restaurantPosition = bundle.getInt("position");
            }
            this.isFavourite = getIntent().getBooleanExtra("is_fav", false);
            if (shops.getOfferPercent() == null) {
                this.offer.setVisibility(View.GONE);
            } else {
                this.offer.setVisibility(View.VISIBLE);
                bundle = this.offer;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Flat ");
                stringBuilder.append(shops.getOfferPercent().toString());
                stringBuilder.append("% offer on all Orders");
                bundle.setText(stringBuilder.toString());
            }
            if (shops.getRating() != null) {
                bundle = Double.valueOf(new BigDecimal(shops.getRating().doubleValue()).setScale(1, RoundingMode.HALF_UP).doubleValue());
                TextView textView = this.rating;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(bundle);
                textView.setText(stringBuilder2.toString());
            } else {
                this.rating.setText("No Rating");
            }
            bundle = this.deliveryTime;
            stringBuilder = new StringBuilder();
            stringBuilder.append(shops.getEstimatedDeliveryTime().toString());
            stringBuilder.append("Mins");
            bundle.setText(stringBuilder.toString());
            itemText = (TextView) findViewById(R.id.item_text);
            viewCartShopName = (TextView) findViewById(R.id.view_cart_shop_name);
            viewCart = (TextView) findViewById(R.id.view_cart);
            viewCartLayout = (RelativeLayout) findViewById(R.id.view_cart_layout);
            viewCartLayout.setOnClickListener(new C07322());
            Glide.with(this.context).load(shops.getAvatar()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.ic_restaurant_place_holder).error((int) R.drawable.ic_restaurant_place_holder)).into(this.restaurantImage);
            Picasso.get().load(shops.getAvatar()).into(new C12973());
            this.collapsingToolbar.setTitle(" ");
            this.toolbarHeaderView.bindTo(shops.getName(), shops.getDescription());
            this.floatHeaderView.bindTo(shops.getName(), shops.getDescription());
            catagoeryAdapter = new HotelCatagoeryAdapter(this, this.activity, categoryList);
            this.accompanimentDishesRv.setLayoutManager(new LinearLayoutManager(this, 1, false));
            this.accompanimentDishesRv.setItemAnimator(new DefaultItemAnimator());
            this.accompanimentDishesRv.setAdapter(catagoeryAdapter);
            if (this.heartBtn != null) {
                this.heartBtn.init(this);
            }
            if (shops.getFavorite() == null) {
                if (this.isFavourite == null) {
                    this.heartBtn.setTag(Integer.valueOf(0));
                    this.heartBtn.setShineDistanceMultiple(1.8f);
                    this.heartBtn.setOnClickListener(new C07334());
                    this.heartBtn.setOnCheckStateChangeListener(new C12985());
                    this.skeleton = Skeleton.bind(this.rootLayout).load(R.layout.skeleton_hotel_view).show();
                    return;
                }
            }
            this.heartBtn.setChecked(true);
            this.heartBtn.setTag(Integer.valueOf(1));
            this.heartBtn.setShineDistanceMultiple(1.8f);
            this.heartBtn.setOnClickListener(new C07334());
            this.heartBtn.setOnCheckStateChangeListener(new C12985());
            this.skeleton = Skeleton.bind(this.rootLayout).load(R.layout.skeleton_hotel_view).show();
            return;
        }
        startActivity(new Intent(this.context, SplashActivity.class));
        finish();
    }

    private void deleteFavorite(Integer num) {
        this.apiInterface.deleteFavorite(num.intValue()).enqueue(new C12996());
    }

    private void doFavorite(Integer num) {
        this.apiInterface.doFavorite(num.intValue()).enqueue(new C13007());
    }

    private void setViewcartBottomLayout(AddCart addCart) {
        this.priceAmount = 0;
        this.itemQuantity = 0;
        this.itemCount = 0;
        this.itemCount = addCart.getProductList().size();
        int i = 0;
        while (i < this.itemCount) {
            this.itemQuantity += ((Cart) addCart.getProductList().get(i)).getQuantity().intValue();
            if (((Cart) addCart.getProductList().get(i)).getProduct().getPrices().getPrice() != null) {
                this.priceAmount += ((Cart) addCart.getProductList().get(i)).getQuantity().intValue() * ((Cart) addCart.getProductList().get(i)).getProduct().getPrices().getPrice().intValue();
            }
            if (!(((Cart) addCart.getProductList().get(i)).getCartAddons() == null || ((Cart) addCart.getProductList().get(i)).getCartAddons().isEmpty())) {
                for (int i2 = 0; i2 < ((Cart) addCart.getProductList().get(i)).getCartAddons().size(); i2++) {
                    this.priceAmount += ((Cart) addCart.getProductList().get(i)).getQuantity().intValue() * (((CartAddon) ((Cart) addCart.getProductList().get(i)).getCartAddons().get(i2)).getQuantity().intValue() * ((CartAddon) ((Cart) addCart.getProductList().get(i)).getCartAddons().get(i2)).getAddonProduct().getPrice().intValue());
                }
            }
            i++;
        }
        GlobalData.notificationCount = this.itemQuantity;
        if (this.itemQuantity == 0) {
            viewCartLayout.setVisibility(View.GONE);
            viewCartLayout.startAnimation(this.slide_down);
        } else if (this.itemQuantity == 1) {
            if (shops.getId().equals(((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShopId())) {
                viewCartShopName.setVisibility(View.GONE);
            } else {
                viewCartShopName.setVisibility(View.VISIBLE);
                r1 = viewCartShopName;
                r3 = new StringBuilder();
                r3.append("From : ");
                r3.append(((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShop().getName());
                r1.setText(r3.toString());
            }
            addCart = ((Cart) addCart.getProductList().get(0)).getProduct().getPrices().getCurrency();
            r1 = itemText;
            r3 = new StringBuilder();
            r3.append("");
            r3.append(this.itemQuantity);
            r3.append(" Item | ");
            r3.append(addCart);
            r3.append("");
            r3.append(this.priceAmount);
            r1.setText(r3.toString());
            if (viewCartLayout.getVisibility() == 8) {
                viewCartLayout.setVisibility(View.VISIBLE);
                viewCartLayout.startAnimation(this.slide_up);
            }
        } else {
            if (Objects.equals(shops.getId(), ((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShopId())) {
                viewCartShopName.setVisibility(View.GONE);
            } else {
                viewCartShopName.setVisibility(View.VISIBLE);
                r1 = viewCartShopName;
                r3 = new StringBuilder();
                r3.append("From : ");
                r3.append(((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShop().getName());
                r1.setText(r3.toString());
            }
            addCart = ((Cart) addCart.getProductList().get(0)).getProduct().getPrices().getCurrency();
            r1 = itemText;
            r3 = new StringBuilder();
            r3.append("");
            r3.append(this.itemQuantity);
            r3.append(" Items | ");
            r3.append(addCart);
            r3.append("");
            r3.append(this.priceAmount);
            r1.setText(r3.toString());
            if (viewCartLayout.getVisibility() == 8) {
                viewCartLayout.setVisibility(View.VISIBLE);
                viewCartLayout.startAnimation(this.slide_up);
            }
        }
    }

    private void getCategories(HashMap<String, String> hashMap) {
        this.skeleton.show();
        this.apiInterface.getCategories(hashMap).enqueue(new C13018());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    protected void onResume() {
        super.onResume();
        if (HotelCatagoeryAdapter.bottomSheetDialogFragment != null) {
            HotelCatagoeryAdapter.bottomSheetDialogFragment.dismiss();
        }
        if (!this.connectionHelper.isConnectingToInternet()) {
            Utils.displayMessage(this.activity, this.context, getString(R.string.oops_connect_your_internet));
        } else if (GlobalData.profileModel != null) {
            r0 = new HashMap();
            r0.put("shop", String.valueOf(shops.getId()));
            r0.put(AccessToken.USER_ID_KEY, String.valueOf(GlobalData.profileModel.getId()));
            getCategories(r0);
        } else {
            r0 = new HashMap();
            r0.put("shop", String.valueOf(shops.getId()));
            getCategories(r0);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        i = ((float) Math.abs(i)) / ((float) appBarLayout.getTotalScrollRange());
        if (i == 1065353216 && this.isHideToolbarView) {
            this.toolbarHeaderView.setVisibility(View.VISIBLE);
            this.isHideToolbarView ^= 1;
        } else if (i < 1065353216 && this.isHideToolbarView == null) {
            this.toolbarHeaderView.setVisibility(View.GONE);
            this.isHideToolbarView ^= 1;
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$1 */
    class C07311 implements OnClickListener {
        C07311() {
        }

        public void onClick(View view) {
            HotelViewActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$2 */
    class C07322 implements OnClickListener {
        C07322() {
        }

        public void onClick(View view) {
            HotelViewActivity.this.startActivity(new Intent(HotelViewActivity.this, ViewCartActivity.class));
            HotelViewActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$4 */
    class C07334 implements OnClickListener {
        C07334() {
        }

        public void onClick(View view) {
            if (HotelViewActivity.this.heartBtn.getTag().equals(Integer.valueOf(0)) != null) {
                HotelViewActivity.this.heartBtn.setTag(Integer.valueOf(1));
                HotelViewActivity.this.heartBtn.setShapeResource(R.raw.heart);
                return;
            }
            HotelViewActivity.this.heartBtn.setTag(Integer.valueOf(0));
            HotelViewActivity.this.heartBtn.setShapeResource(R.raw.icc_heart);
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$3 */
    class C12973 implements Target {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class cls = HotelViewActivity.class;
        }

        C12973() {
        }

        public void onBitmapFailed(Exception exception, Drawable drawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }

        public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
            HotelViewActivity.this.restaurantImage.setImageBitmap(bitmap);
            Palette.from(bitmap).generate(new C12961());
        }

        /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$3$1 */
        class C12961 implements PaletteAsyncListener {
            C12961() {
            }

            public void onGenerated(Palette palette) {
                Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                if (darkMutedSwatch == null) {
                    palette = palette.getMutedSwatch();
                    if (palette != null) {
                        HotelViewActivity.this.collapsingToolbar.setContentScrimColor(palette.getRgb());
                        if (VERSION.SDK_INT >= 21) {
                            Window window = HotelViewActivity.this.getWindow();
                            window.addFlags(Integer.MIN_VALUE);
                            window.setStatusBarColor(palette.getRgb());
                        }
                        HotelViewActivity.this.headerViewTitle.setTextColor(palette.getTitleTextColor());
                        HotelViewActivity.this.headerViewSubTitle.setTextColor(palette.getBodyTextColor());
                        return;
                    }
                    return;
                }
                HotelViewActivity.this.collapsingToolbar.setContentScrimColor(darkMutedSwatch.getRgb());
                if (VERSION.SDK_INT >= 21) {
                    palette = HotelViewActivity.this.getWindow();
                    palette.addFlags(Integer.MIN_VALUE);
                    palette.setStatusBarColor(darkMutedSwatch.getRgb());
                }
                HotelViewActivity.this.headerViewTitle.setTextColor(darkMutedSwatch.getTitleTextColor());
                HotelViewActivity.this.headerViewSubTitle.setTextColor(darkMutedSwatch.getBodyTextColor());
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$5 */
    class C12985 implements OnCheckedChangeListener {
        C12985() {
        }

        public void onCheckedChanged(View view, boolean z) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("click ");
            stringBuilder.append(z);
            Log.e("HeartButton", stringBuilder.toString());
            if (HotelViewActivity.this.connectionHelper.isConnectingToInternet() == null) {
                Utils.displayMessage(HotelViewActivity.this.activity, HotelViewActivity.this.context, HotelViewActivity.this.getString(R.string.oops_connect_your_internet));
            } else if (!z) {
                HotelViewActivity.this.deleteFavorite(HotelViewActivity.shops.getId());
            } else if (GlobalData.profileModel != null) {
                HotelViewActivity.this.doFavorite(HotelViewActivity.shops.getId());
            } else {
                HotelViewActivity.this.startActivity(new Intent(HotelViewActivity.this.context, LoginActivity.class).setFlags(268468224));
                HotelViewActivity.this.overridePendingTransition(true, R.anim.anim_nothing);
                HotelViewActivity.this.finish();
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$6 */
    class C12996 implements Callback<Favorite> {
        C12996() {
        }

        public void onFailure(@NonNull Call<Favorite> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<Favorite> call, @NonNull Response<Favorite> response) {
            Favorite favorite = (Favorite) response.body();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$7 */
    class C13007 implements Callback<Favorite> {
        C13007() {
        }

        public void onFailure(@NonNull Call<Favorite> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<Favorite> call, @NonNull Response<Favorite> response) {
            Favorite favorite = (Favorite) response.body();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.HotelViewActivity$8 */
    class C13018 implements Callback<ShopDetail> {
        C13018() {
        }

        public void onFailure(@NonNull Call<ShopDetail> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<ShopDetail> call, @NonNull Response<ShopDetail> response) {
            HotelViewActivity.this.skeleton.hide();
            if (response.isSuccessful() != null) {
                HotelViewActivity.categoryList = new ArrayList();
                HotelViewActivity.categoryList.clear();
                call = new Category();
                HotelViewActivity.featureProductList = new ArrayList();
                HotelViewActivity.featureProductList = ((ShopDetail) response.body()).getFeaturedProducts();
                call.setName(HotelViewActivity.this.getResources().getString(R.string.featured_products));
                call.setProducts(HotelViewActivity.featureProductList);
                HotelViewActivity.categoryList.add(call);
                HotelViewActivity.categoryList.addAll(((ShopDetail) response.body()).getCategories());
                GlobalData.categoryList = HotelViewActivity.categoryList;
                GlobalData.selectedShop.setCategories(HotelViewActivity.categoryList);
                HotelViewActivity.catagoeryAdapter = new HotelCatagoeryAdapter(HotelViewActivity.this.context, HotelViewActivity.this.activity, HotelViewActivity.categoryList);
                HotelViewActivity.this.accompanimentDishesRv.setLayoutManager(new LinearLayoutManager(HotelViewActivity.this.context, 1, false));
                HotelViewActivity.this.accompanimentDishesRv.setItemAnimator(new DefaultItemAnimator());
                HotelViewActivity.this.accompanimentDishesRv.setAdapter(HotelViewActivity.catagoeryAdapter);
                if (GlobalData.addCart == null || GlobalData.addCart.getProductList().size() == null) {
                    HotelViewActivity.viewCartLayout.setVisibility(View.GONE);
                } else {
                    HotelViewActivity.this.setViewcartBottomLayout(GlobalData.addCart);
                }
                HotelViewActivity.catagoeryAdapter.notifyDataSetChanged();
            }
        }
    }
}
