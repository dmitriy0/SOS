package com.example.searchandrescue;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Auth extends AppCompatActivity {



    private String mLogin;
    private String mPassword;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);
        Button singIn = (Button) findViewById(R.id.singIn); // кнопка авторизации
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogin = ((EditText) findViewById(R.id.login)).getText().toString();
                mPassword = ((EditText) findViewById(R.id.password)).getText().toString();
                if ("".equals(mLogin) || "".equals(mPassword)) {
                    Toast.makeText(getApplicationContext(), "Одно из полей не заполненно. Пожалуйста, заполните все поля и повторите отправку", Toast.LENGTH_LONG).show();
                } else {
                    singInUser();
                }

            }
        });

        Button singUp = (Button) findViewById(R.id.singUp); // кнопка регистрации
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogin = ((EditText) findViewById(R.id.login)).getText().toString();
                mPassword = ((EditText) findViewById(R.id.password)).getText().toString();
                if ("".equals(mLogin) || "".equals(mPassword)) {
                    Toast.makeText(getApplicationContext(), "Одно из полей не заполненно. Пожалуйста, заполните все поля и повторите отправку", Toast.LENGTH_LONG).show();
                } else {
                    addUser();
                }
            }
        });
    }

    private void addUser() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        com.google.android.gms.tasks.Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(mLogin, mPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "Регистрация успешна", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Auth.this, MainActivity.class);

                    intent.putExtra("PARAM", 2);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Регистрация провалена", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void singInUser() {
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // sing in

                } else {
                    //sing out
                }
            }
        };

        mAuth.signInWithEmailAndPassword(mLogin, mPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()  {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "Авторизация успешна", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Auth.this, MainActivity.class);

                    intent.putExtra("PARAM", 1);

                    startActivity(intent);
                    finish();
                    /*Fragment fragment = new Tasks();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);*/

                } else {
                    Toast.makeText(getApplicationContext(), "Авторизация провалена", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

