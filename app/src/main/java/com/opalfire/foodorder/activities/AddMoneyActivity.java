package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.AccountPaymentAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.AddMoney;
import com.opalfire.foodorder.models.Card;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddMoneyActivity extends AppCompatActivity {
    public static final String TAG = "AddMoneyActivity";
    public static AccountPaymentAdapter accountPaymentAdapter;
    @BindView(2131296317)
    EditText amountTxt;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296334)
    ImageView back;
    Context context = this;
    CustomDialog customDialog;
    NumberFormat numberFormat = GlobalData.getNumberFormat();
    @BindView(2131296721)
    Button payBtn;
    @BindView(2131296723)
    ListView paymentMethodLv;
    @BindView(2131296757)
    RelativeLayout promoLayout;
    @BindView(2131296899)
    TextView title;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_add_amount);
        ButterKnife.bind((Activity) this);
        this.customDialog = new CustomDialog(this.context);
        this.title.setText(this.context.getResources().getString(R.string.add_money));
        GlobalData.cardArrayList = new ArrayList();
        accountPaymentAdapter = new AccountPaymentAdapter(this.context, GlobalData.cardArrayList, false);
        this.paymentMethodLv.setAdapter(accountPaymentAdapter);
        this.amountTxt.setHint(GlobalData.currencySymbol);
    }

    protected void onResume() {
        super.onResume();
        getCardList();
    }

    private void getCardList() {
        this.customDialog.show();
        this.apiInterface.getCardList().enqueue(new C12791());
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, WalletActivity.class));
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
        finish();
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @OnClick({2131296334, 2131296757, 2131296721})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.back) {
            onBackPressed();
        } else if (view == R.id.pay_btn) {
            view = this.amountTxt.getText().toString();
            int i = 0;
            if (!GlobalData.isCardChecked) {
                Toast.makeText(this.context, "Please choose your card", 0).show();
            } else if (view.equalsIgnoreCase("")) {
                Toast.makeText(this.context, "Please enter amount", 0).show();
            } else if (Integer.parseInt(view) == null) {
                Toast.makeText(this.context, "Please enter valid amount", 0).show();
            } else if (GlobalData.isCardChecked != null) {
                while (i < GlobalData.cardArrayList.size()) {
                    if (((Card) GlobalData.cardArrayList.get(i)).isChecked() != null) {
                        Card card = (Card) GlobalData.cardArrayList.get(i);
                        HashMap hashMap = new HashMap();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(this.amountTxt.getText().toString());
                        hashMap.put("amount", stringBuilder.toString());
                        hashMap.put("card_id", card.getId().toString());
                        addMoney(hashMap);
                        return;
                    }
                    i++;
                }
            } else {
                Toast.makeText(this.context, "Please select your card", 0).show();
            }
        } else if (view == R.id.promo_layout) {
            startActivity(new Intent(this, PromotionActivity.class).putExtra("tag", TAG));
            overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            finish();
        }
    }

    public void alertDialog(String str) {
        Builder builder = new Builder(this.context);
        builder.setMessage(str).setPositiveButton(getResources().getString(R.string.okay), new C07162());
        str = builder.create();
        str.show();
        str = str.getButton(-1);
        str.setTextColor(ContextCompat.getColor(this.context, R.color.theme));
        str.setTypeface(str.getTypeface(), 1);
    }

    private void addMoney(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.addMoney(hashMap).enqueue(new C12803());
    }

    /* renamed from: com.opalfire.foodorder.activities.AddMoneyActivity$2 */
    class C07162 implements OnClickListener {
        C07162() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            AddMoneyActivity.this.startActivity(new Intent(AddMoneyActivity.this.context, AccountPaymentActivity.class).addFlags(67108864));
            AddMoneyActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            AddMoneyActivity.this.finish();
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.AddMoneyActivity$1 */
    class C12791 implements Callback<List<Card>> {
        C12791() {
        }

        public void onResponse(@NonNull Call<List<Card>> call, @NonNull Response<List<Card>> response) {
            AddMoneyActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.cardArrayList.clear();
                GlobalData.cardArrayList.addAll((Collection) response.body());
                AddMoneyActivity.accountPaymentAdapter.notifyDataSetChanged();
                return;
            }
            try {
                Toast.makeText(AddMoneyActivity.this.context, new JSONObject(response.errorBody().string()).optString("error"), 1).show();
            } catch (Response<List<Card>> response2) {
                Toast.makeText(AddMoneyActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<List<Card>> call, @NonNull Throwable th) {
            AddMoneyActivity.this.customDialog.dismiss();
            Toast.makeText(AddMoneyActivity.this, "Something went wrong", 0).show();
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.AddMoneyActivity$3 */
    class C12803 implements Callback<AddMoney> {
        C12803() {
        }

        public void onResponse(@NonNull Call<AddMoney> call, @NonNull Response<AddMoney> response) {
            AddMoneyActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.profileModel.setWalletBalance(((AddMoney) response.body()).getWalletBalance());
                AddMoneyActivity.this.onBackPressed();
                return;
            }
            try {
                Toast.makeText(AddMoneyActivity.this.context, new JSONObject(response.errorBody().string()).optString("card_id"), 1).show();
            } catch (Response<AddMoney> response2) {
                Toast.makeText(AddMoneyActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<AddMoney> call, @NonNull Throwable th) {
            AddMoneyActivity.this.customDialog.dismiss();
            Toast.makeText(AddMoneyActivity.this, "Something went wrong", 0).show();
        }
    }
}
