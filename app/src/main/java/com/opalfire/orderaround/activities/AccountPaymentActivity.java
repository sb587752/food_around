package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opalfire.orderaround.adapter.AccountPaymentAdapter;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Card;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AccountPaymentActivity extends AppCompatActivity {
    public static AccountPaymentAdapter accountPaymentAdapter;
    public static ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    public static RadioButton cashCheckBox;
    public static LinearLayout cashPaymentLayout;
    public static Context context;
    public static CustomDialog customDialog;
    public static Button proceedToPayBtn;
    public static LinearLayout walletPaymentLayout;
    boolean isCashVisible = false;
    boolean isWalletVisible = false;
    private boolean mPurchased = false;
    private boolean mShouldMakePurchase = false;
    NumberFormat numberFormat = GlobalData.getNumberFormat();

    /* renamed from: com.orderaround.user.activities.AccountPaymentActivity$1 */
    class C07101 implements OnClickListener {
        C07101() {
        }

        public void onClick(View view) {
            AccountPaymentActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.orderaround.user.activities.AccountPaymentActivity$2 */
    class C07112 implements OnClickListener {
        C07112() {
        }

        public void onClick(View view) {
            AccountPaymentActivity.cashCheckBox.setChecked(true);
            GlobalData.isCardChecked = false;
            AccountPaymentActivity.accountPaymentAdapter.notifyDataSetChanged();
            AccountPaymentActivity.proceedToPayBtn.setVisibility(View.VISIBLE);
            for (int i = 0; i < GlobalData.cardArrayList.size(); i++) {
                ((Card) GlobalData.cardArrayList.get(i)).setChecked(false);
            }
            AccountPaymentActivity.accountPaymentAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: com.orderaround.user.activities.AccountPaymentActivity$3 */
    class C07123 implements OnClickListener {
        C07123() {
        }

        public void onClick(View view) {
            int i = 0;
            if (GlobalData.isCardChecked != null) {
                while (i < GlobalData.cardArrayList.size()) {
                    if (((Card) GlobalData.cardArrayList.get(i)).isChecked() != null) {
                        Card card = (Card) GlobalData.cardArrayList.get(i);
                        CartFragment.checkoutMap.put("payment_mode", "stripe");
                        CartFragment.checkoutMap.put("card_id", String.valueOf(card.getId()));
                        AccountPaymentActivity.this.checkOut(CartFragment.checkoutMap);
                        return;
                    }
                    i++;
                }
            } else if (AccountPaymentActivity.cashCheckBox.isChecked() != null) {
                CartFragment.checkoutMap.put("payment_mode", "cash");
                AccountPaymentActivity.this.checkOut(CartFragment.checkoutMap);
            } else {
                Toast.makeText(AccountPaymentActivity.context, "Please select payment mode", 0).show();
            }
        }
    }

    /* renamed from: com.orderaround.user.activities.AccountPaymentActivity$7 */
    class C07137 implements DialogInterface.OnClickListener {
        C07137() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.orderaround.user.activities.AccountPaymentActivity$4 */
    class C12724 implements Callback<Order> {
        C12724() {
        }

        public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
            AccountPaymentActivity.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.addCart = null;
                GlobalData.notificationCount = 0;
                GlobalData.selectedShop = null;
                GlobalData.profileModel.setWalletBalance(((Order) response.body()).getUser().getWalletBalance());
                GlobalData.isSelectedOrder = new Order();
                GlobalData.isSelectedOrder = (Order) response.body();
                AccountPaymentActivity.this.startActivity(new Intent(AccountPaymentActivity.context, CurrentOrderDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                AccountPaymentActivity.this.finish();
                return;
            }
            try {
                Toast.makeText(AccountPaymentActivity.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<Order> response2) {
                Toast.makeText(AccountPaymentActivity.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<Order> call, @NonNull Throwable th) {
            Toast.makeText(AccountPaymentActivity.this, "Something went wrong", 0).show();
        }
    }

    /* renamed from: com.orderaround.user.activities.AccountPaymentActivity$5 */
    class C12735 implements Callback<List<Card>> {
        C12735() {
        }

        public void onResponse(@NonNull Call<List<Card>> call, @NonNull Response<List<Card>> response) {
            AccountPaymentActivity.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.cardArrayList.clear();
                GlobalData.cardArrayList.addAll((Collection) response.body());
                AccountPaymentActivity.accountPaymentAdapter.notifyDataSetChanged();
                return;
            }
            try {
                Toast.makeText(AccountPaymentActivity.context, new JSONObject(response.errorBody().string()).optString("error"), 1).show();
            } catch (Response<List<Card>> response2) {
                Toast.makeText(AccountPaymentActivity.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<List<Card>> call, @NonNull Throwable th) {
            AccountPaymentActivity.customDialog.dismiss();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0709R.layout.activity_account_payment);
        ButterKnife.bind(this);
        context = this;
        customDialog = new CustomDialog(context);
        cashPaymentLayout = findViewById(C0709R.id.cash_payment_layout);
        walletPaymentLayout = findViewById(C0709R.id.wallet_payment_layout);
        proceedToPayBtn = findViewById(C0709R.id.proceed_to_pay_btn);
        cashCheckBox = findViewById(C0709R.id.cash_check_box);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) C0709R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07101());
        this.isWalletVisible = getIntent().getBooleanExtra("is_show_wallet", false);
        this.isCashVisible = getIntent().getBooleanExtra("is_show_cash", false);
        GlobalData.cardArrayList = new ArrayList();
        accountPaymentAdapter = new AccountPaymentAdapter(this, GlobalData.cardArrayList, this.isCashVisible ^ 1);
        this.paymentMethodLv.setAdapter(accountPaymentAdapter);
        if (this.isWalletVisible != null) {
            walletPaymentLayout.setVisibility(View.VISIBLE);
        } else {
            walletPaymentLayout.setVisibility(View.GONE);
        }
        if (this.isCashVisible != null) {
            cashPaymentLayout.setVisibility(View.VISIBLE);
        } else {
            cashPaymentLayout.setVisibility(View.GONE);
        }
        cashPaymentLayout.setOnClickListener(new C07112());
        proceedToPayBtn.setOnClickListener(new C07123());
    }

    private void checkOut(HashMap<String, String> hashMap) {
        customDialog.show();
        apiInterface.postCheckout(hashMap).enqueue(new C12724());
    }

    private void getCardList() {
        customDialog.show();
        apiInterface.getCardList().enqueue(new C12735());
    }

    public static void deleteCard(final int i) {
        customDialog.show();
        apiInterface.deleteCard(i).enqueue(new Callback<Message>() {
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                AccountPaymentActivity.customDialog.dismiss();
                if (response.isSuccessful() != null) {
                    call = AccountPaymentActivity.context;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(((Message) response.body()).getMessage());
                    response = stringBuilder.toString();
                    int i = 0;
                    Toast.makeText(call, response, 0).show();
                    while (i < GlobalData.cardArrayList.size()) {
                        if (((Card) GlobalData.cardArrayList.get(i)).getId().equals(Integer.valueOf(i)) != null) {
                            GlobalData.cardArrayList.remove(i);
                            AccountPaymentActivity.accountPaymentAdapter.notifyDataSetChanged();
                            return;
                        }
                        i++;
                    }
                } else {
                    try {
                        Toast.makeText(AccountPaymentActivity.context, new JSONObject(response.errorBody().string()).optString("error"), 1).show();
                    } catch (Response<Message> response2) {
                        Toast.makeText(AccountPaymentActivity.context, response2.getMessage(), 1).show();
                    }
                }
            }

            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable th) {
                Toast.makeText(AccountPaymentActivity.context, "Something went wrong", 0).show();
                AccountPaymentActivity.customDialog.dismiss();
            }
        });
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(C0709R.anim.anim_nothing, C0709R.anim.slide_out_right);
    }

    protected void showDialog(String str) {
        new Builder(this).setMessage(str).setPositiveButton(17039370, new C07137()).show();
    }

    public void onSaveInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        super.onSaveInstanceState(bundle, persistableBundle);
    }

    protected void onResume() {
        super.onResume();
        int intValue = GlobalData.profileModel.getWalletBalance().intValue();
        TextView textView = this.walletAmtTxt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GlobalData.currencySymbol);
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(intValue));
        textView.setText(stringBuilder.toString());
        getCardList();
    }

    @OnClick({2131296969, 2131296297})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == C0709R.id.add_new_cart) {
            startActivity(new Intent(this, AddCardActivity.class));
            overridePendingTransition(C0709R.anim.slide_in_right, C0709R.anim.anim_nothing);
        } else if (view == C0709R.id.wallet_layout) {
            startActivity(new Intent(this, WalletActivity.class));
            overridePendingTransition(C0709R.anim.slide_in_right, C0709R.anim.anim_nothing);
            finish();
        }
    }
}
