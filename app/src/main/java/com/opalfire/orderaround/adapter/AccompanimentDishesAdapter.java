package com.opalfire.orderaround.adapter;

import android.content.Context;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.HotelViewActivity;
import com.opalfire.orderaround.models.RecommendedDish;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.List;

public class AccompanimentDishesAdapter extends Adapter<MyViewHolder> {
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    AnimatedVectorDrawableCompat avdProgress;
    int itemCount = 0;
    int priceAmount = 0;
    private Context context;
    private List<RecommendedDish> list;

    public AccompanimentDishesAdapter(List<RecommendedDish> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accompainment_list_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        RecommendedDish recommendedDish = (RecommendedDish) this.list.get(i);
        myViewHolder.cardTextValueTicker.setCharacterList(NUMBER_LIST);
        myViewHolder.dishNameTxt.setText(recommendedDish.getName());
        TextView access$200 = myViewHolder.priceTxt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("$");
        stringBuilder.append(recommendedDish.getPrice());
        access$200.setText(stringBuilder.toString());
        if (recommendedDish.getAvaialable().equalsIgnoreCase("available")) {
            myViewHolder.cardAddTextLayout.setVisibility(View.VISIBLE);
            myViewHolder.cardTextValueTicker.setText(String.valueOf(1));
            myViewHolder.cardInfoLayout.setVisibility(View.GONE);
        } else if (recommendedDish.getAvaialable().equalsIgnoreCase("out of stock")) {
            myViewHolder.cardAddTextLayout.setVisibility(View.GONE);
            myViewHolder.cardInfoLayout.setVisibility(View.VISIBLE);
            myViewHolder.cardAddInfoText.setVisibility(View.GONE);
            myViewHolder.cardAddOutOfStock.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.cardAddTextLayout.setVisibility(View.GONE);
            myViewHolder.cardInfoLayout.setVisibility(View.VISIBLE);
            myViewHolder.cardAddInfoText.setVisibility(View.VISIBLE);
            myViewHolder.cardAddOutOfStock.setVisibility(View.GONE);
            myViewHolder.cardAddInfoText.setText(recommendedDish.getAvaialable());
        }
        if (recommendedDish.getIsVeg().booleanValue() != 0) {
            myViewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_nonveg));
        } else {
            myViewHolder.foodImageType.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_veg));
        }
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        RelativeLayout cardAddDetailLayout;
        RelativeLayout cardAddTextLayout;
        RelativeLayout cardInfoLayout;
        TickerView cardTextValueTicker;
        private ImageView animationLineCartAdd;
        private ImageView cardAddBtn;
        private TextView cardAddInfoText;
        private TextView cardAddOutOfStock;
        private ImageView cardMinusBtn;
        private TextView cardTextValue;
        private ImageView dishImg;
        private TextView dishNameTxt;
        private ImageView foodImageType;
        private TextView priceTxt;

        private MyViewHolder(View view) {
            super(view);
            this.dishImg = (ImageView) view.findViewById(R.id.dishImg);
            this.foodImageType = (ImageView) view.findViewById(R.id.food_type_image);
            this.animationLineCartAdd = (ImageView) view.findViewById(R.id.animation_line_cart_add);
            this.dishNameTxt = (TextView) view.findViewById(R.id.dish_name_text);
            this.priceTxt = (TextView) view.findViewById(R.id.price_text);
            this.cardAddDetailLayout = (RelativeLayout) view.findViewById(R.id.add_card_layout);
            this.cardAddTextLayout = (RelativeLayout) view.findViewById(R.id.add_card_text_layout);
            this.cardInfoLayout = (RelativeLayout) view.findViewById(R.id.add_card_info_layout);
            this.cardAddInfoText = (TextView) view.findViewById(R.id.avialablity_time);
            this.cardAddOutOfStock = (TextView) view.findViewById(R.id.out_of_stock);
            this.cardAddBtn = (ImageView) view.findViewById(R.id.card_add_btn);
            this.cardMinusBtn = (ImageView) view.findViewById(R.id.card_minus_btn);
            this.cardTextValue = (TextView) view.findViewById(R.id.card_value);
            this.cardTextValueTicker = (TickerView) view.findViewById(R.id.card_value_ticker);
            this.cardAddTextLayout.setOnClickListener(this);
            this.cardAddBtn.setOnClickListener(this);
            this.cardMinusBtn.setOnClickListener(this);
        }

        public void onClick(View view) {
            view = view.getId();
            if (view == R.id.add_card_text_layout) {
                this.cardAddDetailLayout.setVisibility(View.VISIBLE);
                HotelViewActivity.viewCartLayout.setVisibility(View.VISIBLE);
                AccompanimentDishesAdapter.this.itemCount++;
                AccompanimentDishesAdapter.this.priceAmount += Integer.parseInt(((RecommendedDish) AccompanimentDishesAdapter.this.list.get(getAdapterPosition())).getPrice());
                view = HotelViewActivity.itemText;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(AccompanimentDishesAdapter.this.itemCount);
                stringBuilder.append(" Item | $");
                stringBuilder.append(AccompanimentDishesAdapter.this.priceAmount);
                view.setText(stringBuilder.toString());
                this.cardAddTextLayout.setVisibility(View.GONE);
            } else if (view == R.id.card_add_btn) {
                view = Integer.parseInt(this.cardTextValue.getText().toString()) + 1;
                AccompanimentDishesAdapter.this.itemCount++;
                AccompanimentDishesAdapter.this.priceAmount += Integer.parseInt(((RecommendedDish) AccompanimentDishesAdapter.this.list.get(getAdapterPosition())).getPrice());
                r0 = HotelViewActivity.itemText;
                r1 = new StringBuilder();
                r1.append("");
                r1.append(AccompanimentDishesAdapter.this.itemCount);
                r1.append(" Items | $");
                r1.append(AccompanimentDishesAdapter.this.priceAmount);
                r0.setText(r1.toString());
                r0 = this.cardTextValue;
                r1 = new StringBuilder();
                r1.append("");
                r1.append(view);
                r0.setText(r1.toString());
                r0 = this.cardTextValueTicker;
                r1 = new StringBuilder();
                r1.append("");
                r1.append(view);
                r0.setText(r1.toString());
            } else if (view == R.id.card_minus_btn) {
                if (this.cardTextValue.getText().toString().equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_YES) != null) {
                    this.cardAddDetailLayout.setVisibility(View.GONE);
                    HotelViewActivity.viewCartLayout.setVisibility(View.GONE);
                    this.cardAddTextLayout.setVisibility(View.VISIBLE);
                    return;
                }
                view = Integer.parseInt(this.cardTextValue.getText().toString()) - 1;
                AccompanimentDishesAdapter.this.itemCount--;
                AccompanimentDishesAdapter.this.priceAmount -= Integer.parseInt(((RecommendedDish) AccompanimentDishesAdapter.this.list.get(getAdapterPosition())).getPrice());
                r0 = HotelViewActivity.itemText;
                r1 = new StringBuilder();
                r1.append("");
                r1.append(AccompanimentDishesAdapter.this.itemCount);
                r1.append(" Items | $");
                r1.append(AccompanimentDishesAdapter.this.priceAmount);
                r0.setText(r1.toString());
                r0 = this.cardTextValue;
                r1 = new StringBuilder();
                r1.append("");
                r1.append(view);
                r0.setText(r1.toString());
                r0 = this.cardTextValueTicker;
                r1 = new StringBuilder();
                r1.append("");
                r1.append(view);
                r0.setText(r1.toString());
            }
        }
    }
}
