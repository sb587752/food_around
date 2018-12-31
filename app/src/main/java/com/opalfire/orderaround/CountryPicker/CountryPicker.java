package com.opalfire.orderaround.CountryPicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.opalfire.orderaround.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryPicker extends DialogFragment {
    private CountryListAdapter adapter;
    private Context mContext;
    private List<Country> countriesList = new ArrayList<>();
    private ListView countryListView;
    private CountryPickerListener listener;
    private EditText searchEditText;
    private List<Country> selectedCountriesList = new ArrayList<>();

    public CountryPicker() {
        setCountriesList(Country.getAllCountries());
    }

    public static CountryPicker newInstance(String str) {
        CountryPicker countryPicker = new CountryPicker();
        Bundle bundle = new Bundle();
        bundle.putString("dialogTitle", str);
        countryPicker.setArguments(bundle);
        return countryPicker;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.country_picker, null);
        mContext = view.getContext();
        Bundle arguments = getArguments();
        if (arguments != null) {
            getDialog().setTitle(arguments.getString("dialogTitle"));
            getDialog().getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.cp_dialog_width), getResources().getDimensionPixelSize(R.dimen.cp_dialog_height));
        }
        this.searchEditText = view.findViewById(R.id.country_code_picker_search);
        this.countryListView = view.findViewById(R.id.country_code_picker_listview);
        this.selectedCountriesList = new ArrayList<>(this.countriesList.size());
        this.selectedCountriesList.addAll(this.countriesList);
        this.adapter = new CountryListAdapter(getActivity(), this.selectedCountriesList);
        this.countryListView.setAdapter(this.adapter);
        this.countryListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CountryPicker.this.listener != null) {
                    Country country = CountryPicker.this.selectedCountriesList.get(i);
                    CountryPicker.this.listener.onSelectCountry(country.getName(), country.getCode(), country.getDialCode(), country.getFlag());
                }
            }
        });
        this.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(editable.toString());
            }
        });
        return view;
    }

    public void setListener(CountryPickerListener countryPickerListener) {
        this.listener = countryPickerListener;
    }

    @SuppressLint({"DefaultLocale"})
    private void search(String str) {
        this.selectedCountriesList.clear();
        for (Country country : this.countriesList) {
            if (country.getName().toLowerCase(Locale.ENGLISH).contains(str.toLowerCase())) {
                this.selectedCountriesList.add(country);
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    public void setCountriesList(List<Country> list) {
        this.countriesList.clear();
        this.countriesList.addAll(list);
    }


}
