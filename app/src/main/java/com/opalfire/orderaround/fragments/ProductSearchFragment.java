package com.opalfire.orderaround.fragments;

import android.os.Bundle;
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
    @BindView(2131296746)
    RecyclerView productRv;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_product_search, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        productsAdapter = new ProductsAdapter(getActivity(), getActivity(), GlobalData.searchProductList);
        this.productRv.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.productRv.setItemAnimator(new DefaultItemAnimator());
        this.productRv.setAdapter(productsAdapter);
    }

    public void onResume() {
        super.onResume();
        productsAdapter.notifyDataSetChanged();
    }
}
