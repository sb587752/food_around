package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.build.configure.BuildConfigure;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.models.Message;
import com.opalfire.foodorder.utils.Utils;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONObject;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddCardActivity extends AppCompatActivity {
    static final Pattern CODE_PATTERN = Pattern.compile("([0-9]{0,4})|([0-9]{4}-)+|([0-9]{4}-[0-9]{0,4})+");
    String Card_Token = "";
    Activity activity;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    Context context;
    CustomDialog customDialog;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.card_form)
    CardForm cardForm;
    @BindView(R.id.addCard)
    Button addCard;
    @BindView(R.id.activity_add_card)
    LinearLayout activityAddCard;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        context = this;
        activity = this;
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon((int) R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                .actionLabel("Add CardDetails")
                .setup(activity);
        addCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new CustomDialog(context);
                customDialog.setCancelable(false);
                if (customDialog != null) {
                    customDialog.show();
                }
                if ((cardForm.getCardNumber() != null) && (cardForm.getExpirationMonth() != null) && (cardForm.getExpirationYear() != null) && (cardForm.getCvv() != null)) {
                    if ((!cardForm.getCardNumber().equals("")) && (!cardForm.getExpirationMonth().equals("")) && (!cardForm.getExpirationYear().equals("")) && (!cardForm.getCvv().equals(""))) {
                        int i = Integer.parseInt(cardForm.getExpirationMonth());
                        int j = Integer.parseInt(cardForm.getExpirationYear());
                        String str = cardForm.getCvv();
                        String localStringBuilder = "CardDetails Number: " +
                                cardForm.getCardNumber() +
                                "Month: " +
                                i +
                                " Year: " +
                                j;
                        Utils.print("MyTest", localStringBuilder);
                        Card card = new Card(cardForm.getCardNumber(), i, j, str);
                        try {
                            new Stripe(AddCardActivity.this, BuildConfigure.STRIPE_PK).createToken(card, new TokenCallback() {
                                @Override
                                public void onError(Exception paramAnonymous2Exception) {
                                    displayMessage(context.getResources().getString(R.string.enter_card_details));
                                    if ((customDialog != null) && (customDialog.isShowing())) {
                                        customDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onSuccess(Token token) {
                                    Utils.print("CardToken:", token.getId());
                                    Utils.print("CardToken:", token.getCard().getLast4());
                                    Card_Token = token.getId();
                                    addCardToAccount(Card_Token);
                                }
                            });
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (customDialog == null) {
                                return;
                            }
                        }
                        if (customDialog.isShowing()) {
                            customDialog.dismiss();
                        }
                    } else {
                        if ((customDialog != null) && (customDialog.isShowing())) {
                            customDialog.dismiss();
                        }
                        displayMessage(context.getResources().getString(R.string.enter_card_details));
                    }
                } else {
                    if ((customDialog != null) && (customDialog.isShowing())) {
                        customDialog.dismiss();
                    }
                    displayMessage(context.getResources().getString(R.string.enter_card_details));
                }
            }
        });
    }

    public void addCardToAccount(String str) {
        Log.e("stripe_token", str);
        apiInterface.addCard(str).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                try {
                    Toast.makeText(context, new JSONObject(response.errorBody().string()).optString("error"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void displayMessage(String str) {
        Snackbar.make(getCurrentFocus(), str, -1).setAction("Action", null).show();
    }

    public void GoToBeginActivity() {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        activity.finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


}
