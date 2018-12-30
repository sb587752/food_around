package com.opalfire.orderaround.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.models.NotificationItem;
import com.opalfire.orderaround.models.Restaurant;

import java.util.List;

public class NotificationAdapter extends Adapter<MyViewHolder> {
    private Context context;
    private List<NotificationItem> list;

    public NotificationAdapter(List<NotificationItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_detail_item, viewGroup, false));
    }

    public void add(NotificationItem notificationItem, int i) {
        this.list.add(i, notificationItem);
        notifyItemInserted(i);
    }

    public void remove(Restaurant restaurant) {
        restaurant = this.list.indexOf(restaurant);
        this.list.remove(restaurant);
        notifyItemRemoved(restaurant);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        NotificationItem notificationItem = (NotificationItem) this.list.get(i);
        myViewHolder.validity.setText(notificationItem.validity);
        myViewHolder.offerDescription.setText(notificationItem.offerDescription);
        myViewHolder.offerCode.setText(notificationItem.offerCode);
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        private LinearLayout notificatioLayout;
        private TextView offerCode;
        private TextView offerDescription;
        private TextView validity;

        private MyViewHolder(View view) {
            super(view);
            this.notificatioLayout = (LinearLayout) view.findViewById(R.id.notification_layout);
            this.validity = (TextView) view.findViewById(R.id.validity_date);
            this.offerDescription = (TextView) view.findViewById(R.id.offer_description);
            this.offerCode = (TextView) view.findViewById(R.id.offer_code);
        }

        public void onClick(View view) {
            view.getId();
            this.notificatioLayout.getId();
        }
    }
}
