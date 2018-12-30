package com.opalfire.foodorder.fragments;

import android.content.Context;
import android.os.Bundle;
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
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    String input = "";
    ProgressBar progressBar;
    @BindView(2131296771)
    TextView relatedTxt;
    @BindView(2131296793)
    RelativeLayout rootLayout;
    ImageView searchCloseImg;
    @BindView(2131296880)
    TabLayout tabLayout;
    Unbinder unbinder;
    @BindView(2131296964)
    ViewPager viewPager;
    private Context context;
    private ViewGroup toolbar;
    private View toolbarLayout;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = getContext();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_search, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        return inflate;
    }

    public void onResume() {
        super.onResume();
        HomeActivity.updateNotificationCount(this.context, GlobalData.notificationCount);
        if (!this.input.equalsIgnoreCase("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("name", this.input);
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
        this.context = context;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.toolbar != null) {
            this.toolbar.removeView(this.toolbarLayout);
        }
        this.unbinder.unbind();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        System.out.println("SearchFragment");
        GlobalData.searchShopList = new ArrayList();
        GlobalData.searchProductList = new ArrayList();
        this.toolbar = (ViewGroup) getActivity().findViewById(R.id.toolbar);
        this.toolbar.setVisibility(View.VISIBLE);
        this.rootLayout.setVisibility(View.GONE);
        GlobalData.searchProductList = new ArrayList();
        GlobalData.searchShopList = new ArrayList();
        this.toolbarLayout = LayoutInflater.from(this.context).inflate(R.layout.toolbar_search, this.toolbar, false);
        searchEt = (EditText) this.toolbarLayout.findViewById(R.id.search_et);
        this.progressBar = (ProgressBar) this.toolbarLayout.findViewById(R.id.progress_bar);
        this.searchCloseImg = (ImageView) this.toolbarLayout.findViewById(R.id.search_close_img);
        this.adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        this.adapter.addFragment(new RestaurantSearchFragment(), "RESTAURANT");
        this.adapter.addFragment(new ProductSearchFragment(), "DISHES");
        this.viewPager.setOffscreenPageLimit(2);
        this.viewPager.addOnPageChangeListener(new C14101());
        this.viewPager.setAdapter(this.adapter);
        this.tabLayout.setupWithViewPager(this.viewPager);
        searchEt.addTextChangedListener(new C08562());
        this.toolbar.addView(this.toolbarLayout);
        HomeActivity.updateNotificationCount(this.context, GlobalData.notificationCount);
        this.searchCloseImg.setOnClickListener(new C08573());
    }

    private void getSearch(HashMap hashMap) {
        this.progressBar.setVisibility(View.VISIBLE);
        this.apiInterface.getSearch(hashMap).enqueue(new C14114());
    }

    /* renamed from: com.entriver.orderaround.fragments.SearchFragment$2 */
    class C08562 implements TextWatcher {
        C08562() {
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.length() != 0) {
                SearchFragment.this.input = charSequence.toString();
                i = new HashMap();
                i.put("name", charSequence.toString());
                if (GlobalData.profileModel != 0) {
                    i.put(AccessToken.USER_ID_KEY, GlobalData.profileModel.getId().toString());
                }
                SearchFragment.this.getSearch(i);
                SearchFragment.this.searchCloseImg.setVisibility(View.VISIBLE);
                SearchFragment.this.rootLayout.setVisibility(View.VISIBLE);
                i = SearchFragment.this.relatedTxt;
                i2 = new StringBuilder();
                i2.append("Related to \"");
                i2.append(charSequence.toString());
                i2.append("\"");
                i.setText(i2.toString());
            } else if (charSequence.length() == 0) {
                SearchFragment.this.relatedTxt.setText("Related to ");
                SearchFragment.this.searchCloseImg.setVisibility(View.GONE);
                SearchFragment.this.rootLayout.setVisibility(View.GONE);
                GlobalData.searchShopList.clear();
                GlobalData.searchProductList.clear();
                SearchFragment.this.relatedTxt.setText(charSequence.toString());
                RestaurantSearchFragment.restaurantsAdapter.notifyDataSetChanged();
            }
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.SearchFragment$3 */
    class C08573 implements OnClickListener {
        C08573() {
        }

        public void onClick(View view) {
            SearchFragment.searchEt.setText("");
            GlobalData.searchShopList.clear();
            GlobalData.searchProductList.clear();
            ProductSearchFragment.productsAdapter.notifyDataSetChanged();
            RestaurantSearchFragment.restaurantsAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.SearchFragment$1 */
    class C14101 implements OnPageChangeListener {
        C14101() {
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.SearchFragment$4 */
    class C14114 implements Callback<Search> {
        C14114() {
        }

        public void onResponse(Call<Search> call, Response<Search> response) {
            SearchFragment.this.progressBar.setVisibility(View.GONE);
            if (response != null && response.isSuccessful() == null && response.errorBody() != null) {
                try {
                    Toast.makeText(SearchFragment.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<Search> response2) {
                    Toast.makeText(SearchFragment.this.context, response2.getMessage(), 1).show();
                }
            } else if (response2.isSuccessful() != null) {
                SearchFragment.this.progressBar.setVisibility(View.GONE);
                GlobalData.searchShopList.clear();
                GlobalData.searchProductList.clear();
                GlobalData.searchShopList.addAll(((Search) response2.body()).getShops());
                GlobalData.searchProductList.addAll(((Search) response2.body()).getProducts());
                ProductSearchFragment.productsAdapter.notifyDataSetChanged();
                RestaurantSearchFragment.restaurantsAdapter.notifyDataSetChanged();
            }
        }

        public void onFailure(Call<Search> call, Throwable th) {
            SearchFragment.this.progressBar.setVisibility(View.GONE);
        }
    }
}
