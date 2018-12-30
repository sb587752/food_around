package com.opalfire.foodorder.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.ManageAddressActivity;
import com.opalfire.foodorder.activities.SaveDeliveryLocationActivity;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Address;
import com.opalfire.foodorder.models.Message;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAddressAdapter extends Adapter<MyViewHolder> {
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    private Context context;
    private List<Address> list;

    public ManageAddressAdapter(List<Address> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_list_item, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Address address = (Address) this.list.get(i);
        myViewHolder.addressLabelTxt.setText(address.getType());
        myViewHolder.addressTxt.setText(address.getMapAddress());
        setIcon(myViewHolder.iconImg, address.getType());
    }

    public int getItemCount() {
        return this.list.size();
    }

    private void setIcon(ImageView imageView, String str) {
        int hashCode = str.hashCode();
        if (hashCode != 3208415) {
            if (hashCode == 3655441) {
                if (str.equals("work") != null) {
                    str = true;
                    switch (str) {
                        case null:
                            imageView.setImageResource(R.drawable.home);
                            return;
                        case 1:
                            imageView.setImageResource(R.drawable.ic_work);
                            return;
                        default:
                            imageView.setImageResource(R.drawable.ic_map_marker);
                            return;
                    }
                }
            }
        } else if (str.equals("home") != null) {
            str = null;
            switch (str) {
                case null:
                    imageView.setImageResource(R.drawable.home);
                    return;
                case 1:
                    imageView.setImageResource(R.drawable.ic_work);
                    return;
                default:
                    imageView.setImageResource(R.drawable.ic_map_marker);
                    return;
            }
        }
        str = -1;
        switch (str) {
            case null:
                imageView.setImageResource(R.drawable.home);
                return;
            case 1:
                imageView.setImageResource(R.drawable.ic_work);
                return;
            default:
                imageView.setImageResource(R.drawable.ic_map_marker);
                return;
        }
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        private TextView addressLabelTxt;
        private TextView addressTxt;
        private Button deleteBtn;
        private Button editBtn;
        private ImageView iconImg;

        private MyViewHolder(View view) {
            super(view);
            this.addressLabelTxt = (TextView) view.findViewById(R.id.address_label);
            this.addressTxt = (TextView) view.findViewById(R.id.address);
            this.editBtn = (Button) view.findViewById(R.id.edit);
            this.deleteBtn = (Button) view.findViewById(R.id.delete);
            this.iconImg = (ImageView) view.findViewById(R.id.icon);
            this.editBtn.setOnClickListener(this);
            this.deleteBtn.setOnClickListener(this);
        }

        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (view.getId() == this.editBtn.getId()) {
                GlobalData.selectedAddress = (Address) ManageAddressAdapter.this.list.get(adapterPosition);
                view = new Intent(ManageAddressAdapter.this.context, SaveDeliveryLocationActivity.class);
                view.putExtra("edit", "yes");
                ManageAddressAdapter.this.context.startActivity(view);
            } else if (view.getId() == this.deleteBtn.getId()) {
                deleteAddress(((Address) ManageAddressAdapter.this.list.get(adapterPosition)).getId(), adapterPosition);
            }
        }

        private void deleteAddress(final Integer num, final int i) {
            if (num != null) {
                new Builder(ManageAddressAdapter.this.context).setMessage(ManageAddressAdapter.this.context.getResources().getString(R.string.delete_confirm)).setPositiveButton(17039379, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {
                        ManageAddressAdapter.this.apiInterface.deleteAddress(num.intValue()).enqueue(new C13821());
                    }

                    /* renamed from: com.entriver.orderaround.adapter.ManageAddressAdapter$MyViewHolder$1$1 */
                    class C13821 implements Callback<Message> {
                        C13821() {
                        }

                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                                try {
                                    Toast.makeText(ManageAddressAdapter.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                                } catch (Response<Message> response2) {
                                    Toast.makeText(ManageAddressAdapter.this.context, response2.getMessage(), 1).show();
                                }
                            } else if (response2 != null && response2.isSuccessful()) {
                                Toast.makeText(ManageAddressAdapter.this.context, ((Message) response2.body()).getMessage(), 1).show();
                                ManageAddressAdapter.this.list.remove(i);
                                response2 = null;
                                if (ManageAddressAdapter.this.list.size() == null) {
                                    ManageAddressActivity.errorLayout.setVisibility(View.VISIBLE);
                                } else {
                                    ManageAddressActivity.errorLayout.setVisibility(View.GONE);
                                    while (response2 < GlobalData.addressList.getAddresses().size()) {
                                        if (((Address) GlobalData.addressList.getAddresses().get(response2)).getId().equals(num) != null) {
                                            GlobalData.addressList.getAddresses().remove(response2);
                                        }
                                        response2++;
                                    }
                                }
                                ManageAddressAdapter.this.notifyItemRemoved(i);
                                ManageAddressAdapter.this.notifyItemRangeChanged(i, ManageAddressAdapter.this.list.size());
                            }
                        }

                        public void onFailure(Call<Message> call, Throwable th) {
                            Toast.makeText(ManageAddressAdapter.this.context, "Something went wrong", 1).show();
                        }
                    }
                }).setNegativeButton(17039369, null).show();
            }
        }
    }
}
