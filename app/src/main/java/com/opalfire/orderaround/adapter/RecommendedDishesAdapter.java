package com.opalfire.orderaround.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.models.RecommendedDish;

import java.util.List;

public class RecommendedDishesAdapter extends Adapter<MyViewHolder> {
    public Context context;
    public List<RecommendedDish> list;

    public RecommendedDishesAdapter(List<RecommendedDish> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommended_list_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        RecommendedDish recommendedDish = (RecommendedDish) this.list.get(i);
        TextView access$100 = myViewHolder.dishNameTxt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("     ");
        stringBuilder.append(recommendedDish.getName());
        access$100.setText(stringBuilder.toString());
        myViewHolder.priceTxt.setText(recommendedDish.getPrice());
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
        private Button addBtn;
        private ImageView dishImg;
        private TextView dishNameTxt;
        private ImageView foodImageType;
        private TextView priceTxt;

        private MyViewHolder(View view) {
            super(view);
            this.dishImg = (ImageView) view.findViewById(R.id.dishImg);
            this.foodImageType = (ImageView) view.findViewById(R.id.food_type_image);
            this.dishNameTxt = (TextView) view.findViewById(R.id.dish_name_text);
            this.priceTxt = (TextView) view.findViewById(R.id.price_text);
            this.addBtn = (Button) view.findViewById(R.id.add_btn);
            this.addBtn.setOnClickListener(this);
        }

        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (view.getId() == this.addBtn.getId()) {
                view = view.getContext();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ITEM PRESSED = ");
                stringBuilder.append(((RecommendedDish) RecommendedDishesAdapter.this.list.get(adapterPosition)).getName());
                stringBuilder.append(((RecommendedDish) RecommendedDishesAdapter.this.list.get(adapterPosition)).getPrice());
                Toast.makeText(view, stringBuilder.toString(), 0).show();
                return;
            }
            view = view.getContext();
            stringBuilder = new StringBuilder();
            stringBuilder.append("ROW PRESSED = ");
            stringBuilder.append(((RecommendedDish) RecommendedDishesAdapter.this.list.get(adapterPosition)).getName());
            stringBuilder.append(((RecommendedDish) RecommendedDishesAdapter.this.list.get(adapterPosition)).getPrice());
            Toast.makeText(view, stringBuilder.toString(), 0).show();
        }
    }
}
