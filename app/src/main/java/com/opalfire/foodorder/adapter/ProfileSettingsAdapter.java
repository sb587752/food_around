package com.opalfire.foodorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.opalfire.foodorder.R;

import java.util.List;

public class ProfileSettingsAdapter extends BaseAdapter {
    private static final String LOG_TAG = "ProfileSettingsAdapter";
    private Context context_;
    private List<String> items;
    private List<Integer> listIcon;

    public ProfileSettingsAdapter(Context context, List<String> list, List<Integer> list2) {
        this.context_ = context;
        this.items = list;
        this.listIcon = list2;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getCount() {
        return this.items.size();
    }

    public Object getItem(int i) {
        return this.items.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.context_.getSystemService("layout_inflater")).inflate(R.layout.profile_settings_list_item, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.setting_label);
        ((ImageView) view.findViewById(R.id.setting_icon)).setImageResource(((Integer) this.listIcon.get(i)).intValue());
        textView.setText((CharSequence) this.items.get(i));
        i = AnimationUtils.loadAnimation(this.context_, R.anim.anim_push_left_in);
        i.setDuration(400);
        view.startAnimation(i);
        return view;
    }
}
