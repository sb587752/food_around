package com.opalfire.orderaround.adapter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.RecommendedListActivity;
import com.opalfire.orderaround.models.RecommendedDish;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;

public class RecommendedListAdapter extends BaseAdapter {
    private Context context;
    private List<RecommendedDish> list;

    public RecommendedListAdapter(Context context, List<RecommendedDish> list) {
        this.context = context;
        this.list = list;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int i) {
        return this.list.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final RecommendedDish recommendedDish = (RecommendedDish) this.list.get(i);
        if (view != null) {
            viewGroup = (ViewHolder) view.getTag();
        } else {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.recommended_list_item_2, viewGroup, false);
            viewGroup = new ViewHolder(view);
            view.setTag(viewGroup);
        }
        viewGroup.dishNameTxt.setText(recommendedDish.getName());
        viewGroup.priceTxt.setText(recommendedDish.getPrice());
        viewGroup.descriptionTxt.setText(recommendedDish.getDescription());
        viewGroup.addBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                System.out.println(recommendedDish.getName());
                if ((RecommendedListAdapter.this.context instanceof RecommendedListActivity) != null) {
                    ((RecommendedListActivity) RecommendedListAdapter.this.context).addItemToCart();
                }
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(2131296285)
        Button addBtn;
        @BindView(2131296470)
        TextView descriptionTxt;
        @BindView(2131296484)
        ImageView dishImg;
        @BindView(2131296485)
        TextView dishNameTxt;
        @BindView(2131296739)
        TextView priceTxt;

        ViewHolder(View view) {
            ButterKnife.bind((Object) this, view);
        }
    }

    public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.dishImg = (ImageView) Utils.findRequiredViewAsType(view, R.id.dish_img, "field 'dishImg'", ImageView.class);
            viewHolder.addBtn = (Button) Utils.findRequiredViewAsType(view, R.id.add, "field 'addBtn'", Button.class);
            viewHolder.dishNameTxt = (TextView) Utils.findRequiredViewAsType(view, R.id.dish_name, "field 'dishNameTxt'", TextView.class);
            viewHolder.priceTxt = (TextView) Utils.findRequiredViewAsType(view, R.id.price, "field 'priceTxt'", TextView.class);
            viewHolder.descriptionTxt = (TextView) Utils.findRequiredViewAsType(view, R.id.description, "field 'descriptionTxt'", TextView.class);
        }

        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder != null) {
                this.target = null;
                viewHolder.dishImg = null;
                viewHolder.addBtn = null;
                viewHolder.dishNameTxt = null;
                viewHolder.priceTxt = null;
                viewHolder.descriptionTxt = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }
}
