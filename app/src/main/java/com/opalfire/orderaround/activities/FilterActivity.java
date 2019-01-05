package com.opalfire.orderaround.activities;

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
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FilterActivity extends AppCompatActivity implements OnClickListener {
    public static Button applyFilterBtn = null;
    public static boolean isReset = false;
    public static TextView resetTxt;
    @BindView(R.id.close_img)
    ImageView closeImg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.filter_rv)
    RecyclerView filterRv;
    @BindView(R.id.apply_filter)
    Button applyFilter;
    private FilterAdapter adapter;
    private List<FilterModel> modelListReference = new ArrayList<>();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        getWindow().setFlags(1024, 1024);
        applyFilterBtn = findViewById(R.id.apply_filter);
        resetTxt = findViewById(R.id.reset_txt);
        closeImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_nothing, R.anim.slide_down);
            }
        });
        List<Cuisine> cuisineList = new ArrayList<>();
        Cuisine cuisine = new Cuisine();
        cuisine.setName("Offers");
        Cuisine cuisine2 = new Cuisine();
        cuisine2.setName("Pure veg");
        List<Cuisine> cuisineList1 = new ArrayList();
        cuisineList1.add(cuisine);
        cuisineList1.add(cuisine2);
        FilterModel filterModel = new FilterModel();
        filterModel.setHeader("Show Restaurants With");
        filterModel.setCuisines(cuisineList1);
        modelListReference.add(filterModel);
        List<String> cuisineStr = new ArrayList<>();
        if (GlobalData.cuisineList != null) {
            for (Cuisine name : GlobalData.cuisineList) {
                cuisineStr.add(name.getName());
            }
            filterModel = new FilterModel();
            filterModel.setHeader("Cuisines");
            filterModel.setCuisines(GlobalData.cuisineList);
            modelListReference.add(filterModel);
            modelListReference.clear();
            modelListReference.addAll(modelListReference);
            filterRv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new FilterAdapter(this, modelListReference);
            isReset = !HomeFragment.isFilterApplied;
            filterRv.setAdapter(adapter);
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

    @OnClick({R.id.apply_filter, R.id.reset_txt})
    public void onClick(View view) {
        if (view.getId() == R.id.apply_filter) {
            GlobalData.isPureVegApplied = FilterAdapter.isPureVegApplied;
            GlobalData.isOfferApplied = FilterAdapter.isOfferApplied;
            GlobalData.cuisineIdArrayList = new ArrayList<>();
            GlobalData.cuisineIdArrayList.addAll(FilterAdapter.cuisineIdList);
            HomeFragment.isFilterApplied = GlobalData.isOfferApplied;
            if (GlobalData.isPureVegApplied) {
                HomeFragment.isFilterApplied = true;
            }
            if (!(GlobalData.cuisineIdArrayList == null || GlobalData.cuisineIdArrayList.size() == 0)) {
                HomeFragment.isFilterApplied = true;
            }
            setResult(-1, new Intent());
            finish();
        } else if (view.getId() == R.id.reset_txt) {
            isReset = true;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

}
