package com.opalfire.foodorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.models.Discover;

import java.util.List;

public class DiscoverAdapter extends Adapter<MyViewHolder> {
    private static ClickListener clickListener;
    private List<Discover> list;

    public DiscoverAdapter(List<Discover> list, Context context) {
        this.list = list;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        clickListener = clickListener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discover_list_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Discover discover = (Discover) this.list.get(i);
        myViewHolder.title.setText(discover.title);
        myViewHolder.optionCount.setText(discover.optionCount);
    }

    public int getItemCount() {
        return this.list.size();
    }

    public interface ClickListener {
        void onItemClick(int i, View view);
    }

    public static class MyViewHolder extends ViewHolder implements OnClickListener {
        public ImageView icon;
        public TextView optionCount;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(R.id.icon);
            this.title = (TextView) view.findViewById(R.id.title);
            this.optionCount = (TextView) view.findViewById(R.id.option_count);
        }

        public void onClick(View view) {
            DiscoverAdapter.clickListener.onItemClick(getAdapterPosition(), view);
        }
    }
}
