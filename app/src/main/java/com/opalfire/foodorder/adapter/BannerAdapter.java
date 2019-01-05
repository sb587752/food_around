package com.opalfire.foodorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.HotelViewActivity;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Banner;

import java.util.List;

public class BannerAdapter extends Adapter<MyViewHolder> {
    Context context;
    private Activity activity;
    private List<Banner> list;

    public BannerAdapter(List<Banner> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.impressive_list_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        Glide.with(this.context).load(((Banner) this.list.get(i)).getUrl()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.ic_banner).error((int) R.drawable.ic_banner)).into(myViewHolder.bannerImg);
        myViewHolder.bannerImg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Banner banner = (Banner) BannerAdapter.this.list.get(i);
                BannerAdapter.this.context.startActivity(new Intent(BannerAdapter.this.context, HotelViewActivity.class));
                GlobalData.selectedShop = banner.getShop();
                BannerAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onItemClick position: ");
                stringBuilder.append(banner.getShop().getName());
                Log.d("Hello", stringBuilder.toString());
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }

    public static class MyViewHolder extends ViewHolder {
        ImageView bannerImg;

        public MyViewHolder(View view) {
            super(view);
            this.bannerImg = (ImageView) view.findViewById(R.id.banner_img);
        }
    }
}
