package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.FilterAdapter;
import com.opalfire.orderaround.fragments.HomeFragment;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Cuisine;
import com.opalfire.orderaround.models.FilterModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FilterActivity extends AppCompatActivity implements OnClickListener {
    public static Button applyFilterBtn = null;
    public static boolean isReset = false;
    public static TextView resetTxt;
    @BindView(2131296417)
    ImageView closeImg;
    @BindView(2131296548)
    RecyclerView filterRv;
    @BindView(2131296914)
    Toolbar toolbar;
    private FilterAdapter adapter;
    private List<FilterModel> modelListReference = new ArrayList();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_filter);
        ButterKnife.bind((Activity) this);
        getWindow().setFlags(1024, 1024);
        applyFilterBtn = (Button) findViewById(R.id.apply_filter);
        resetTxt = (TextView) findViewById(R.id.reset_txt);
        this.closeImg.setOnClickListener(new C07291());
        bundle = new ArrayList();
        ArrayList arrayList = new ArrayList();
        Cuisine cuisine = new Cuisine();
        cuisine.setName("Offers");
        Cuisine cuisine2 = new Cuisine();
        cuisine2.setName("Pure veg");
        List arrayList2 = new ArrayList();
        arrayList2.add(cuisine);
        arrayList2.add(cuisine2);
        FilterModel filterModel = new FilterModel();
        filterModel.setHeader("Show Restaurants With");
        filterModel.setCuisines(arrayList2);
        bundle.add(filterModel);
        List arrayList3 = new ArrayList();
        if (GlobalData.cuisineList != null) {
            for (Cuisine name : GlobalData.cuisineList) {
                arrayList3.add(name.getName());
            }
            filterModel = new FilterModel();
            filterModel.setHeader("Cuisines");
            filterModel.setCuisines(GlobalData.cuisineList);
            bundle.add(filterModel);
            this.modelListReference.clear();
            this.modelListReference.addAll(bundle);
            this.filterRv.setLayoutManager(new LinearLayoutManager(this));
            this.adapter = new FilterAdapter(this, this.modelListReference);
            if (HomeFragment.isFilterApplied != null) {
                isReset = null;
            } else {
                isReset = true;
            }
            this.filterRv.setAdapter(this.adapter);
        }
        resetTxt.setOnClickListener(this);
        applyFilterBtn.setOnClickListener(this);
    }

    public void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
        super.onBackPressed();
        FilterAdapter.cuisineIdList.clear();
        isReset = true;
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_down);
    }

    public void onClick(View view) {
        view = view.getId();
        if (view == R.id.apply_filter) {
            GlobalData.isPureVegApplied = FilterAdapter.isPureVegApplied;
            GlobalData.isOfferApplied = FilterAdapter.isOfferApplied;
            GlobalData.cuisineIdArrayList = new ArrayList();
            GlobalData.cuisineIdArrayList.addAll(FilterAdapter.cuisineIdList);
            HomeFragment.isFilterApplied = null;
            if (GlobalData.isOfferApplied != null) {
                HomeFragment.isFilterApplied = true;
            }
            if (GlobalData.isPureVegApplied != null) {
                HomeFragment.isFilterApplied = true;
            }
            if (!(GlobalData.cuisineIdArrayList == null || GlobalData.cuisineIdArrayList.size() == null)) {
                HomeFragment.isFilterApplied = true;
            }
            setResult(-1, new Intent());
            finish();
        } else if (view == R.id.reset_txt) {
            isReset = true;
            this.adapter.notifyDataSetChanged();
        }
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    /* renamed from: com.entriver.orderaround.activities.FilterActivity$1 */
    class C07291 implements OnClickListener {
        C07291() {
        }

        public void onClick(View view) {
            FilterActivity.this.finish();
            FilterActivity.this.overridePendingTransition(R.anim.anim_nothing, R.anim.slide_down);
        }
    }
}
