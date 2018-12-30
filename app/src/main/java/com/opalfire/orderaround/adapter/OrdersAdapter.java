package com.opalfire.orderaround.adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.CurrentOrderDetailActivity;
import com.opalfire.orderaround.activities.PastOrderDetailActivity;
import com.opalfire.orderaround.activities.ViewCartActivity;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.AddCart;
import com.opalfire.orderaround.models.Cart;
import com.opalfire.orderaround.models.Item;
import com.opalfire.orderaround.models.Order;
import com.opalfire.orderaround.models.OrderModel;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersAdapter extends SectionedRecyclerViewAdapter<ViewHolder> {
    Activity activity;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    Context context1;
    CustomDialog customDialog;
    List<Item> itemList;
    int lastPosition = -1;
    private LayoutInflater inflater;
    private List<OrderModel> list = new ArrayList();

    public OrdersAdapter(Context context, Activity activity, List<OrderModel> list) {
        this.context1 = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.activity = activity;
        this.customDialog = new CustomDialog(context);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case -2:
                return new ViewHolder(this.inflater.inflate(R.layout.header_order, viewGroup, false), true);
            case -1:
                return new ViewHolder(this.inflater.inflate(R.layout.orders_list_item, viewGroup, false), false);
            default:
                return new ViewHolder(this.inflater.inflate(R.layout.orders_list_item, viewGroup, false), false);
        }
    }

    public int getSectionCount() {
        return this.list.size();
    }

    public int getItemCount(int i) {
        return ((OrderModel) this.list.get(i)).getOrders().size();
    }

    public void onBindHeaderViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.headerTxt.setText(((OrderModel) this.list.get(i)).getHeader());
        viewHolder.headerTxt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                System.out.println(((OrderModel) OrdersAdapter.this.list.get(i)).getHeader());
            }
        });
    }

    private void setScaleAnimation(View view) {
        Animation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(1000);
        view.startAnimation(scaleAnimation);
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i, final int i2, int i3) {
        TextView textView;
        StringBuilder stringBuilder;
        final Order order = (Order) ((OrderModel) this.list.get(i)).getOrders().get(i2);
        viewHolder.restaurantNameTxt.setText(order.getShop().getName());
        viewHolder.restaurantAddressTxt.setText(order.getShop().getAddress());
        viewHolder.reorderBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new HashMap();
                view.put("order_id", String.valueOf(order.getId()));
                if (GlobalData.addCart == null || GlobalData.addCart.getProductList().isEmpty()) {
                    OrdersAdapter.this.Reorder(view);
                    return;
                }
                new Builder(OrdersAdapter.this.activity).setTitle("Reorder").setMessage(String.format(OrdersAdapter.this.activity.getResources().getString(R.string.reorder_confirm_message), new Object[]{((Cart) GlobalData.addCart.getProductList().get(0)).getProduct().getShop().getName(), order.getShop().getName()})).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OrdersAdapter.this.Reorder(view);
                    }
                }).setNegativeButton(17039369, null).show();
            }
        });
        int i4 = i2 + 1;
        int i5 = 0;
        if (((OrderModel) this.list.get(i)).getOrders().size() == 1) {
            viewHolder.dividerLine.setVisibility(View.GONE);
        } else if (((OrderModel) this.list.get(i)).getOrders().size() == i4) {
            viewHolder.dividerLine.setVisibility(View.GONE);
        } else {
            viewHolder.dividerLine.setVisibility(View.VISIBLE);
        }
        if (order.getDispute() == null || order.getDispute().equalsIgnoreCase("NODISPUTE")) {
            viewHolder.disputeLayout.setVisibility(View.GONE);
        } else {
            viewHolder.disputeLayout.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder2;
            if (order.getDispute().equalsIgnoreCase("CREATED")) {
                viewHolder.disputeStatusImage.setBackgroundResource(R.drawable.dispute);
                textView = viewHolder.disputeTxt;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.context1.getResources().getString(R.string.dispute));
                stringBuilder2.append(" ");
                stringBuilder2.append(order.getDispute());
                textView.setText(stringBuilder2.toString());
            } else {
                viewHolder.disputeStatusImage.setBackgroundResource(R.drawable.dispute_success);
                textView = viewHolder.disputeTxt;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.context1.getResources().getString(R.string.dispute));
                stringBuilder2.append(" ");
                stringBuilder2.append(order.getDispute());
                textView.setText(stringBuilder2.toString());
            }
        }
        viewHolder.disputeTxt.setOnClickListener(new C08123());
        if (order.getItems().get(0) != null) {
            textView = viewHolder.totalAmount;
            stringBuilder = new StringBuilder();
            stringBuilder.append(((Item) order.getItems().get(0)).getProduct().getPrices().getCurrency());
            stringBuilder.append(order.getInvoice().getNet().toString());
            textView.setText(stringBuilder.toString());
        } else {
            textView = viewHolder.totalAmount;
            stringBuilder = new StringBuilder();
            stringBuilder.append(GlobalData.currencySymbol);
            stringBuilder.append(order.getInvoice().getNet().toString());
            textView.setText(stringBuilder.toString());
        }
        this.itemList = new ArrayList();
        this.itemList.addAll(order.getItems());
        CharSequence charSequence = "";
        while (i5 < this.itemList.size()) {
            if (i5 == 0) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(((Item) order.getItems().get(i5)).getProduct().getName());
                stringBuilder3.append(" (");
                stringBuilder3.append(((Item) order.getItems().get(i5)).getQuantity());
                stringBuilder3.append(")");
                charSequence = stringBuilder3.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(charSequence);
                stringBuilder.append(", ");
                stringBuilder.append(((Item) order.getItems().get(i5)).getProduct().getName());
                stringBuilder.append(" (");
                stringBuilder.append(((Item) order.getItems().get(i5)).getQuantity());
                stringBuilder.append(")");
                charSequence = stringBuilder.toString();
            }
            i5++;
        }
        viewHolder.dishNameTxt.setText(charSequence);
        viewHolder.dateTimeTxt.setText(getTimeFromString(order.getInvoice().getCreatedAt()));
        viewHolder.itemLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (((OrderModel) OrdersAdapter.this.list.get(i)).getHeader().equalsIgnoreCase("Current Orders") != null) {
                    GlobalData.isSelectedOrder = (Order) ((OrderModel) OrdersAdapter.this.list.get(i)).getOrders().get(i2);
                    OrdersAdapter.this.context1.startActivity(new Intent(OrdersAdapter.this.context1, CurrentOrderDetailActivity.class).putExtra("is_order_page", true));
                } else {
                    GlobalData.isSelectedOrder = (Order) ((OrderModel) OrdersAdapter.this.list.get(i)).getOrders().get(i2);
                    OrdersAdapter.this.context1.startActivity(new Intent(OrdersAdapter.this.context1, PastOrderDetailActivity.class));
                }
                OrdersAdapter.this.activity.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            }
        });
    }

    private void Reorder(HashMap<String, String> hashMap) {
        this.customDialog.show();
        System.out.println(hashMap.toString());
        this.apiInterface.reOrder(hashMap).enqueue(new C13835());
    }

    private String getTimeFromString(String str) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time : ");
        stringBuilder.append(str);
        printStream.println(stringBuilder.toString());
        String str2 = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("d MMM yyyy, hh:mm aa");
            if (str != null) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
            return str2;
        } catch (String str3) {
            str3.printStackTrace();
            return str2;
        }
    }

    /* renamed from: com.entriver.orderaround.adapter.OrdersAdapter$3 */
    class C08123 implements OnClickListener {
        C08123() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: com.entriver.orderaround.adapter.OrdersAdapter$5 */
    class C13835 implements Callback<AddCart> {
        C13835() {
        }

        public void onResponse(@NonNull Call<AddCart> call, @NonNull Response<AddCart> response) {
            OrdersAdapter.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.addCart = (AddCart) response.body();
                OrdersAdapter.this.activity.startActivity(new Intent(OrdersAdapter.this.activity, ViewCartActivity.class));
            }
        }

        public void onFailure(@NonNull Call<AddCart> call, @NonNull Throwable th) {
            OrdersAdapter.this.customDialog.dismiss();
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView dateTimeTxt;
        TextView dishNameTxt;
        LinearLayout disputeLayout;
        ImageView disputeStatusImage;
        TextView disputeTxt;
        View dividerLine;
        TextView headerTxt;
        LinearLayout itemLayout;
        Button reorderBtn;
        TextView restaurantAddressTxt;
        TextView restaurantNameTxt;
        TextView totalAmount;

        public ViewHolder(View view, boolean z) {
            super(view);
            if (z) {
                this.headerTxt = (TextView) view.findViewById(R.id.header);
                return;
            }
            this.disputeStatusImage = (ImageView) view.findViewById(R.id.dispute_status_image);
            this.disputeLayout = (LinearLayout) view.findViewById(R.id.dispute_layout);
            this.disputeTxt = (TextView) view.findViewById(R.id.dispute_txt);
            this.itemLayout = (LinearLayout) view.findViewById(R.id.item_layout);
            this.restaurantNameTxt = (TextView) view.findViewById(R.id.restaurant_name);
            this.restaurantAddressTxt = (TextView) view.findViewById(R.id.restaurant_address);
            this.totalAmount = (TextView) view.findViewById(R.id.total_amount);
            this.reorderBtn = (Button) view.findViewById(R.id.reorder);
            this.dishNameTxt = (TextView) view.findViewById(R.id.dish_name);
            this.dateTimeTxt = (TextView) view.findViewById(R.id.date_time);
            this.dividerLine = view.findViewById(R.id.divider_line);
        }
    }
}
