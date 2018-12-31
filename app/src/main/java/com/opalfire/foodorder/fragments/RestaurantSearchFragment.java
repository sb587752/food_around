package com.opalfire.foodorder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.RestaurantsAdapter;
import com.opalfire.foodorder.helper.GlobalData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RestaurantSearchFragment extends Fragment {
    public static RestaurantsAdapter restaurantsAdapter;
    Context context;
    Unbinder unbinder;
    @BindView(R.id.restaurants_rv)
    RecyclerView restaurantsRv;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_restaurant, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.context = getActivity();
        return inflate;
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.restaurantsRv.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false));
        this.restaurantsRv.setItemAnimator(new DefaultItemAnimator());
        this.restaurantsRv.setHasFixedSize(true);
        restaurantsAdapter = new RestaurantsAdapter(GlobalData.searchShopList, this.context, getActivity());
        this.restaurantsRv.setAdapter(restaurantsAdapter);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
