package com.opalfire.orderaround.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderViewFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_order_view, viewGroup, false);
        unbinder = ButterKnife.bind((Object) this, inflate);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragment(new OrderDetailFragment(), "DETAILS");
        viewPagerAdapter.addFragment(new OrderHelpFragment(), "HELP");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
