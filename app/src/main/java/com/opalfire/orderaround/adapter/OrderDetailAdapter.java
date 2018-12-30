package com.opalfire.orderaround.adapter;

import android.content.Context;
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
import com.opalfire.orderaround.models.CartAddon;
import com.opalfire.orderaround.models.Item;

import java.util.List;

public class OrderDetailAdapter extends Adapter<MyViewHolder> {
    private Context context;
    private List<Item> list;

    public OrderDetailAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_detail_list_item, viewGroup, false));
    }

    public void add(Item item, int i) {
        this.list.add(i, item);
        notifyItemInserted(i);
    }

    public void remove(Item item) {
        item = this.list.indexOf(item);
        this.list.remove(item);
        notifyItemRemoved(item);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Item item = (Item) this.list.get(i);
        TextView access$100 = myViewHolder.dishName;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.getProduct().getName());
        stringBuilder.append(" x ");
        stringBuilder.append(String.valueOf(item.getQuantity()));
        access$100.setText(stringBuilder.toString());
        Object valueOf = Integer.valueOf(item.getProduct().getPrices().getPrice().intValue() * item.getQuantity().intValue());
        if (!(((Item) this.list.get(i)).getCartAddons() == null || ((Item) this.list.get(i)).getCartAddons().isEmpty())) {
            Integer num = valueOf;
            for (int i2 = 0; i2 < ((Item) this.list.get(i)).getCartAddons().size(); i2++) {
                num = Integer.valueOf(num.intValue() + (((Item) this.list.get(i)).getQuantity().intValue() * (((CartAddon) ((Item) this.list.get(i)).getCartAddons().get(i2)).getQuantity().intValue() * ((CartAddon) ((Item) this.list.get(i)).getCartAddons().get(i2)).getAddonProduct().getPrice().intValue())));
            }
            valueOf = num;
        }
        i = myViewHolder.price;
        stringBuilder = new StringBuilder();
        stringBuilder.append(item.getProduct().getPrices().getCurrency());
        stringBuilder.append(valueOf);
        i.setText(stringBuilder.toString());
        if (item.getProduct().getFoodType().equalsIgnoreCase("veg") != 0) {
            myViewHolder.dishImg.setImageResource(R.drawable.ic_veg);
        } else {
            myViewHolder.dishImg.setImageResource(R.drawable.ic_nonveg);
        }
        if (item.getCartAddons() == 0 || item.getCartAddons().isEmpty() != 0) {
            myViewHolder.addons.setVisibility(View.GONE);
            return;
        }
        i = item.getCartAddons();
        for (int i3 = 0; i3 < i.size(); i3++) {
            if (i3 == 0) {
                myViewHolder.addons.setText(((CartAddon) i.get(i3)).getAddonProduct().getAddon().getName());
            } else {
                access$100 = myViewHolder.addons;
                stringBuilder = new StringBuilder();
                stringBuilder.append(", ");
                stringBuilder.append(((CartAddon) i.get(i3)).getAddonProduct().getAddon().getName());
                access$100.append(stringBuilder.toString());
            }
        }
        myViewHolder.addons.setVisibility(View.VISIBLE);
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        private TextView addons;
        private ImageView dishImg;
        private TextView dishName;
        private LinearLayout itemView;
        private TextView price;

        private MyViewHolder(View view) {
            super(view);
            this.itemView = (LinearLayout) view.findViewById(R.id.item_view);
            this.dishName = (TextView) view.findViewById(R.id.restaurant_name);
            this.addons = (TextView) view.findViewById(R.id.addons);
            this.dishImg = (ImageView) view.findViewById(R.id.food_type_image);
            this.price = (TextView) view.findViewById(R.id.price);
            this.itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            view.getId();
            this.itemView.getId();
        }
    }
}
