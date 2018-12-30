package com.opalfire.orderaround.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.OtherHelpActivity;
import com.opalfire.orderaround.activities.SplashActivity;
import com.opalfire.orderaround.adapter.DisputeMessageAdapter;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Order;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHelpFragment extends Fragment {
    Integer DISPUTE_HELP_ID = Integer.valueOf(View.VISIBLE);
    int DISPUTE_ID = 0;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296409)
    Button chatUs;
    Context context;
    String currency = "";
    CustomDialog customDialog;
    @BindView(2131296489)
    Button dispute;
    DisputeMessageAdapter disputeMessageAdapter;
    String disputeType;
    @BindView(2131296579)
    RecyclerView helpRv;
    int itemQuantity = 0;
    @BindView(2131296705)
    LinearLayout otherHelpLayout;
    Double priceAmount = Double.valueOf(0.0d);
    String reason = "OTHERS";
    Unbinder unbinder;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.context = getContext();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_order_help, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.customDialog = new CustomDialog(this.context);
        if (GlobalData.disputeMessageList != null) {
            this.helpRv.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
            this.helpRv.setItemAnimator(new DefaultItemAnimator());
            this.helpRv.setHasFixedSize(true);
            this.disputeMessageAdapter = new DisputeMessageAdapter(GlobalData.disputeMessageList, this.context, getActivity());
            this.helpRv.setAdapter(this.disputeMessageAdapter);
            if (GlobalData.disputeMessageList.size() > null) {
                this.otherHelpLayout.setVisibility(View.GONE);
            } else {
                this.otherHelpLayout.setVisibility(View.VISIBLE);
            }
        } else {
            startActivity(new Intent(this.context, SplashActivity.class));
            getActivity().finish();
        }
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void showDialog() {
        final String[] strArr = new String[]{"COMPLAINED", "CANCELED", "REFUND"};
        this.disputeType = "COMPLAINED";
        Builder builder = new Builder(getActivity());
        View inflate = getLayoutInflater().inflate(R.layout.dispute_dialog, null);
        builder.setView(inflate);
        final EditText editText = (EditText) inflate.findViewById(R.id.reason_edit);
        Spinner spinner = (Spinner) inflate.findViewById(R.id.dispute_type);
        SpinnerAdapter arrayAdapter = new ArrayAdapter(getActivity(), 17367048, strArr);
        arrayAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                OrderHelpFragment.this.disputeType = strArr[i];
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ORDER #000");
        stringBuilder.append(GlobalData.isSelectedOrder.getId().toString());
        builder.setTitle(stringBuilder.toString());
        builder.setMessage(this.reason);
        builder.setPositiveButton((CharSequence) "Submit", null);
        builder.setNegativeButton((CharSequence) "Cancel", new C08452());
        final AlertDialog create = builder.create();
        create.setCancelable(false);
        create.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialogInterface) {
                create.getButton(-1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equalsIgnoreCase("") != null) {
                            Toast.makeText(OrderHelpFragment.this.context, "Please enter reason", 0).show();
                            return;
                        }
                        dialogInterface.dismiss();
                        view = new HashMap();
                        view.put("order_id", GlobalData.isSelectedOrder.getId().toString());
                        view.put("status", "CREATED");
                        view.put("description", editText.getText().toString());
                        view.put("dispute_type", OrderHelpFragment.this.disputeType);
                        view.put("created_by", "user");
                        view.put("created_to", "user");
                        OrderHelpFragment.this.postDispute(view);
                    }
                });
            }
        });
        create.show();
    }

    private void postDispute(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postDispute(hashMap).enqueue(new C14014());
    }

    @OnClick({2131296409, 2131296489})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.chat_us) {
            startActivity(new Intent(getActivity(), OtherHelpActivity.class).putExtra("is_chat", true));
        } else if (view == R.id.dispute) {
            showDialog();
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.OrderHelpFragment$2 */
    class C08452 implements OnClickListener {
        C08452() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.OrderHelpFragment$4 */
    class C14014 implements Callback<Order> {
        C14014() {
        }

        public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
            OrderHelpFragment.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                Toast.makeText(OrderHelpFragment.this.context, "Dispute create successfully", 0).show();
                OrderHelpFragment.this.getActivity().finish();
                return;
            }
            try {
                Toast.makeText(OrderHelpFragment.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<Order> response2) {
                Toast.makeText(OrderHelpFragment.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<Order> call, @NonNull Throwable th) {
            OrderHelpFragment.this.customDialog.dismiss();
            Toast.makeText(OrderHelpFragment.this.context, "Something went wrong", 0).show();
        }
    }
}
