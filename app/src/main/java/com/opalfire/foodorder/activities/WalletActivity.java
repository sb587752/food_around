package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.WalletHistoryAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.WalletHistory;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WalletActivity extends AppCompatActivity {
    @BindView(2131296289)
    Button addBtn;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296334)
    ImageView back;
    Context context = this;
    CustomDialog customDialog;
    @BindView(2131296514)
    LinearLayout errorLayout;
    NumberFormat numberFormat = GlobalData.getNumberFormat();
    @BindView(2131296899)
    TextView title;
    @BindView(2131296914)
    Toolbar toolbar;
    @BindView(2131296967)
    TextView walletAmountTxt;
    WalletHistoryAdapter walletHistoryAdapter;
    List<WalletHistory> walletHistoryHistoryList;
    @BindView(2131296968)
    RecyclerView walletHistoryRecyclerView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_wallet);
        ButterKnife.bind((Activity) this);
        this.customDialog = new CustomDialog(this.context);
        this.title.setText(this.context.getResources().getString(R.string.walletHistory));
        this.walletHistoryHistoryList = new ArrayList();
        this.walletHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
        this.walletHistoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.walletHistoryRecyclerView.setHasFixedSize(true);
        this.walletHistoryAdapter = new WalletHistoryAdapter(this.walletHistoryHistoryList);
        this.walletHistoryRecyclerView.setAdapter(this.walletHistoryAdapter);
    }

    protected void onResume() {
        super.onResume();
        int intValue = GlobalData.profileModel.getWalletBalance().intValue();
        TextView textView = this.walletAmountTxt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GlobalData.currencySymbol);
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(intValue));
        textView.setText(stringBuilder.toString());
        getWalletHistory();
    }

    private void getWalletHistory() {
        this.customDialog.show();
        this.apiInterface.getWalletHistory().enqueue(new C13741());
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AccountPaymentActivity.class).putExtra("is_show_wallet", true).putExtra("is_show_cash", false));
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @OnClick({2131296289})
    public void onViewClicked() {
        startActivity(new Intent(this, AddMoneyActivity.class));
        finish();
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @OnClick({2131296334})
    public void onBackClicked() {
        onBackPressed();
    }

    /* renamed from: com.opalfire.foodorder.activities.WalletActivity$1 */
    class C13741 implements Callback<List<WalletHistory>> {
        C13741() {
        }

        public void onResponse(@NonNull Call<List<WalletHistory>> call, @NonNull Response<List<WalletHistory>> response) {
            WalletActivity.this.customDialog.dismiss();
            if (response.isSuccessful() == null) {
                try {
                    Toast.makeText(WalletActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (Response<List<WalletHistory>> response2) {
                    Toast.makeText(WalletActivity.this.context, response2.getMessage(), 1).show();
                }
            } else if (response2.body() == null || ((List) response2.body()).size() <= null) {
                WalletActivity.this.errorLayout.setVisibility(null);
            } else {
                WalletActivity.this.walletHistoryHistoryList.addAll((Collection) response2.body());
                WalletActivity.this.walletHistoryRecyclerView.getAdapter().notifyDataSetChanged();
                WalletActivity.this.errorLayout.setVisibility(8);
            }
        }

        public void onFailure(@NonNull Call<List<WalletHistory>> call, @NonNull Throwable th) {
            WalletActivity.this.customDialog.dismiss();
        }
    }
}
