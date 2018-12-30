package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.OrdersAdapter;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.ConnectionHelper;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Order;
import com.opalfire.orderaround.models.OrderModel;
import com.opalfire.orderaround.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrdersActivity extends AppCompatActivity {
    Activity activity = this;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    ConnectionHelper connectionHelper;
    Context context;
    CustomDialog customDialog;
    @BindView(2131296514)
    LinearLayout errorLayout;
    List<OrderModel> modelList = new ArrayList();
    Intent orderIntent;
    Runnable orderStatusRunnable;
    @BindView(2131296702)
    RecyclerView ordersRv;
    @BindView(2131296914)
    Toolbar toolbar;
    private OrdersAdapter adapter;
    private List<OrderModel> modelListReference = new ArrayList();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_orders);
        this.context = this;
        this.customDialog = new CustomDialog(this.context);
        ButterKnife.bind((Activity) this);
        this.connectionHelper = new ConnectionHelper(this.context);
        setSupportActionBar(this.toolbar);
        GlobalData.onGoingOrderList = new ArrayList();
        GlobalData.pastOrderList = new ArrayList();
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07451());
        this.toolbar.setPadding(0, 0, 0, 0);
        this.toolbar.setContentInsetsAbsolute(0, 0);
        this.ordersRv.setLayoutManager(new LinearLayoutManager(this));
        this.modelListReference.clear();
        this.adapter = new OrdersAdapter(this, this.activity, this.modelListReference);
        this.ordersRv.setAdapter(this.adapter);
        this.ordersRv.setHasFixedSize(false);
    }

    private void getPastOrders() {
        this.apiInterface.getPastOders().enqueue(new C13262());
    }

    private void getOngoingOrders() {
        this.apiInterface.getOngoingOrders().enqueue(new C13273());
    }

    protected void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
        this.modelList.clear();
        if (this.connectionHelper.isConnectingToInternet()) {
            this.customDialog.show();
            getOngoingOrders();
            return;
        }
        Utils.displayMessage(this.activity, this.context, getString(R.string.oops_connect_your_internet));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    /* renamed from: com.entriver.orderaround.activities.OrdersActivity$1 */
    class C07451 implements OnClickListener {
        C07451() {
        }

        public void onClick(View view) {
            OrdersActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.OrdersActivity$2 */
    class C13262 implements Callback<List<Order>> {
        C13262() {
        }

        public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
            OrdersActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.pastOrderList.clear();
                GlobalData.pastOrderList = (List) response.body();
                call = new OrderModel();
                call.setHeader(OrdersActivity.this.getString(R.string.past_orders));
                call.setOrders(GlobalData.pastOrderList);
                OrdersActivity.this.modelList.add(call);
                OrdersActivity.this.modelListReference.clear();
                OrdersActivity.this.modelListReference.addAll(OrdersActivity.this.modelList);
                OrdersActivity.this.adapter.notifyDataSetChanged();
                if (GlobalData.onGoingOrderList.size() == null && GlobalData.pastOrderList.size() == null) {
                    OrdersActivity.this.errorLayout.setVisibility(null);
                    return;
                } else {
                    OrdersActivity.this.errorLayout.setVisibility(View.GONE);
                    return;
                }
            }
            try {
                Toast.makeText(OrdersActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<List<Order>> response2) {
                Toast.makeText(OrdersActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable th) {
            OrdersActivity.this.customDialog.dismiss();
            Toast.makeText(OrdersActivity.this, "Some thing went wrong", 0).show();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.OrdersActivity$3 */
    class C13273 implements Callback<List<Order>> {
        C13273() {
        }

        public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
            if (response.isSuccessful() != null) {
                GlobalData.onGoingOrderList.clear();
                OrdersActivity.this.modelListReference.clear();
                GlobalData.onGoingOrderList = (List) response.body();
                OrdersActivity.this.modelList.clear();
                call = new OrderModel();
                call.setHeader("Current Orders");
                call.setOrders(GlobalData.onGoingOrderList);
                OrdersActivity.this.modelList.add(call);
                OrdersActivity.this.modelListReference.addAll(OrdersActivity.this.modelList);
                OrdersActivity.this.getPastOrders();
                return;
            }
            OrdersActivity.this.getPastOrders();
            try {
                Toast.makeText(OrdersActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<List<Order>> response2) {
                Toast.makeText(OrdersActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable th) {
            Toast.makeText(OrdersActivity.this, "Something went wrong!", 0).show();
            OrdersActivity.this.getPastOrders();
            OrdersActivity.this.customDialog.dismiss();
        }
    }
}
