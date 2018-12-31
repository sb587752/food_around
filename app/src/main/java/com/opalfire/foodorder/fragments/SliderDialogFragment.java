package com.opalfire.foodorder.fragments;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.SliderPagerAdapter;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SliderDialogFragment extends DialogFragment {

    SliderPagerAdapter sliderPagerAdapter;
    List<Image> slider_image_list = new ArrayList<>();
    Unbinder unbinder;
    @BindView(R.id.close_img)
    ImageView closeImg;
    @BindView(R.id.product_slider)
    ViewPager productSlider;
    @BindView(R.id.product_slider_dots)
    LinearLayout productSliderDots;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_slider_dialog, viewGroup);
        unbinder = ButterKnife.bind(this, inflate);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        slider_image_list.addAll(GlobalData.isSelectedProduct.getImages());
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list, Boolean.FALSE);
        productSlider.setAdapter(sliderPagerAdapter);
        productSlider.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                addBottomDots(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        addBottomDots(View.VISIBLE);
        return inflate;
    }

    private void addBottomDots(int i) {
        TextView[] textViewArr = new TextView[slider_image_list.size()];
        productSliderDots.removeAllViews();
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            textViewArr[i2] = new TextView(getActivity());
            textViewArr[i2].setText(Html.fromHtml("&#8226;"));
            textViewArr[i2].setTextSize(35.0f);
            textViewArr[i2].setTextColor(Color.parseColor("#000000"));
            productSliderDots.addView(textViewArr[i2]);
        }
        if (textViewArr.length > 0) {
            textViewArr[i].setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onStart() {
        super.onStart();
    }

    @OnClick(R.id.close_img)
    public void onViewClicked() {
        dismiss();
    }

}
