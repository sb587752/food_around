package com.opalfire.orderaround.adapter;

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
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.HotelViewActivity;
import com.opalfire.orderaround.models.Restaurant;

import java.util.List;

public class OfferRestaurantAdapter extends Adapter<MyViewHolder> {
    private Context context;
    private List<Restaurant> list;

    public OfferRestaurantAdapter(List<Restaurant> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_restaurant_list_item, viewGroup, false));
    }

    public void add(Restaurant restaurant, int i) {
        this.list.add(i, restaurant);
        notifyItemInserted(i);
    }

    public void remove(Restaurant restaurant) {
        restaurant = this.list.indexOf(restaurant);
        this.list.remove(restaurant);
        notifyItemRemoved(restaurant);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Restaurant restaurant = (Restaurant) this.list.get(i);
        myViewHolder.restaurantName.setText(restaurant.name);
        myViewHolder.category.setText(restaurant.category);
        myViewHolder.rating.setText(restaurant.rating);
        myViewHolder.distanceTime.setText(restaurant.distance);
        myViewHolder.price.setText(restaurant.price);
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        private TextView category;
        private ImageView dishImg;
        private TextView distanceTime;
        private LinearLayout itemView;
        private TextView offer;
        private TextView price;
        private TextView rating;
        private TextView restaurantName;

        private MyViewHolder(View view) {
            super(view);
            this.itemView = (LinearLayout) view.findViewById(R.id.item_view);
            this.dishImg = (ImageView) view.findViewById(R.id.dishImg);
            this.dishImg = (ImageView) view.findViewById(R.id.dishImg);
            this.restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
            this.category = (TextView) view.findViewById(R.id.category);
            this.offer = (TextView) view.findViewById(R.id.offer);
            this.rating = (TextView) view.findViewById(R.id.rating);
            this.distanceTime = (TextView) view.findViewById(R.id.distance_time);
            this.price = (TextView) view.findViewById(R.id.price);
            this.itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (view.getId() == this.itemView.getId()) {
                OfferRestaurantAdapter.this.context.startActivity(new Intent(OfferRestaurantAdapter.this.context, HotelViewActivity.class));
            }
        }
    }
}
