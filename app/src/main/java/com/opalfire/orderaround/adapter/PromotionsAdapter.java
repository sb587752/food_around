package com.opalfire.orderaround.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.models.Promotions;
import com.opalfire.orderaround.models.Restaurant;

import java.util.List;

public class PromotionsAdapter extends Adapter<MyViewHolder> {
    PromotionListener promotionListener;
    private List<Promotions> list;

    public PromotionsAdapter(List<Promotions> list, PromotionListener promotionListener) {
        this.list = list;
        this.promotionListener = promotionListener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.promotions_item, viewGroup, false));
    }

    public void add(Promotions promotions, int i) {
        this.list.add(i, promotions);
        notifyItemInserted(i);
    }

    public void remove(Restaurant restaurant) {
        restaurant = this.list.indexOf(restaurant);
        this.list.remove(restaurant);
        notifyItemRemoved(restaurant);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Promotions promotions = (Promotions) this.list.get(i);
        myViewHolder.promoNameTxt.setText(promotions.getPromoCode());
        myViewHolder.statusBtnTxt.setTag(promotions);
    }

    public int getItemCount() {
        return this.list.size();
    }

    public interface PromotionListener {
        void onApplyBtnClick(Promotions promotions);
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        TextView promoNameTxt;
        Button statusBtnTxt;

        private MyViewHolder(View view) {
            super(view);
            this.promoNameTxt = (TextView) view.findViewById(R.id.promo_name_txt);
            this.statusBtnTxt = (Button) view.findViewById(R.id.status_btn);
            this.statusBtnTxt.setOnClickListener(this);
        }

        public void onClick(View view) {
            PromotionsAdapter.this.promotionListener.onApplyBtnClick((Promotions) view.getTag());
        }
    }
}
