package com.example.searchandrescue;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class Volunteer extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String car_name;
    private String car_reg_sign;
    private String car_seats;
    private String full_name;
    private String vol_age;
    private  String car_type;
    private String equp;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();


    static final int GALLERY_REQUEST = 1;

    View root;
    LayoutInflater inflate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_volunteer, container, false);
        final EditText fullName = rootView.findViewById(R.id.vol_full_name), age = rootView.findViewById(R.id.vol_age);
        final CheckBox car = rootView.findViewById(R.id.has_car);
        Button addVol =(Button) rootView.findViewById(R.id.addVol);
        ImageView avatar = rootView.findViewById(R.id.avatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQUEST);

            }
        });

        root = rootView;
        inflate = inflater;
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
                    ((EditText) getActivity().findViewById(R.id.car_type)).setVisibility(View.VISIBLE);

                } else {

                    ((EditText) getActivity().findViewById(R.id.car_name)).setVisibility(View.GONE);
                    ((EditText) getActivity().findViewById(R.id.car_reg_sign)).setVisibility(View.GONE);
                    ((EditText) getActivity().findViewById(R.id.car_seats)).setVisibility(View.GONE);
                    ((EditText) getActivity().findViewById(R.id.car_type)).setVisibility(View.GONE);

                }
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
                car_type = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.car_type)).getText().toString();
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
        mRef.child("volunter").child(user.getUid()).child("car_type").setValue(car_type);
        mRef.child("volunter").child(user.getUid()).child("car_sign_in").setValue(car_reg_sign);
        mRef.child("volunter").child(user.getUid()).child("car_seats").setValue(car_seats);
        mRef.child("volunter").child(user.getUid()).child("car_name").setValue(car_name);
        mRef.child("volunter").child(user.getUid()).child("age").setValue(vol_age);
        mRef.child("volunter").child(user.getUid()).child("equipment").setValue(equp);
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

    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {

        super.onActivityResult(requestCode, resultCode, resultIntent);

        ImageView avatar = (ImageView) root.findViewById(R.id.avatar);

        if (resultCode == -1) {

            switch (requestCode) {

                case GALLERY_REQUEST:
                    Uri selectedImage = resultIntent.getData();

                    Picasso.with(getContext())
                            .load(selectedImage)
                            .transform(new CircularTransformation())
                            .into(avatar);


            }

        }

    }

}
