package com.opalfire.foodorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.SetDeliveryLocationActivity;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Address;
import com.opalfire.foodorder.models.AddressList;

import java.util.ArrayList;
import java.util.List;

public class DeliveryLocationAdapter extends SectionedRecyclerViewAdapter<ViewHolder> {
    Activity activity;
    Context context;
    private LayoutInflater inflater;
    private List<AddressList> list = new ArrayList();

    public DeliveryLocationAdapter(Context context, Activity activity, List<AddressList> list) {
        this.context = context;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case -2:
                return new ViewHolder(this.inflater.inflate(R.layout.header, viewGroup, false), true);
            case -1:
                return new ViewHolder(this.inflater.inflate(R.layout.location_list_item, viewGroup, false), false);
            default:
                return new ViewHolder(this.inflater.inflate(R.layout.location_list_item, viewGroup, false), false);
        }
    }

    public int getSectionCount() {
        return this.list.size();
    }

    public int getItemCount(int i) {
        return ((AddressList) this.list.get(i)).getAddresses().size();
    }

    public void onBindHeaderViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.path.setText(((AddressList) this.list.get(i)).getHeader());
        viewHolder.path.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                System.out.println(((AddressList) DeliveryLocationAdapter.this.list.get(i)).getHeader());
            }
        });
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i, int i2, int i3) {
        final Address address = (Address) ((AddressList) this.list.get(i)).getAddresses().get(i2);
        viewHolder.addressLabel.setText(address.getType());
        viewHolder.address.setText(address.getMapAddress());
        setIcon(viewHolder.icon, address.getType());
        viewHolder.itemLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SetDeliveryLocationActivity.isAddressSelection != null) {
                    view = new Intent();
                    GlobalData.selectedAddress = address;
                    DeliveryLocationAdapter.this.activity.setResult(-1, view);
                    DeliveryLocationAdapter.this.activity.finish();
                }
            }
        });
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

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView address;
        TextView addressLabel;
        ImageView icon;
        LinearLayout itemLayout;
        TextView path;

        public ViewHolder(View view, boolean z) {
            super(view);
            if (z) {
                this.path = (TextView) view.findViewById(R.id.header);
                return;
            }
            this.itemLayout = (LinearLayout) view.findViewById(R.id.item_layout);
            this.addressLabel = (TextView) view.findViewById(R.id.address_label);
            this.address = (TextView) view.findViewById(R.id.address);
            this.icon = (ImageView) view.findViewById(R.id.icon);
        }
    }
}
