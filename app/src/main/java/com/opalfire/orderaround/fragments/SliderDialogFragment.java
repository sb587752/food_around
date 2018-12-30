package com.opalfire.orderaround.fragments;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.SliderPagerAdapter;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SliderDialogFragment extends DialogFragment {
    @BindView(2131296747)
    ViewPager productSlider;
    @BindView(2131296748)
    LinearLayout productSliderDots;
    SliderPagerAdapter sliderPagerAdapter;
    List<Image> slider_image_list = new ArrayList();
    Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_slider_dialog, viewGroup);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.slider_image_list.addAll(GlobalData.isSelectedProduct.getImages());
        this.sliderPagerAdapter = new SliderPagerAdapter(getActivity(), this.slider_image_list, Boolean.valueOf(false));
        this.productSlider.setAdapter(this.sliderPagerAdapter);
        this.productSlider.addOnPageChangeListener(new C14121());
        addBottomDots(View.VISIBLE);
        return inflate;
    }

    private void addBottomDots(int i) {
        TextView[] textViewArr = new TextView[this.slider_image_list.size()];
        this.productSliderDots.removeAllViews();
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            textViewArr[i2] = new TextView(getActivity());
            textViewArr[i2].setText(Html.fromHtml("&#8226;"));
            textViewArr[i2].setTextSize(35.0f);
            textViewArr[i2].setTextColor(Color.parseColor("#000000"));
            this.productSliderDots.addView(textViewArr[i2]);
        }
        if (textViewArr.length > 0) {
            textViewArr[i].setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public void onStart() {
        super.onStart();
    }

    @OnClick({2131296417})
    public void onViewClicked() {
        dismiss();
    }

    /* renamed from: com.entriver.orderaround.fragments.SliderDialogFragment$1 */
    class C14121 implements OnPageChangeListener {
        C14121() {
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            SliderDialogFragment.this.addBottomDots(i);
        }
    }
}
