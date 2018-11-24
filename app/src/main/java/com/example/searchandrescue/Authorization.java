package com.example.searchandrescue;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Authorization extends Fragment {

    private String mLogin;
    private String mPassword;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authorization, container, false);

        /*
        EditText login = (EditText) getActivity().findViewById(R.id.login);
        EditText password = (EditText) getActivity().findViewById(R.id.password);

        setFocusChange(login);
        setFocusChange(password);
        */

        Button singIn = (Button) rootView.findViewById(R.id.singIn); // кнопка авторизации
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogin = ((EditText) getActivity().findViewById(R.id.login)).getText().toString();
                mPassword = ((EditText) getActivity().findViewById(R.id.password)).getText().toString();
                if("".equals(mLogin) || "".equals(mPassword)) {
                    Toast.makeText(getActivity(), "Одно из полей не заполненно. Пожалуйста, заполните все поля и повторите отправку", Toast.LENGTH_LONG).show();
                }
                else {
                singInUser();
                }

            }
        });

        Button singUp = (Button) rootView.findViewById(R.id.singUp); // кнопка регистрации
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogin = ((EditText) getActivity().findViewById(R.id.login)).getText().toString();
                mPassword = ((EditText) getActivity().findViewById(R.id.password)).getText().toString();
                if("".equals(mLogin) || "".equals(mPassword)) {
                    Toast.makeText(getActivity(), "Одно из полей не заполненно. Пожалуйста, заполните все поля и повторите отправку", Toast.LENGTH_LONG).show();
                }
                else {
                    addUser();
                }
            }
        });
        return rootView;
    }

    private void addUser(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(mLogin, mPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Регистрация успешна", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Регистрация провалена", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void singInUser(){
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // sing in

                }
                else {
                    // sing out
                }
            }
        };

        mAuth.signInWithEmailAndPassword(mLogin, mPassword).addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getContext(), "Авторизация успешна", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Авторизация провалена", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
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
    */

}
