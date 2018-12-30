package com.opalfire.orderaround.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.OrderDetailAdapter;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderDetailFragment extends Fragment {
    Context context = getActivity();
    String currency = "";

    int discount = 0;

    int itemCount = 0;
    List<Item> itemList;
    int itemQuantity = 0;

    int totalAmountValue = 0;
    Unbinder unbinder;
    @BindView(R.id.order_recycler_view)
    RecyclerView orderRecyclerView;
    @BindView(R.id.item_total_amount)
    TextView itemTotalAmount;
    @BindView(R.id.service_tax)
    TextView serviceTax;
    @BindView(R.id.delivery_charges)
    TextView deliveryCharges;
    @BindView(R.id.discount_amount)
    TextView discountAmount;
    @BindView(R.id.wallet_amount_detection)
    TextView walletAmountDetection;
    @BindView(R.id.total_amount)
    TextView totalAmount;


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_order_detail, viewGroup, false);
        unbinder = ButterKnife.bind(this, inflate);
        itemList = new ArrayList<>();
        if (GlobalData.isSelectedOrder != null) {
            itemList.addAll(GlobalData.isSelectedOrder.getItems());
            orderRecyclerView.setLayoutManager(new LinearLayoutManager(context, 1, false));
            orderRecyclerView.setItemAnimator(new DefaultItemAnimator());
            orderRecyclerView.setHasFixedSize(true);
            orderRecyclerView.setAdapter(new OrderDetailAdapter(itemList, context));
            currency = GlobalData.isSelectedOrder.getItems().get(0).getProduct().getPrices().getCurrency();
            itemQuantity = GlobalData.isSelectedOrder.getInvoice().getQuantity();
            String itemTotalAmountStr =currency +
                    GlobalData.isSelectedOrder.getInvoice().getGross().toString() ;
            itemTotalAmount.setText(itemTotalAmountStr);
            String serviceTaxStr = currency +
                    GlobalData.isSelectedOrder.getInvoice().getTax().toString();
            serviceTax.setText(serviceTaxStr);
            String deliveryChargesStr = currency +
                    GlobalData.isSelectedOrder.getInvoice().getDeliveryCharge().toString();
            deliveryCharges.setText(deliveryChargesStr);
            String discountAmountStr = "-" +
                    currency +
                    GlobalData.isSelectedOrder.getInvoice().getDiscount().toString();
            discountAmount.setText(discountAmountStr);
            String walletAmountDetectionStr = currency +
                    GlobalData.isSelectedOrder.getInvoice().getWalletAmount().toString();
            walletAmountDetection.setText(walletAmountDetectionStr);
            String totalAmountStr = currency +
                    String.valueOf(GlobalData.isSelectedOrder.getInvoice().getPayable());
            totalAmount.setText(totalAmountStr);
        }
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
