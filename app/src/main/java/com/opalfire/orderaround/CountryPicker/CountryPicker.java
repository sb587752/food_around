package com.opalfire.orderaround.CountryPicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
    private Context context;
    private List<Country> countriesList = new ArrayList();
    private ListView countryListView;
    private CountryPickerListener listener;
    private EditText searchEditText;
    private List<Country> selectedCountriesList = new ArrayList();

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

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(R.layout.country_picker, null);
        viewGroup = getArguments();
        if (viewGroup != null) {
            getDialog().setTitle(viewGroup.getString("dialogTitle"));
            getDialog().getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.cp_dialog_width), getResources().getDimensionPixelSize(R.dimen.cp_dialog_height));
        }
        this.searchEditText = (EditText) layoutInflater.findViewById(R.id.country_code_picker_search);
        this.countryListView = (ListView) layoutInflater.findViewById(R.id.country_code_picker_listview);
        this.selectedCountriesList = new ArrayList(this.countriesList.size());
        this.selectedCountriesList.addAll(this.countriesList);
        this.adapter = new CountryListAdapter(getActivity(), this.selectedCountriesList);
        this.countryListView.setAdapter(this.adapter);
        this.countryListView.setOnItemClickListener(new C07031());
        this.searchEditText.addTextChangedListener(new C07042());
        return layoutInflater;
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

    /* renamed from: com.entriver.orderaround.CountryPicker.CountryPicker$1 */
    class C07031 implements OnItemClickListener {
        C07031() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (CountryPicker.this.listener != null) {
                Country country = (Country) CountryPicker.this.selectedCountriesList.get(i);
                CountryPicker.this.listener.onSelectCountry(country.getName(), country.getCode(), country.getDialCode(), country.getFlag());
            }
        }
    }

    /* renamed from: com.entriver.orderaround.CountryPicker.CountryPicker$2 */
    class C07042 implements TextWatcher {
        C07042() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            CountryPicker.this.search(editable.toString());
        }
    }
}
