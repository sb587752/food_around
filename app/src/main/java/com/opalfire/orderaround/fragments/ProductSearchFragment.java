package com.opalfire.orderaround.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.ProductsAdapter;
import com.opalfire.orderaround.helper.GlobalData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductSearchFragment extends Fragment {
    public static ProductsAdapter productsAdapter;

    Unbinder unbinder;
    @BindView(R.id.product_rv)
    RecyclerView productRv;
    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_product_search, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        mContext=view.getContext();
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        productsAdapter = new ProductsAdapter(mContext, getActivity(), GlobalData.searchProductList);
        productRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        productRv.setItemAnimator(new DefaultItemAnimator());
        productRv.setAdapter(productsAdapter);
    }

    public void onResume() {
        super.onResume();
        productsAdapter.notifyDataSetChanged();
    }
}
