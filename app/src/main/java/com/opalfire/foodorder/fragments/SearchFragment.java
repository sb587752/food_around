package com.opalfire.foodorder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.ProductsAdapter;
import com.opalfire.foodorder.adapter.ViewPagerAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Search;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    public static EditText searchEt;
    ViewPagerAdapter adapter;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    String input = "";
    ProgressBar progressBar;
    ImageView searchCloseImg;
    Unbinder unbinder;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.related_txt)
    TextView relatedTxt;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;
    private Context mContext;
    private ViewGroup toolbar;
    private View toolbarLayout;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mContext = getContext();
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_search, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onResume() {
        super.onResume();
        HomeActivity.updateNotificationCount(mContext, GlobalData.notificationCount);
        if (!input.equalsIgnoreCase("")) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", input);
            if (GlobalData.profileModel != null) {
                hashMap.put(AccessToken.USER_ID_KEY, GlobalData.profileModel.getId().toString());
            }
            getSearch(hashMap);
        }
        if (ProductsAdapter.bottomSheetDialogFragment != null) {
            ProductsAdapter.bottomSheetDialogFragment.dismiss();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (toolbar != null) {
            toolbar.removeView(toolbarLayout);
        }
        unbinder.unbind();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        System.out.println("SearchFragment");
        GlobalData.searchShopList = new ArrayList();
        GlobalData.searchProductList = new ArrayList();
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
        GlobalData.searchProductList = new ArrayList();
        GlobalData.searchShopList = new ArrayList();
        toolbarLayout = LayoutInflater.from(mContext).inflate(R.layout.toolbar_search, toolbar, false);
        searchEt = toolbarLayout.findViewById(R.id.search_et);
        progressBar = toolbarLayout.findViewById(R.id.progress_bar);
        searchCloseImg = toolbarLayout.findViewById(R.id.search_close_img);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new RestaurantSearchFragment(), "RESTAURANT");
        adapter.addFragment(new ProductSearchFragment(), "DISHES");
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    input = charSequence.toString();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", charSequence.toString());
                    if (GlobalData.profileModel != null) {
                        hashMap.put("user_id", GlobalData.profileModel.getId().toString());
                    }
                    getSearch(hashMap);
                    searchCloseImg.setVisibility(View.VISIBLE);
                    rootLayout.setVisibility(View.VISIBLE);
                    String localStringBuilder = "Related to \"" +
                            charSequence.toString() +
                            "\"";
                    relatedTxt.setText(localStringBuilder);
                    return;
                }
                if (charSequence.length() == 0) {
                    relatedTxt.setText("Related to ");
                    searchCloseImg.setVisibility(View.GONE);
                    rootLayout.setVisibility(View.GONE);
                    GlobalData.searchShopList.clear();
                    GlobalData.searchProductList.clear();
                    relatedTxt.setText(charSequence.toString());
                    RestaurantSearchFragment.restaurantsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        toolbar.addView(toolbarLayout);
        HomeActivity.updateNotificationCount(mContext, GlobalData.notificationCount);
        searchCloseImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment.searchEt.setText("");
                GlobalData.searchShopList.clear();
                GlobalData.searchProductList.clear();
                ProductSearchFragment.productsAdapter.notifyDataSetChanged();
                RestaurantSearchFragment.restaurantsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getSearch(HashMap<String, String> paramHashMap) {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getSearch(paramHashMap).enqueue(new Callback<Search>() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                progressBar.setVisibility(View.GONE);
                if ((!response.isSuccessful()) && (response.errorBody() != null)) {
                    try {
                        JSONObject errObj = new JSONObject(response.errorBody().string());
                        Toast.makeText(mContext, errObj.optString("message"), Toast.LENGTH_LONG).show();
                        return;
                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    GlobalData.searchShopList.clear();
                    GlobalData.searchProductList.clear();
                    GlobalData.searchShopList.addAll(((Search) response.body()).getShops());
                    GlobalData.searchProductList.addAll(((Search) response.body()).getProducts());
                    ProductSearchFragment.productsAdapter.notifyDataSetChanged();
                    RestaurantSearchFragment.restaurantsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}
