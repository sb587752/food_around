package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.user.Pubnub.ChatFragment;
import com.foodorder.user.R;
import com.foodorder.user.build.api.ApiClient;
import com.foodorder.user.build.api.ApiInterface;
import com.foodorder.user.helper.CustomDialog;
import com.foodorder.user.helper.GlobalData;
import com.foodorder.user.models.Item;
import com.foodorder.user.models.Order;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OtherHelpActivity extends AppCompatActivity {
    Integer DISPUTE_HELP_ID = Integer.valueOf(0);
    int DISPUTE_ID = 0;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296409)
    Button chatUs;
    Context context;
    String currency = "";
    CustomDialog customDialog;
    @BindView(2131296489)
    Button dispute;
    String disputeType;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    boolean isChat = false;
    int itemQuantity = 0;
    @BindView(2131296689)
    TextView orderIdTxt;
    @BindView(2131296692)
    TextView orderItemTxt;
    Double priceAmount = Double.valueOf(0.0d);
    String reason;
    @BindView(2131296765)
    TextView reasonDescription;
    @BindView(2131296767)
    TextView reasonTitle;
    @BindView(2131296914)
    Toolbar toolbar;

    protected void onCreate(Bundle bundle) {
        TextView textView;
        StringBuilder stringBuilder;
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_other_help);
        ButterKnife.bind((Activity) this);
        this.context = this;
        this.customDialog = new CustomDialog(this.context);
        this.fragmentManager = getSupportFragmentManager();
        this.reason = getIntent().getExtras().getString("type");
        this.DISPUTE_HELP_ID = Integer.valueOf(getIntent().getExtras().getInt("id"));
        bundle = GlobalData.isSelectedOrder;
        this.itemQuantity = bundle.getInvoice().getQuantity().intValue();
        this.priceAmount = bundle.getInvoice().getNet();
        this.currency = ((Item) bundle.getItems().get(0)).getProduct().getPrices().getCurrency();
        if (this.itemQuantity == 1) {
            textView = this.orderItemTxt;
            stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(this.itemQuantity));
            stringBuilder.append(" Item, ");
            stringBuilder.append(this.currency);
            stringBuilder.append(String.valueOf(this.priceAmount));
            textView.setText(stringBuilder.toString());
        } else {
            textView = this.orderItemTxt;
            stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(this.itemQuantity));
            stringBuilder.append(" Items, ");
            stringBuilder.append(this.currency);
            stringBuilder.append(String.valueOf(this.priceAmount));
            textView.setText(stringBuilder.toString());
        }
        textView = this.orderIdTxt;
        stringBuilder = new StringBuilder();
        stringBuilder.append("ORDER #000");
        stringBuilder.append(bundle.getId().toString());
        textView.setText(stringBuilder.toString());
        this.reasonTitle.setText(this.reason);
        this.isChat = getIntent().getBooleanExtra("is_chat", false);
        if (this.isChat != null) {
            this.chatUs.performClick();
        }
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07461());
    }

    private void showDialog() {
        final String[] strArr = new String[]{"COMPLAINED", "CANCELED", "REFUND"};
        this.disputeType = "COMPLAINED";
        Builder builder = new Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.dispute_dialog, null);
        builder.setView(inflate);
        final EditText editText = (EditText) inflate.findViewById(R.id.reason_edit);
        Spinner spinner = (Spinner) inflate.findViewById(R.id.dispute_type);
        SpinnerAdapter arrayAdapter = new ArrayAdapter(this, 17367048, strArr);
        arrayAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                OtherHelpActivity.this.disputeType = strArr[i];
            }
        });
        builder.setTitle(this.orderIdTxt.getText().toString());
        builder.setMessage(this.reason);
        builder.setPositiveButton(getString(R.string.submit), null);
        builder.setNegativeButton(getString(R.string.cancel), new C07483());
        final AlertDialog create = builder.create();
        create.setCancelable(false);
        create.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialogInterface) {
                create.getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equalsIgnoreCase("") != null) {
                            Toast.makeText(OtherHelpActivity.this.context, "Please enter reason", 0).show();
                            return;
                        }
                        dialogInterface.dismiss();
                        view = new HashMap();
                        view.put("order_id", GlobalData.isSelectedOrder.getId().toString());
                        view.put("status", "CREATED");
                        view.put("description", editText.getText().toString());
                        view.put("dispute_type", OtherHelpActivity.this.disputeType);
                        view.put("created_by", "user");
                        view.put("created_to", "user");
                        if (!OtherHelpActivity.this.disputeType.equalsIgnoreCase("others")) {
                            view.put("disputehelp_id", OtherHelpActivity.this.DISPUTE_HELP_ID.toString());
                        }
                        OtherHelpActivity.this.postDispute(view);
                    }
                });
            }
        });
        create.show();
    }

    @OnClick({2131296409, 2131296489})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.chat_us) {
            this.fragmentTransaction = this.fragmentManager.beginTransaction();
            this.fragmentTransaction.replace(R.id.chat_fragment, new ChatFragment(), "Tamil");
            this.fragmentTransaction.commit();
        } else if (view == R.id.dispute) {
            showDialog();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    /* renamed from: com.foodorder.user.activities.OtherHelpActivity$1 */
    class C07461 implements OnClickListener {
        C07461() {
        }

        public void onClick(View view) {
            OtherHelpActivity.this.onBackPressed();
        }
    }

    private void postDispute(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postDispute(hashMap).enqueue(new C13285());
    }

    /* renamed from: com.foodorder.user.activities.OtherHelpActivity$3 */
    class C07483 implements DialogInterface.OnClickListener {
        C07483() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.foodorder.user.activities.OtherHelpActivity$5 */
    class C13285 implements Callback<Order> {
        public void onFailure(@NonNull Call<Order> call, @NonNull Throwable th) {
        }

        C13285() {
        }

        public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
            OtherHelpActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                Toast.makeText(OtherHelpActivity.this, "Dispute create successfully", 0).show();
                OtherHelpActivity.this.finish();
                return;
            }
            try {
                Toast.makeText(OtherHelpActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<Order> response2) {
                Toast.makeText(OtherHelpActivity.this.context, response2.getMessage(), 1).show();
            }
        }
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
