package com.opalfire.orderaround.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.OtherHelpActivity;
import com.opalfire.orderaround.models.DisputeMessage;

import java.util.List;

public class DisputeMessageAdapter extends Adapter<MyViewHolder> {
    private Activity activity;
    private Context context;
    private List<DisputeMessage> list;

    public DisputeMessageAdapter(List<DisputeMessage> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dispute_message_item, viewGroup, false));
    }

    public void add(DisputeMessage disputeMessage, int i) {
        this.list.add(i, disputeMessage);
        notifyItemInserted(i);
    }

    public void remove(DisputeMessage disputeMessage) {
        disputeMessage = this.list.indexOf(disputeMessage);
        this.list.remove(disputeMessage);
        notifyItemRemoved(disputeMessage);
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        final DisputeMessage disputeMessage = (DisputeMessage) this.list.get(i);
        myViewHolder.diputeMessageTxt.setText(disputeMessage.getName());
        myViewHolder.rootLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DisputeMessageAdapter.this.context.startActivity(new Intent(DisputeMessageAdapter.this.context, OtherHelpActivity.class).putExtra("type", disputeMessage.getName()).putExtra("id", disputeMessage.getId()));
                DisputeMessageAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder {
        private TextView diputeMessageTxt;
        private LinearLayout rootLayout;

        private MyViewHolder(View view) {
            super(view);
            this.rootLayout = (LinearLayout) view.findViewById(R.id.root_layout);
            this.diputeMessageTxt = (TextView) view.findViewById(R.id.dispute_message);
        }
    }
}
