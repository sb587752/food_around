package com.opalfire.orderaround.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.fragments.SliderDialogFragment;
import com.opalfire.orderaround.models.Image;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {
    private Activity activity;
    private List<Image> image_arraylist;
    private Boolean isClickable = Boolean.valueOf(false);

    public SliderPagerAdapter(Activity activity, List<Image> list, Boolean bool) {
        this.activity = activity;
        this.image_arraylist = list;
        this.isClickable = bool;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = ((LayoutInflater) this.activity.getSystemService("layout_inflater")).inflate(R.layout.layout_slider, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.im_slider);
        Glide.with(this.activity.getApplicationContext()).load(((Image) this.image_arraylist.get(i)).getUrl()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.ic_restaurant_place_holder).error((int) R.drawable.ic_restaurant_place_holder)).into(imageView);
        if (this.isClickable.booleanValue() != 0) {
            imageView.setOnClickListener(new C08281());
        }
        viewGroup.addView(inflate);
        return inflate;
    }

    public int getCount() {
        return this.image_arraylist.size();
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    /* renamed from: com.entriver.orderaround.adapter.SliderPagerAdapter$1 */
    class C08281 implements OnClickListener {
        C08281() {
        }

        public void onClick(View view) {
            new SliderDialogFragment().show(SliderPagerAdapter.this.activity.getFragmentManager(), "slider_dialog");
        }
    }
}
