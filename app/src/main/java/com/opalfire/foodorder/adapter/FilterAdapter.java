package com.opalfire.foodorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.FilterActivity;
import com.opalfire.foodorder.fragments.HomeFragment;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Cuisine;
import com.opalfire.foodorder.models.FilterModel;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends SectionedRecyclerViewAdapter<ViewHolder> {
    public static ArrayList<Integer> cuisineIdList = new ArrayList();
    public static boolean isOfferApplied = false;
    public static boolean isPureVegApplied = false;
    boolean once = true;
    private LayoutInflater inflater;
    private List<FilterModel> list = new ArrayList();

    public FilterAdapter(Context context, List<FilterModel> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case -2:
                return new ViewHolder(this.inflater.inflate(R.layout.header_filter, viewGroup, false), true);
            case -1:
                return new ViewHolder(this.inflater.inflate(R.layout.filter_list_item, viewGroup, false), false);
            default:
                return new ViewHolder(this.inflater.inflate(R.layout.filter_list_item, viewGroup, false), false);
        }
    }

    public int getSectionCount() {
        return this.list.size();
    }

    public int getItemCount(int i) {
        return ((FilterModel) this.list.get(i)).getCuisines().size();
    }

    public void onBindHeaderViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.headerTxt.setText(((FilterModel) this.list.get(i)).getHeader());
        if (i == 0) {
            viewHolder.viewBox.setVisibility(View.GONE);
        } else {
            viewHolder.viewBox.setVisibility(View.VISIBLE);
        }
        viewHolder.headerTxt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                System.out.println(((FilterModel) FilterAdapter.this.list.get(i)).getHeader());
            }
        });
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i, final int i2, int i3) {
        viewHolder.chkSelected.setText(((Cuisine) ((FilterModel) this.list.get(i)).getCuisines().get(i2)).getName());
        int i4 = 0;
        if (((FilterModel) this.list.get(i)).getCuisines().size() == i2 + 1) {
            viewHolder.viewLine.setVisibility(View.GONE);
        } else {
            viewHolder.viewLine.setVisibility(View.VISIBLE);
        }
        cuisineIdList = new ArrayList();
        if (FilterActivity.isReset != 0) {
            viewHolder.chkSelected.setChecked(false);
            isOfferApplied = false;
            isPureVegApplied = false;
            cuisineIdList.clear();
        } else {
            if (GlobalData.cuisineIdArrayList != 0) {
                cuisineIdList.addAll(GlobalData.cuisineIdArrayList);
            }
            if (((FilterModel) this.list.get(i)).getHeader().equalsIgnoreCase("Cuisines") != 0) {
                if (cuisineIdList.size() != 0) {
                    Cuisine cuisine = (Cuisine) ((FilterModel) this.list.get(i)).getCuisines().get(i2);
                    while (i4 < cuisineIdList.size()) {
                        if (((Integer) cuisineIdList.get(i4)).equals(cuisine.getId())) {
                            viewHolder.chkSelected.setChecked(true);
                        }
                        i4++;
                    }
                } else {
                    viewHolder.chkSelected.setChecked(false);
                }
            } else if (((Cuisine) ((FilterModel) this.list.get(i)).getCuisines().get(i2)).getName().equalsIgnoreCase("Offers") != 0) {
                viewHolder.chkSelected.setChecked(isOfferApplied);
            } else if (((Cuisine) ((FilterModel) this.list.get(i)).getCuisines().get(i2)).getName().equalsIgnoreCase("Pure veg") != 0) {
                viewHolder.chkSelected.setChecked(isPureVegApplied);
            }
        }
        if (HomeFragment.isFilterApplied != 0) {
            FilterActivity.applyFilterBtn.setAlpha(1.0f);
            FilterActivity.resetTxt.setAlpha(1.0f);
            FilterActivity.applyFilterBtn.setClickable(true);
            FilterActivity.resetTxt.setClickable(true);
            FilterActivity.applyFilterBtn.setEnabled(true);
            FilterActivity.resetTxt.setEnabled(true);
        }
        viewHolder.chkSelected.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    if (((FilterModel) FilterAdapter.this.list.get(i)).getHeader().equalsIgnoreCase("Cuisines") != null) {
                        FilterAdapter.cuisineIdList.add(((Cuisine) ((FilterModel) FilterAdapter.this.list.get(i)).getCuisines().get(i2)).getId());
                    } else if (((Cuisine) ((FilterModel) FilterAdapter.this.list.get(i)).getCuisines().get(i2)).getName().equalsIgnoreCase("Offers") != null) {
                        FilterAdapter.isOfferApplied = true;
                    } else if (((Cuisine) ((FilterModel) FilterAdapter.this.list.get(i)).getCuisines().get(i2)).getName().equalsIgnoreCase("Pure veg") != null) {
                        FilterAdapter.isPureVegApplied = true;
                    }
                    FilterActivity.applyFilterBtn.setAlpha(1.0f);
                    FilterActivity.resetTxt.setAlpha(1.0f);
                    FilterActivity.applyFilterBtn.setClickable(true);
                    FilterActivity.applyFilterBtn.setEnabled(true);
                    FilterActivity.resetTxt.setEnabled(true);
                    FilterActivity.resetTxt.setClickable(true);
                    return;
                }
                if (((FilterModel) FilterAdapter.this.list.get(i)).getHeader().equalsIgnoreCase("Cuisines") != null) {
                    Cuisine cuisine = (Cuisine) ((FilterModel) FilterAdapter.this.list.get(i)).getCuisines().get(i2);
                    for (int i = 0; i < FilterAdapter.cuisineIdList.size(); i++) {
                        if (((Integer) FilterAdapter.cuisineIdList.get(i)).equals(cuisine.getId())) {
                            FilterAdapter.cuisineIdList.remove(i);
                            break;
                        }
                    }
                } else if (((Cuisine) ((FilterModel) FilterAdapter.this.list.get(i)).getCuisines().get(i2)).getName().equalsIgnoreCase("Offers") != null) {
                    FilterAdapter.isOfferApplied = false;
                } else if (((Cuisine) ((FilterModel) FilterAdapter.this.list.get(i)).getCuisines().get(i2)).getName().equalsIgnoreCase("Pure veg") != null) {
                    FilterAdapter.isPureVegApplied = false;
                }
                if (FilterAdapter.cuisineIdList.size() == null && FilterAdapter.isPureVegApplied == null && FilterAdapter.isOfferApplied == null && HomeFragment.isFilterApplied == null) {
                    FilterActivity.applyFilterBtn.setAlpha(0.5f);
                    FilterActivity.applyFilterBtn.setClickable(false);
                    FilterActivity.applyFilterBtn.setEnabled(false);
                    FilterActivity.resetTxt.setAlpha(0.5f);
                    FilterActivity.resetTxt.setClickable(false);
                    FilterActivity.resetTxt.setEnabled(false);
                }
            }
        });
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        CheckBox chkSelected;
        TextView headerTxt;
        LinearLayout itemLayout;
        View viewBox;
        View viewLine;

        public ViewHolder(View view, boolean z) {
            super(view);
            if (z) {
                this.headerTxt = (TextView) view.findViewById(R.id.header);
                this.viewBox = view.findViewById(R.id.view_box);
                return;
            }
            this.itemLayout = (LinearLayout) view.findViewById(R.id.item_layout);
            this.chkSelected = (CheckBox) view.findViewById(R.id.chk_selected);
            this.viewLine = view.findViewById(R.id.view_line);
        }
    }
}
