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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.PromotionsAdapter;
import com.opalfire.foodorder.adapter.PromotionsAdapter.PromotionListener;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.PromotionResponse;
import com.opalfire.foodorder.models.Promotions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PromotionActivity extends AppCompatActivity implements PromotionListener {
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    Context context = this;
    CustomDialog customDialog;
    @BindView(2131296514)
    LinearLayout errorLayout;
    ArrayList<Promotions> promotionsModelArrayList;
    @BindView(2131296759)
    RecyclerView promotionsRv;
    @BindView(2131296914)
    Toolbar toolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_promotion);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07561());
        this.promotionsModelArrayList = new ArrayList();
        this.customDialog = new CustomDialog(this.context);
        this.promotionsRv.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
        this.promotionsRv.setItemAnimator(new DefaultItemAnimator());
        this.promotionsRv.setHasFixedSize(true);
        this.promotionsRv.setAdapter(new PromotionsAdapter(this.promotionsModelArrayList, this));
        getPromoDetails();
    }

    private void getPromoDetails() {
        this.customDialog.show();
        this.apiInterface.getWalletPromoCode().enqueue(new C13402());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        String string;
        super.onBackPressed();
        try {
            string = getIntent().getExtras().getString("tag");
        } catch (Exception e) {
            e.printStackTrace();
            string = null;
        }
        if (string != null && string.equalsIgnoreCase(AddMoneyActivity.TAG)) {
            startActivity(new Intent(this, AddMoneyActivity.class));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
        finish();
    }

    public void onApplyBtnClick(Promotions promotions) {
        this.customDialog.show();
        this.apiInterface.applyWalletPromoCode(String.valueOf(promotions.getId())).enqueue(new C13413());
    }

    private void gotoFlow() {
        startActivity(new Intent(this, AccountPaymentActivity.class).putExtra("is_show_wallet", true).putExtra("is_show_cash", false));
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
        finish();
    }

    /* renamed from: com.entriver.orderaround.activities.PromotionActivity$1 */
    class C07561 implements OnClickListener {
        C07561() {
        }

        public void onClick(View view) {
            PromotionActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.PromotionActivity$2 */
    class C13402 implements Callback<List<Promotions>> {
        C13402() {
        }

        public void onResponse(@NonNull Call<List<Promotions>> call, @NonNull Response<List<Promotions>> response) {
            PromotionActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                PromotionActivity.this.promotionsModelArrayList.clear();
                Log.e("onResponse: ", response.toString());
                PromotionActivity.this.promotionsModelArrayList.addAll((Collection) response.body());
                if (PromotionActivity.this.promotionsModelArrayList.size() == null) {
                    PromotionActivity.this.errorLayout.setVisibility(null);
                    return;
                } else {
                    PromotionActivity.this.promotionsRv.getAdapter().notifyDataSetChanged();
                    return;
                }
            }
            try {
                Toast.makeText(PromotionActivity.this.context, new JSONObject(response.errorBody().toString()).optString("message"), 1).show();
            } catch (Response<List<Promotions>> response2) {
                Toast.makeText(PromotionActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<List<Promotions>> call, @NonNull Throwable th) {
            PromotionActivity.this.customDialog.dismiss();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.PromotionActivity$3 */
    class C13413 implements Callback<PromotionResponse> {
        C13413() {
        }

        public void onResponse(@NonNull Call<PromotionResponse> call, @NonNull Response<PromotionResponse> response) {
            PromotionActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                Toast.makeText(PromotionActivity.this, PromotionActivity.this.getResources().getString(R.string.promo_code_apply_successfully), 0).show();
                GlobalData.profileModel.setWalletBalance(((PromotionResponse) response.body()).getWalletMoney());
                PromotionActivity.this.gotoFlow();
                return;
            }
            try {
                Toast.makeText(PromotionActivity.this.context, new JSONObject(response.errorBody().string()).optString("error"), 1).show();
            } catch (Response<PromotionResponse> response2) {
                Toast.makeText(PromotionActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<PromotionResponse> call, @NonNull Throwable th) {
            PromotionActivity.this.customDialog.dismiss();
        }
    }
}
