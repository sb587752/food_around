package com.opalfire.foodorder.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsConstants;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.FilterActivity;
import com.opalfire.foodorder.activities.SetDeliveryLocationActivity;
import com.opalfire.foodorder.adapter.BannerAdapter;
import com.opalfire.foodorder.adapter.DiscoverAdapter;
import com.opalfire.foodorder.adapter.DiscoverAdapter.ClickListener;
import com.opalfire.foodorder.adapter.OfferRestaurantAdapter;
import com.opalfire.foodorder.adapter.RestaurantsAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.ConnectionHelper;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Address;
import com.opalfire.foodorder.models.Banner;
import com.opalfire.foodorder.models.Cuisine;
import com.opalfire.foodorder.models.Discover;
import com.opalfire.foodorder.models.Restaurant;
import com.opalfire.foodorder.models.RestaurantsData;
import com.opalfire.foodorder.models.Shop;
import com.opalfire.foodorder.utils.Utils;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnItemSelectedListener {
    public static ArrayList<Integer> cuisineSelectedList = null;
    public static boolean isFilterApplied = false;
    int ADDRESS_SELECTION = 1;
    int FILTER_APPLIED_CHECK = 2;
    Activity activity;
    RestaurantsAdapter adapterRestaurant;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    AnimatedVectorDrawableCompat avdProgress;
    BannerAdapter bannerAdapter;
    List<Banner> bannerList;
    String[] catagoery = new String[]{"Relevance", "Cost for Two", "Delivery Time", "Rating"};
    ConnectionHelper connectionHelper;
    Context context;
    @BindView(R.id.animation_line_image)
    ImageView animationLineImage;
    Runnable action = new Runnable() {
        @Override
        public void run() {
            avdProgress.stop();
            if (animationLineImage != null) {
                animationLineImage.setVisibility(View.INVISIBLE);
            }
        }
    };
    @BindView(R.id.offer_title_header)
    TextView offerTitleHeader;
    @BindView(R.id.impressive_dishes_rv)
    RecyclerView impressiveDishesRv;
    @BindView(R.id.impressive_dishes_layout)
    LinearLayout impressiveDishesLayout;
    @BindView(R.id.restaurant_count_txt)
    TextView restaurantCountTxt;
    @BindView(R.id.catagoery_spinner)
    Spinner catagoerySpinner;
    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.restaurants_rv)
    RecyclerView restaurantsRv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.discover_rv)
    RecyclerView discoverRv;
    @BindView(R.id.restaurants_offer_rv)
    RecyclerView restaurantsOfferRv;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    @BindView(R.id.scrollView)
    StickyScrollView scrollView;
    private TextView addressLabel;
    private TextView addressTxt;
    private RelativeLayout errorLoadingLayout;
    private Button filterBtn;
    private LinearLayout locationAddressLayout;
    private SkeletonScreen skeletonScreen;
    private SkeletonScreen skeletonScreen2;
    private SkeletonScreen skeletonSpinner;
    private SkeletonScreen skeletonText1;
    private SkeletonScreen skeletonText2;
    private ViewGroup toolbar;
    private View toolbarLayout;
    private ArrayList<Shop> shopList;
    private ImageView filterSelectionImage;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.d("receiver ", message);
            errorLoadingLayout.setVisibility(View.GONE);
            int i = 0;
            locationAddressLayout.setVisibility(View.VISIBLE);
            if ((GlobalData.selectedAddress != null) && (GlobalData.profileModel != null)) {
                GlobalData.addressHeader = GlobalData.selectedAddress.getType();
                addressLabel.setText(GlobalData.selectedAddress.getType());
                addressTxt.setText(GlobalData.selectedAddress.getMapAddress());
                GlobalData.latitude = GlobalData.selectedAddress.getLatitude();
                GlobalData.longitude = GlobalData.selectedAddress.getLongitude();
                GlobalData.addressHeader = GlobalData.selectedAddress.getMapAddress();
            } else {
                if ((GlobalData.addressList != null) && (GlobalData.addressList.getAddresses().size() != 0) && (GlobalData.profileModel != null)) {
                }
                while (i < GlobalData.addressList.getAddresses().size()) {
                    Address address = GlobalData.addressList.getAddresses().get(i);
                    if ((getDoubleThreeDigits(GlobalData.latitude) == getDoubleThreeDigits(address.getLatitude())) && (getDoubleThreeDigits(GlobalData.longitude) == getDoubleThreeDigits(address.getLongitude()))) {
                        GlobalData.selectedAddress = address;
                        addressLabel.setText(GlobalData.addressHeader);
                        addressTxt.setText(GlobalData.address);
                        addressLabel.setText(GlobalData.selectedAddress.getType());
                        addressTxt.setText(GlobalData.selectedAddress.getMapAddress());
                        GlobalData.latitude = GlobalData.selectedAddress.getLatitude();
                        GlobalData.longitude = GlobalData.selectedAddress.getLongitude();
                    } else {
                        addressLabel.setText(GlobalData.addressHeader);
                        addressTxt.setText(GlobalData.address);
                        i += 1;
                    }
                }
            }
            findRestaurant();
        }
    };
    private Unbinder unbinder;

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        context = getContext();
        activity = getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        System.out.println("HomeFragment");
        connectionHelper = new ConnectionHelper(context);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbarLayout = LayoutInflater.from(context).inflate(R.layout.toolbar_home, toolbar, false);
        addressLabel = toolbarLayout.findViewById(R.id.address_label);
        addressTxt = toolbarLayout.findViewById(R.id.address);
        locationAddressLayout = toolbarLayout.findViewById(R.id.location_ll);
        errorLoadingLayout = toolbarLayout.findViewById(R.id.error_loading_layout);
        locationAddressLayout.setVisibility(View.INVISIBLE);
        errorLoadingLayout.setVisibility(View.VISIBLE);
        bannerList = new ArrayList<>();
        bannerAdapter = new BannerAdapter(bannerList, context, getActivity());
        impressiveDishesRv.setHasFixedSize(true);
        impressiveDishesRv.setItemViewCacheSize(20);
        impressiveDishesRv.setDrawingCacheEnabled(true);
        impressiveDishesRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        impressiveDishesRv.setLayoutManager(new LinearLayoutManager(context, 0, false));
        impressiveDishesRv.setItemAnimator(new DefaultItemAnimator());
        skeletonScreen2 = Skeleton.bind(impressiveDishesRv).adapter(bannerAdapter).load(R.layout.skeleton_impressive_list_item).count(3).show();
        skeletonText1 = Skeleton.bind(offerTitleHeader).load(R.layout.skeleton_label).show();
        skeletonText2 = Skeleton.bind(restaurantCountTxt).load(R.layout.skeleton_label).show();
        skeletonSpinner = Skeleton.bind(catagoerySpinner).load(R.layout.skeleton_label).show();
        HomeActivity.updateNotificationCount(context, GlobalData.notificationCount);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, catagoery);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        catagoerySpinner.setAdapter(arrayAdapter);
        catagoerySpinner.setOnItemSelectedListener(this);
        restaurantsRv.setLayoutManager(new LinearLayoutManager(context, 1, false));
        restaurantsRv.setItemAnimator(new DefaultItemAnimator());
        restaurantsRv.setHasFixedSize(true);
        shopList = new ArrayList<>();
        adapterRestaurant = new RestaurantsAdapter(shopList, context, getActivity());
        skeletonScreen = Skeleton.bind(restaurantsRv).adapter(adapterRestaurant).load(R.layout.skeleton_restaurant_list_item).count(2).show();
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("Madras Coffee House", "Cafe, South Indian", "", "3.8", "51 Mins", "$20", ""));
        restaurantList.add(new Restaurant("Behrouz Biryani", "Biriyani", "", "3.7", "52 Mins", "$50", ""));
        restaurantList.add(new Restaurant("SubWay", "American fast food", "Flat 20% offer on all orders", "4.3", "30 Mins", "$5", "Close soon"));
        restaurantList.add(new Restaurant("Dominoz Pizza", "Pizza shop", "", "4.5", "25 Mins", "$5", ""));
        restaurantList.add(new Restaurant("Pizza hut", "Cafe, Bakery", "", "4.1", "45 Mins", "$5", "Close soon"));
        restaurantList.add(new Restaurant("McDonlad's", "Pizza Food, burger", "Flat 20% offer on all orders", "4.6", "20 Mins", "$5", ""));
        restaurantList.add(new Restaurant("Chai Kings", "Cafe, Bakery", "", "3.3", "36 Mins", "$5", ""));
        restaurantList.add(new Restaurant("sea sell", "Fish, Chicken, mutton", "Flat 30% offer on all orders", "4.3", "20 Mins", "$5", "Close soon"));
        restaurantsOfferRv.setLayoutManager(new LinearLayoutManager(context, 0, false));
        restaurantsOfferRv.setItemAnimator(new DefaultItemAnimator());
        restaurantsOfferRv.setHasFixedSize(true);
        restaurantsOfferRv.setAdapter(new OfferRestaurantAdapter(restaurantList, context));
        final List<Discover> discoverList = new ArrayList<>();
        discoverList.add(new Discover("Trending now ", "22 options", AppEventsConstants.EVENT_PARAM_VALUE_YES));
        discoverList.add(new Discover("Offers near you", "51 options", AppEventsConstants.EVENT_PARAM_VALUE_YES));
        discoverList.add(new Discover("Whats special", "7 options", AppEventsConstants.EVENT_PARAM_VALUE_YES));
        discoverList.add(new Discover("Pocket Friendly", "44 options", AppEventsConstants.EVENT_PARAM_VALUE_YES));
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discoverList, context);
        discoverRv.setLayoutManager(new LinearLayoutManager(context, 0, false));
        discoverRv.setItemAnimator(new DefaultItemAnimator());
        discoverRv.setAdapter(discoverAdapter);
        discoverAdapter.setOnItemClickListener(new ClickListener() {
            public void onItemClick(int i, View view) {
                Discover discover = discoverList.get(i);
            }
        });
        locationAddressLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalData.profileModel != null) {
                    startActivityForResult(new Intent(getActivity(), SetDeliveryLocationActivity.class).putExtra("get_address", true).putExtra("home_page", true), ADDRESS_SELECTION);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                    return;
                }
                Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show();
            }
        });
        filterBtn = toolbarLayout.findViewById(R.id.filter);
        filterSelectionImage = toolbarLayout.findViewById(R.id.filter_selection_image);
        filterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, FilterActivity.class), FILTER_APPLIED_CHECK);
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.anim_nothing);
            }
        });
        toolbar.addView(toolbarLayout);
        initializeAvd();
        if (connectionHelper.isConnectingToInternet()) {
            getCuisines();
        } else {
            Utils.displayMessage(activity, context, getString(R.string.oops_connect_your_internet));
        }
    }

    private void findRestaurant() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("latitude", String.valueOf(GlobalData.latitude));
        hashMap.put("longitude", String.valueOf(GlobalData.longitude));
        if (GlobalData.profileModel != null) {
            hashMap.put(AccessToken.USER_ID_KEY, String.valueOf(GlobalData.profileModel.getId()));
        }
        if (isFilterApplied) {
            int i = 0;
            filterSelectionImage.setVisibility(View.VISIBLE);
            if (GlobalData.isOfferApplied) {
                hashMap.put("offer", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            }
            if (GlobalData.isPureVegApplied) {
                hashMap.put("pure_veg", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            }
            if (!(GlobalData.cuisineIdArrayList == null || GlobalData.cuisineIdArrayList.size() == 0)) {
                while (i < GlobalData.cuisineIdArrayList.size()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("cuisine[");
                    stringBuilder.append(i);
                    stringBuilder.append("]");
                    hashMap.put(stringBuilder.toString(), GlobalData.cuisineIdArrayList.get(i).toString());
                    i++;
                }
            }
        } else {
            filterSelectionImage.setVisibility(View.GONE);
        }
        if (connectionHelper.isConnectingToInternet()) {
            getRestaurant(hashMap);
        } else {
            Utils.displayMessage(activity, context, getString(R.string.oops_connect_your_internet));
        }
    }


    private void getRestaurant(HashMap<String, String> paramHashMap) {
        this.apiInterface.getshops(paramHashMap).enqueue(new Callback<RestaurantsData>() {
            public void onFailure(Call<RestaurantsData> call, Throwable th) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                skeletonScreen.hide();
                skeletonScreen2.hide();
                skeletonText1.hide();
                skeletonText2.hide();
                skeletonSpinner.hide();
                if (response.body() != null) {
                    if (!response.isSuccessful() && response.errorBody() != null) {
                        try {
                            JSONObject errObj = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, errObj.optString("message"), Toast.LENGTH_LONG).show();
                            return;
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getShops().size() == 0) {
                            title.setVisibility(View.GONE);
                            errorLayout.setVisibility(View.VISIBLE);
                        } else {
                            title.setVisibility(View.VISIBLE);
                            errorLayout.setVisibility(View.GONE);
                        }
                        if ((response.body().getBanners().size() != 0) && (!HomeFragment.isFilterApplied)) {
                            impressiveDishesLayout.setVisibility(View.VISIBLE);
                        } else {
                            impressiveDishesLayout.setVisibility(View.GONE);
                        }
                        GlobalData.shopList = response.body().getShops();
                        shopList.clear();
                        shopList.addAll(GlobalData.shopList);
                        bannerList.clear();
                        bannerList.addAll(response.body().getBanners());
                        restaurantCountTxt.setText(shopList.size() + " Restaurants");
                        adapterRestaurant.notifyDataSetChanged();
                        bannerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void getCuisines() {
        this.apiInterface.getcuCuisineCall().enqueue(new Callback<List<Cuisine>>() {
            @Override
            public void onFailure(Call<List<Cuisine>> call, Throwable th) {
            }

            @Override
            public void onResponse(Call<List<Cuisine>> call, Response<List<Cuisine>> response) {
                if (response.body() != null) {
                    if (!response.isSuccessful() && response.errorBody() != null) {
                        try {
                            JSONObject errObj = new JSONObject(response.errorBody().string());
                            Toast.makeText(HomeFragment.this.context, errObj.optString("message"), Toast.LENGTH_LONG).show();
                            return;
                        } catch (Exception paramAnonymousCall) {
                            Toast.makeText(HomeFragment.this.context, paramAnonymousCall.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    if (response.isSuccessful()) {
                        GlobalData.cuisineList = new ArrayList<>();
                        GlobalData.cuisineList.addAll(response.body());
                    }
                }
            }
        });
    }

    public double getDoubleThreeDigits(Double d) {
        return new BigDecimal(d.toString()).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    private void initializeAvd() {
        avdProgress = AnimatedVectorDrawableCompat.create(context, R.drawable.avd_line);
        animationLineImage.setBackground(avdProgress);
        repeatAnimation();
    }

    private void repeatAnimation() {
        avdProgress.start();
        animationLineImage.postDelayed(action, 3000);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        errorLayout.setVisibility(View.GONE);
        HomeActivity.updateNotificationCount(context, GlobalData.notificationCount);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("location"));
        if (!GlobalData.addressHeader.equalsIgnoreCase("")) {
            errorLoadingLayout.setVisibility(View.GONE);
            int i = 0;
            locationAddressLayout.setVisibility(View.VISIBLE);
            if (GlobalData.selectedAddress == null || GlobalData.profileModel == null) {
                if (GlobalData.addressList != null && GlobalData.addressList.getAddresses().size() != 0 && GlobalData.profileModel != null) {
                    while (i < GlobalData.addressList.getAddresses().size()) {
                        Address address = GlobalData.addressList.getAddresses().get(i);
                        if (getDoubleThreeDigits(GlobalData.latitude) == getDoubleThreeDigits(address.getLatitude()) && getDoubleThreeDigits(Double.valueOf(GlobalData.longitude)) == getDoubleThreeDigits(address.getLongitude())) {
                            GlobalData.selectedAddress = address;
                            addressLabel.setText(GlobalData.addressHeader);
                            addressTxt.setText(GlobalData.address);
                            addressLabel.setText(GlobalData.selectedAddress.getType());
                            addressTxt.setText(GlobalData.selectedAddress.getMapAddress());
                            GlobalData.latitude = GlobalData.selectedAddress.getLatitude();
                            GlobalData.longitude = GlobalData.selectedAddress.getLongitude();
                            break;
                        }
                        addressLabel.setText(GlobalData.addressHeader);
                        addressTxt.setText(GlobalData.address);
                        i++;
                    }
                } else {
                    addressLabel.setText(GlobalData.addressHeader);
                    addressTxt.setText(GlobalData.address);
                }
            } else {
                GlobalData.addressHeader = GlobalData.selectedAddress.getType();
                addressLabel.setText(GlobalData.selectedAddress.getType());
                addressTxt.setText(GlobalData.selectedAddress.getMapAddress());
                GlobalData.latitude = GlobalData.selectedAddress.getLatitude().doubleValue();
                GlobalData.longitude = GlobalData.selectedAddress.getLongitude().doubleValue();
                GlobalData.addressHeader = GlobalData.selectedAddress.getMapAddress();
            }
            findRestaurant();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        context = context;
        activity = getActivity();
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (toolbar != null) {
            toolbar.removeView(toolbarLayout);
        }
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        unbinder.unbind();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == ADDRESS_SELECTION && i2 == -1) {
            System.out.print("HomeFragment : Success");
            if (GlobalData.selectedAddress != null) {
                addressLabel.setText(GlobalData.addressHeader);
                addressTxt.setText(GlobalData.address);
                addressLabel.setText(GlobalData.selectedAddress.getType());
                addressTxt.setText(GlobalData.selectedAddress.getMapAddress());
                GlobalData.latitude = GlobalData.selectedAddress.getLatitude();
                GlobalData.longitude = GlobalData.selectedAddress.getLongitude();
                skeletonScreen.show();
                skeletonScreen2.show();
                skeletonText1.show();
                skeletonText2.show();
                skeletonSpinner.show();
                findRestaurant();
            }
        } else if (i == ADDRESS_SELECTION && i2 == 0) {
            System.out.print("HomeFragment : Failure");
        }
        if (i == FILTER_APPLIED_CHECK && i2 == -1) {
            System.out.print("HomeFragment : Filter Success");
            skeletonScreen.show();
            skeletonScreen2.show();
            skeletonText1.show();
            skeletonText2.show();
            skeletonSpinner.show();
            findRestaurant();
        } else if (i == ADDRESS_SELECTION && i2 == 0) {
            System.out.print("HomeFragment : Filter Failure");
        }
    }

    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }


}
