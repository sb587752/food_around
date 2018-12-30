package com.opalfire.orderaround.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.WalletHistory;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class WalletHistoryAdapter extends Adapter<MyViewHolder> {
    private List<WalletHistory> list;

    public WalletHistoryAdapter(List<WalletHistory> list) {
        this.list = list;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wallet_history_list_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        WalletHistory walletHistory = (WalletHistory) this.list.get(i);
        TextView access$100 = myViewHolder.amountTxt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GlobalData.currencySymbol);
        stringBuilder.append(" ");
        stringBuilder.append(walletHistory.getAmount());
        access$100.setText(stringBuilder.toString());
        myViewHolder.timeTxt.setText(getFormatTime(walletHistory.getCreatedAt()));
        myViewHolder.statusTxt.setText(walletHistory.getStatus());
    }

    public int getItemCount() {
        return this.list.size();
    }

    private String getFormatTime(String str) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time : ");
        stringBuilder.append(str);
        printStream.println(stringBuilder.toString());
        String str2 = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEE, MMM d, hh:mm aaa");
            if (str != null) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
            return str2;
        } catch (String str3) {
            str3.printStackTrace();
            return str2;
        }
    }

    public class MyViewHolder extends ViewHolder {
        private TextView amountTxt;
        private TextView statusTxt;
        private TextView timeTxt;

        private MyViewHolder(View view) {
            super(view);
            this.amountTxt = (TextView) view.findViewById(R.id.amount_txt);
            this.timeTxt = (TextView) view.findViewById(R.id.time_txt);
            this.statusTxt = (TextView) view.findViewById(R.id.status_txt);
        }
    }
}
