package com.opalfire.foodorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.HotelViewActivity;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Shop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class RestaurantsAdapter extends Adapter<MyViewHolder> {
    private Activity activity;
    private Context context;
    private List<Shop> list;

    public RestaurantsAdapter(List<Shop> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_list_item, viewGroup, false));
    }

    public void add(Shop shop, int i) {
        this.list.add(i, shop);
        notifyItemInserted(i);
    }

    public void remove(Shop shop) {
        shop = this.list.indexOf(shop);
        this.list.remove(shop);
        notifyItemRemoved(shop);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Shop shop = (Shop) this.list.get(i);
        Glide.with(this.context).load(shop.getAvatar()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.ic_restaurant_place_holder).error((int) R.drawable.ic_restaurant_place_holder)).into(myViewHolder.dishImg);
        myViewHolder.restaurantName.setText(shop.getName());
        myViewHolder.category.setText(shop.getDescription());
        int i2 = 0;
        if (shop.getOfferPercent() == null) {
            myViewHolder.offer.setVisibility(View.GONE);
        } else {
            myViewHolder.offer.setVisibility(View.VISIBLE);
            TextView access$400 = myViewHolder.offer;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Flat ");
            stringBuilder.append(shop.getOfferPercent().toString());
            stringBuilder.append("% offer on all Orders");
            access$400.setText(stringBuilder.toString());
        }
        if (shop.getShopstatus() != null) {
            RelativeLayout relativeLayout = myViewHolder.closedLay;
            if (!shop.getShopstatus().equalsIgnoreCase("CLOSED")) {
                i2 = 8;
            }
            relativeLayout.setVisibility(i2);
        }
        if (shop.getRating() != null) {
            Double valueOf = Double.valueOf(new BigDecimal(shop.getRating().doubleValue()).setScale(1, RoundingMode.HALF_UP).doubleValue());
            TextView access$500 = myViewHolder.rating;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(valueOf);
            access$500.setText(stringBuilder2.toString());
        } else {
            myViewHolder.rating.setText("No Rating");
        }
        myViewHolder = myViewHolder.distanceTime;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(shop.getEstimatedDeliveryTime().toString());
        stringBuilder3.append(" Mins");
        myViewHolder.setText(stringBuilder3.toString());
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        RelativeLayout closedLay;
        private TextView category;
        private ImageView dishImg;
        private TextView distanceTime;
        private LinearLayout itemView;
        private TextView offer;
        private TextView price;
        private TextView rating;
        private TextView restaurantInfo;
        private TextView restaurantName;

        private MyViewHolder(View view) {
            super(view);
            this.itemView = (LinearLayout) view.findViewById(R.id.item_view);
            this.closedLay = (RelativeLayout) view.findViewById(R.id.closed_lay);
            this.dishImg = (ImageView) view.findViewById(R.id.dish_img);
            this.restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
            this.category = (TextView) view.findViewById(R.id.category);
            this.offer = (TextView) view.findViewById(R.id.offer);
            this.rating = (TextView) view.findViewById(R.id.rating);
            this.restaurantInfo = (TextView) view.findViewById(R.id.restaurant_info);
            this.distanceTime = (TextView) view.findViewById(R.id.distance_time);
            this.price = (TextView) view.findViewById(R.id.price);
            this.itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (view.getId() == this.itemView.getId()) {
                GlobalData.selectedShop = (Shop) RestaurantsAdapter.this.list.get(getAdapterPosition());
                if (GlobalData.selectedShop.getShopstatus().equalsIgnoreCase("CLOSED") == null) {
                    RestaurantsAdapter.this.context.startActivity(new Intent(RestaurantsAdapter.this.context, HotelViewActivity.class).putExtra("position", getAdapterPosition()).addFlags(67108864));
                    RestaurantsAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                    ((Shop) RestaurantsAdapter.this.list.get(getAdapterPosition())).getCuisines();
                    return;
                }
                Toast.makeText(RestaurantsAdapter.this.context, "The Shop is closed", 0).show();
            }
        }
    }
}
