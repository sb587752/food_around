package com.opalfire.foodorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.HotelViewActivity;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Available;
import com.opalfire.foodorder.models.FavListModel;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends SectionedRecyclerViewAdapter<ViewHolder> {
    Context context;
    private LayoutInflater inflater;
    private List<FavListModel> list = new ArrayList();

    public FavouritesAdapter(Context context, List<FavListModel> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case -2:
                return new ViewHolder(this.inflater.inflate(R.layout.header, viewGroup, false), true);
            case -1:
                return new ViewHolder(this.inflater.inflate(R.layout.favorite_list_item, viewGroup, false), false);
            default:
                return new ViewHolder(this.inflater.inflate(R.layout.favorite_list_item, viewGroup, false), false);
        }
    }

    public int getSectionCount() {
        return this.list.size();
    }

    public int getItemCount(int i) {
        return ((FavListModel) this.list.get(i)).getFav().size();
    }

    public void onBindHeaderViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.header.setText(((FavListModel) this.list.get(i)).getHeader());
        viewHolder.header.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                System.out.println(((FavListModel) FavouritesAdapter.this.list.get(i)).getHeader());
            }
        });
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i, int i2, int i3) {
        final Available available = (Available) ((FavListModel) this.list.get(i)).getFav().get(i2);
        viewHolder.shopName.setText(available.getShop().getName());
        viewHolder.shopAddress.setText(available.getShop().getAddress());
        System.out.println(available.getShop().getAvatar());
        Glide.with(this.context).load(available.getShop().getAvatar()).into(viewHolder.shopAvatar);
        viewHolder.itemLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GlobalData.selectedShop = available.getShop();
                FavouritesAdapter.this.context.startActivity(new Intent(FavouritesAdapter.this.context, HotelViewActivity.class).putExtra("is_fav", true));
            }
        });
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView header;
        RelativeLayout itemLayout;
        TextView shopAddress;
        ImageView shopAvatar;
        TextView shopName;

        public ViewHolder(View view, boolean z) {
            super(view);
            if (z) {
                this.header = (TextView) view.findViewById(R.id.header);
                return;
            }
            this.itemLayout = (RelativeLayout) view.findViewById(R.id.item_layout);
            this.shopName = (TextView) view.findViewById(R.id.shop_name);
            this.shopAddress = (TextView) view.findViewById(R.id.shop_address);
            this.shopAvatar = (ImageView) view.findViewById(R.id.shop_avatar);
        }
    }
}
