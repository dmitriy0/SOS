package com.example.searchandrescue;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchandrescue.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class Volunteer extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String car_name;
    private String car_reg_sign;
    private String car_seats;
    private String full_name;
    private String vol_age;
    private String equp;
    private String[] array;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_volunteer, container, false);
        final EditText fullName = rootView.findViewById(R.id.vol_full_name), age = rootView.findViewById(R.id.vol_age);
        Spinner spinner = rootView.findViewById(R.id.spinner);
        final CheckBox car = rootView.findViewById(R.id.has_car);
        Button addVol =(Button) rootView.findViewById(R.id.addVol);
        car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    ((EditText) getActivity().findViewById(R.id.car_name)).setVisibility(View.VISIBLE);
                    ((EditText) getActivity().findViewById(R.id.car_reg_sign)).setVisibility(View.VISIBLE);
                    ((EditText) getActivity().findViewById(R.id.car_seats)).setVisibility(View.VISIBLE);
                    //car_name = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.car_name)).getText().toString();
                    //car_reg_sign = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.car_reg_sign)).getText().toString();
                    //car_seats = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.car_seats)).getText().toString();
                } else {

                    ((EditText) getActivity().findViewById(R.id.car_name)).setVisibility(View.GONE);
                    ((EditText) getActivity().findViewById(R.id.car_reg_sign)).setVisibility(View.GONE);
                    ((EditText) getActivity().findViewById(R.id.car_seats)).setVisibility(View.GONE);

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
        addVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                full_name = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.vol_full_name)).getText().toString();
                vol_age = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.vol_age)).getText().toString();
                car_name = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.car_name)).getText().toString();
                car_reg_sign = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.car_reg_sign)).getText().toString();
                car_seats = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.car_seats)).getText().toString();
                equp = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.equipment)).getText().toString();
                if(user == null){
                    Toast.makeText(getActivity(), "Пожалуйста, авторизируйтесь", Toast.LENGTH_LONG).show();
                }
                else {
                    saveDataToDatabase();
                }
            }
            });
        // Inflate the layout for this fragment
        return rootView;
    }
    private void saveDataToDatabase(){

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        // баг с тем, что занчения в бд он отправляет раньше, чем получает dbCounter
        // устанавливаем значение
        mRef.child("volunter").child(user.getUid()).child("full_name").setValue(full_name);
        mRef.child("volunter").child(user.getUid()).child("car_sign_in").setValue(car_reg_sign);
        mRef.child("volunter").child(user.getUid()).child("car_seats").setValue(car_seats);
        mRef.child("volunter").child(user.getUid()).child("car_name").setValue(car_name);
        mRef.child("volunter").child(user.getUid()).child("age").setValue(vol_age);
        mRef.child("volunter").child(user.getUid()).child("equipment").setValue(equp);
        //mRef.child("volunter").child(stringCounter).child("sex").setValue(array);
        Toast.makeText(getActivity(), "Волонтер успешно создан", Toast.LENGTH_SHORT).show();
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
