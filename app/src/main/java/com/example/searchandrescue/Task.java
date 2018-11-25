package com.example.searchandrescue;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.searchandrescue.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Task extends Fragment {

    private String mNameTask;
    private String mDescrbingOfTask;
    private String mCoordiinate1;
    private String mCoordinate2 = "No coordinate 2";
    private String mEquipment;
    private String mNaturalConditions;
    private String mTime;
    private String mDate;

    //private DatabaseReference mRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        Bundle bundle = getArguments();
        String conterOfFragment = "0";
        if(bundle != null){
            conterOfFragment = bundle.getString("Value", "0");
        }
        Toast.makeText(getActivity(), conterOfFragment, Toast.LENGTH_SHORT).show();
        //changeText();

        return rootView;
    }

    public void changeText() {
        //mRef = FirebaseDatabase.getInstance().getReference();
    }

}
