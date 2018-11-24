package com.example.searchandrescue;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.searchandrescue.R;

public class Volunteer extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_volunteer, container, false);

        final EditText fullName = rootView.findViewById(R.id.vol_full_name), age = rootView.findViewById(R.id.vol_age);
        Spinner spinner = rootView.findViewById(R.id.spinner);
        CheckBox car = rootView.findViewById(R.id.has_car);

        car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    ((EditText) rootView.findViewById(R.id.car_name)).setVisibility(View.VISIBLE);
                    ((EditText) rootView.findViewById(R.id.car_reg_sign)).setVisibility(View.VISIBLE);
                    ((EditText) rootView.findViewById(R.id.car_seats)).setVisibility(View.VISIBLE);

                } else {

                    ((EditText) rootView.findViewById(R.id.car_name)).setVisibility(View.GONE);
                    ((EditText) rootView.findViewById(R.id.car_reg_sign)).setVisibility(View.GONE);
                    ((EditText) rootView.findViewById(R.id.car_seats)).setVisibility(View.GONE);

                }
            }
        });

        String[] array = getResources().getStringArray(R.array.sex);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(16);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setFocusChange(fullName);
        setFocusChange(age);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void setFocusChange(EditText view) {

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                EditText editText = (EditText) view;

                if (b) {
                    editText.setBackground(getResources().getDrawable(R.drawable.input_selected));
                    editText.setTextColor(Color.parseColor("#000000"));
                } else {
                    editText.setBackground(getResources().getDrawable(R.drawable.input_default));
                    editText.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

    }

}
