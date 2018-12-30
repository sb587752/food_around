package com.opalfire.orderaround.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.CurrentOrderDetailActivity;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.OrderFlow;

import java.util.List;

public class OrderFlowAdapter extends Adapter<MyViewHolder> {
    public String orderStatus = "";
    private Context context;
    private List<OrderFlow> list;

    public OrderFlowAdapter(List<OrderFlow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_flow_item, viewGroup, false));
    }

    public void add(OrderFlow orderFlow, int i) {
        this.list.add(i, orderFlow);
        notifyItemInserted(i);
    }

    public void remove(OrderFlow orderFlow) {
        orderFlow = this.list.indexOf(orderFlow);
        this.list.remove(orderFlow);
        notifyItemRemoved(orderFlow);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        OrderFlow orderFlow = (OrderFlow) this.list.get(i);
        myViewHolder.statusTitle.setText(orderFlow.statusTitle);
        myViewHolder.statusDescription.setText(orderFlow.statusDescription);
        myViewHolder.statusImage.setImageResource(orderFlow.statusImage);
        if (orderFlow.status.contains(GlobalData.isSelectedOrder.getStatus())) {
            myViewHolder.statusTitle.setTextColor(ContextCompat.getColor(this.context, R.color.colorTextBlack));
            if (GlobalData.isSelectedOrder.getStatus().equals(GlobalData.ORDER_STATUS.get(GlobalData.ORDER_STATUS.size() - 1))) {
                new Handler().postDelayed(new C08081(), 2000);
            }
            if (GlobalData.isSelectedOrder.getStatus().equals(GlobalData.ORDER_STATUS.get(0))) {
                CurrentOrderDetailActivity.orderCancelTxt.setVisibility(View.VISIBLE);
            } else {
                CurrentOrderDetailActivity.orderCancelTxt.setVisibility(View.GONE);
            }
        } else {
            myViewHolder.statusTitle.setTextColor(ContextCompat.getColor(this.context, R.color.colorSecondaryText));
        }
        if (this.list.size() == i + 1) {
            myViewHolder.viewLine.setVisibility(View.GONE);
        } else {
            myViewHolder.viewLine.setVisibility(View.VISIBLE);
        }
    }

    public int getItemCount() {
        return this.list.size();
    }

    /* renamed from: com.entriver.orderaround.adapter.OrderFlowAdapter$1 */
    class C08081 implements Runnable {
        C08081() {
        }

        public void run() {
            ((CurrentOrderDetailActivity) OrderFlowAdapter.this.context).rate();
        }
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        private TextView statusDescription;
        private ImageView statusImage;
        private TextView statusTitle;
        private View viewLine;

        private MyViewHolder(View view) {
            super(view);
            this.statusImage = (ImageView) view.findViewById(R.id.order_status_img);
            this.statusTitle = (TextView) view.findViewById(R.id.order_status_title);
            this.statusDescription = (TextView) view.findViewById(R.id.order_status_description);
            this.viewLine = view.findViewById(R.id.view_line);
        }

        public void onClick(View view) {
        }
    }
}
