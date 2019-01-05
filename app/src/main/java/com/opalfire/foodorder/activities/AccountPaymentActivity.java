package com.opalfire.foodorder.activities;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.AccountPaymentAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.fragments.CartFragment;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Card;
import com.opalfire.foodorder.models.Message;
import com.opalfire.foodorder.models.Order;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AccountPaymentActivity extends AppCompatActivity {
    public static AccountPaymentAdapter accountPaymentAdapter;
    public static ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    public static RadioButton cashCheckBox;
    public static LinearLayout cashPaymentLayout;
    public static Context mContext;
    public static CustomDialog customDialog;
    public static Button proceedToPayBtn;
    public static LinearLayout walletPaymentLayout;
    boolean isCashVisible = false;
    boolean isWalletVisible = false;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wallet_amount_txt)
    TextView walletAmountTxt;
    @BindView(R.id.wallet_layout)
    RelativeLayout walletLayout;
    @BindView(R.id.payment_method_lv)
    ListView paymentMethodLv;
    @BindView(R.id.add_card_img)
    ImageView addCardImg;
    @BindView(R.id.add_new_cart)
    TextView addNewCart;
    @BindView(R.id.cash_img)
    ImageView cashImg;
    NumberFormat numberFormat = GlobalData.getNumberFormat();
    private boolean mPurchased = false;
    private boolean mShouldMakePurchase = false;

    public static void deleteCard(final int i) {
        customDialog.show();
        apiInterface.deleteCard(i).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    int i = 0;
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    while (i < GlobalData.cardArrayList.size()) {
                        if (GlobalData.cardArrayList.get(i).getId().equals(i)) {
                            GlobalData.cardArrayList.remove(i);
                            accountPaymentAdapter.notifyDataSetChanged();
                            return;
                        }
                        i++;
                    }
                } else {
                    try {
                        Toast.makeText(mContext, new JSONObject(response.errorBody().string()).optString("error"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable th) {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                customDialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_account_payment);
        ButterKnife.bind(this);
        mContext = this;
        customDialog = new CustomDialog(mContext);
        cashPaymentLayout = findViewById(R.id.cash_payment_layout);
        walletPaymentLayout = findViewById(R.id.wallet_payment_layout);
        proceedToPayBtn = findViewById(R.id.proceed_to_pay_btn);
        cashCheckBox = findViewById(R.id.cash_check_box);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        isWalletVisible = getIntent().getBooleanExtra("is_show_wallet", false);
        isCashVisible = getIntent().getBooleanExtra("is_show_cash", false);
        GlobalData.cardArrayList = new ArrayList<>();
        accountPaymentAdapter = new AccountPaymentAdapter(this, GlobalData.cardArrayList, isCashVisible);
        paymentMethodLv.setAdapter(accountPaymentAdapter);
        if (isWalletVisible) {
            walletPaymentLayout.setVisibility(View.VISIBLE);
        } else {
            walletPaymentLayout.setVisibility(View.GONE);
        }
        if (isCashVisible) {
            cashPaymentLayout.setVisibility(View.VISIBLE);
        } else {
            cashPaymentLayout.setVisibility(View.GONE);
        }
        cashPaymentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cashCheckBox.setChecked(true);
                GlobalData.isCardChecked = false;
                accountPaymentAdapter.notifyDataSetChanged();
                proceedToPayBtn.setVisibility(View.VISIBLE);
                int i = 0;
                while (i < GlobalData.cardArrayList.size()) {
                    GlobalData.cardArrayList.get(i).setChecked(false);
                    i += 1;
                }
                accountPaymentAdapter.notifyDataSetChanged();
            }
        });
        proceedToPayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bool = GlobalData.isCardChecked;
                int i = 0;
                if (bool) {
                    while (i < GlobalData.cardArrayList.size()) {
                        if (GlobalData.cardArrayList.get(i).isChecked()) {
                            Card card = GlobalData.cardArrayList.get(i);
                            CartFragment.checkoutMap.put("payment_mode", "stripe");
                            CartFragment.checkoutMap.put("card_id", String.valueOf(card.getId()));
                            checkOut(CartFragment.checkoutMap);
                            return;
                        }
                        i += 1;
                    }
                }
                if (cashCheckBox.isChecked()) {
                    CartFragment.checkoutMap.put("payment_mode", "cash");
                    checkOut(CartFragment.checkoutMap);
                    return;
                }
                Toast.makeText(mContext, "Please select payment mode", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkOut(HashMap<String, String> hashMap) {
        customDialog.show();
        apiInterface.postCheckout(hashMap).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    GlobalData.addCart = null;
                    GlobalData.notificationCount = 0;
                    GlobalData.selectedShop = null;
                    GlobalData.profileModel.setWalletBalance(response.body().getUser().getWalletBalance());
                    GlobalData.isSelectedOrder = new Order();
                    GlobalData.isSelectedOrder = response.body();
                    startActivity(new Intent(mContext, CurrentOrderDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    return;
                }
                try {
                    Toast.makeText(mContext, new JSONObject(response.errorBody().string()).optString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getCardList() {
        customDialog.show();
        apiInterface.getCardList().enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    GlobalData.cardArrayList.clear();
                    GlobalData.cardArrayList.addAll(response.body());
                    accountPaymentAdapter.notifyDataSetChanged();
                    return;
                }
                try {
                    Toast.makeText(mContext, new JSONObject(response.errorBody().string()).optString("error"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    protected void showDialog(String str) {
        new AlertDialog.Builder(this).setMessage(str).setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void onSaveInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        super.onSaveInstanceState(bundle, persistableBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int intValue = GlobalData.profileModel.getWalletBalance();
        String stringBuilder = GlobalData.currencySymbol +
                " " +
                String.valueOf(intValue);
        walletAmountTxt.setText(stringBuilder);
        getCardList();
    }

    @OnClick({R.id.add_new_cart, R.id.wallet_layout})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.add_new_cart) {
            startActivity(new Intent(this, AddCardActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
        } else if (view.getId() == R.id.wallet_layout) {
            startActivity(new Intent(this, WalletActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            finish();
        }
    }

}
