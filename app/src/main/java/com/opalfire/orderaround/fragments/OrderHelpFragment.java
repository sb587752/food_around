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
import android.widget.TextView;
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
import com.opalfire.orderaround.models.Search;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHelpFragment extends Fragment {
    Integer DISPUTE_HELP_ID = View.VISIBLE;
    int DISPUTE_ID = 0;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    Context context;
    String currency = "";
    CustomDialog customDialog;
    DisputeMessageAdapter disputeMessageAdapter;
    int itemQuantity = 0;
    Double priceAmount = 0.0d;
    String reason = "OTHERS";
    Unbinder unbinder;
    @BindView(R.id.help_rv)
    RecyclerView helpRv;
    @BindView(R.id.reason_description)
    TextView reasonDescription;
    @BindView(R.id.dispute)
    Button dispute;
    @BindView(R.id.chat_us)
    Button chatUs;
    @BindView(R.id.other_help_layout)
    LinearLayout otherHelpLayout;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        context = getContext();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_order_help, viewGroup, false);
        unbinder = ButterKnife.bind(this, inflate);
        customDialog = new CustomDialog(context);
        if (GlobalData.disputeMessageList != null) {
            helpRv.setLayoutManager(new LinearLayoutManager(context, 1, false));
            helpRv.setItemAnimator(new DefaultItemAnimator());
            helpRv.setHasFixedSize(true);
            disputeMessageAdapter = new DisputeMessageAdapter(GlobalData.disputeMessageList, context, getActivity());
            helpRv.setAdapter(disputeMessageAdapter);
            if (GlobalData.disputeMessageList.size() > 0) {
                otherHelpLayout.setVisibility(View.GONE);
            } else {
                otherHelpLayout.setVisibility(View.VISIBLE);
            }
        } else {
            startActivity(new Intent(context, SplashActivity.class));
            getActivity().finish();
        }
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showDialog() {
        final String[] strArr = new String[]{"COMPLAINED", "CANCELED", "REFUND"};
        final String[] disputeType = {"COMPLAINED"};
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dispute_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editText = dialogView.findViewById(R.id.reason_edit);
        Spinner spinner = dialogView.findViewById(R.id.dispute_type);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, strArr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                disputeType[0] = strArr[i];
            }
        });
        final AlertDialog alertDialog = dialogBuilder.create();
        dialogBuilder.setTitle("ORDER #000" +
                GlobalData.isSelectedOrder.getId().toString());
        dialogBuilder.setMessage(reason);
        dialogBuilder.setPositiveButton("Submit", null);
        dialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        dialogBuilder.setCancelable(false);
        alertDialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!editText.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(context, "Please enter reason", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dialogInterface.dismiss();
                        HashMap<String, String> sdfa = new HashMap<>();
                        sdfa.put("order_id", GlobalData.isSelectedOrder.getId().toString());
                        sdfa.put("status", "CREATED");
                        sdfa.put("description", editText.getText().toString());
                        sdfa.put("dispute_type", disputeType[0]);
                        sdfa.put("created_by", "user");
                        sdfa.put("created_to", "user");
                        postDispute(sdfa);
                    }
                });
            }
        });

        alertDialog.show();
    }

    private void postDispute(HashMap<String, String> hashMap) {
        customDialog.show();
        apiInterface.postDispute(hashMap).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Dispute create successfully", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    return;
                }
                try {
                    Toast.makeText(context, new JSONObject(response.errorBody().string()).optString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                customDialog.dismiss();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.chat_us, R.id.dispute})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.chat_us) {
            startActivity(new Intent(getActivity(), OtherHelpActivity.class).putExtra("is_chat", true));
        } else if (view.getId() == R.id.dispute) {
            showDialog();
        }
    }

}
