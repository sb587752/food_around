package com.opalfire.foodorder.CountryPicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.opalfire.foodorder.R;

import java.util.List;

public class CountryListAdapter extends BaseAdapter {
    List<Country> countries;
    LayoutInflater inflater;
    private Context mContext;

    public CountryListAdapter(Context context, List<Country> list) {
        this.mContext = context;
        this.countries = list;
        this.inflater = LayoutInflater.from(context);
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public int getCount() {
        return this.countries.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Country country = this.countries.get(i);
        if (view == null) {
            view = this.inflater.inflate(R.layout.row, null);
        }
        Cell cell = Cell.from(view);
        cell.textView.setText(country.getName());
        country.loadFlagByCode(this.mContext);
        if (country.getFlag() != -1) {
            cell.imageView.setImageResource(country.getFlag());
        }
        return view;
    }

    static class Cell {
        public ImageView imageView;
        public TextView textView;

        Cell() {
        }

        static Cell from(View view) {
            if (view == null) {
                return null;
            }
            if (view.getTag() != null) {
                return (Cell) view.getTag();
            }
            Cell cell = new Cell();
            cell.textView = view.findViewById(R.id.row_title);
            cell.imageView = view.findViewById(R.id.row_icon);
            view.setTag(cell);
            return cell;
        }
    }
}
