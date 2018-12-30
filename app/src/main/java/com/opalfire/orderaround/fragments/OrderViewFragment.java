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
    @BindView(2131296880)
    TabLayout tabLayout;
    Unbinder unbinder;
    @BindView(2131296962)
    View viewLine4;
    @BindView(2131296964)
    ViewPager viewPager;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_order_view, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        viewGroup = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewGroup.addFragment(new OrderDetailFragment(), "DETAILS");
        viewGroup.addFragment(new OrderHelpFragment(), "HELP");
        this.viewPager.setAdapter(viewGroup);
        this.tabLayout.setupWithViewPager(this.viewPager);
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
