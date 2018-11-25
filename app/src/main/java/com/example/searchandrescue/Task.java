package com.example.searchandrescue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



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
        changeText();

        return rootView;
    }
    @SuppressLint("WrongViewCast")
    public void changeText() {
        mNameTask = ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.nameFromDatabase)).getText().toString();
        mDescrbingOfTask = ((EditText) getActivity().findViewById(R.id.descridingFromDatabase)).getText().toString();
        mCoordiinate1 = ((EditText) getActivity().findViewById(R.id.coordinate1From)).getText().toString();
        mCoordinate2 = ((EditText) getActivity().findViewById(R.id.coordinate2From)).getText().toString();
        mEquipment = ((EditText) getActivity().findViewById(R.id.equipmentFromDatabase)).getText().toString();
        mNaturalConditions = ((EditText) getActivity().findViewById(R.id.naturalConditionsFrom)).getText().toString();
        mTime = ((EditText) getActivity().findViewById(R.id.timeFrom)).getText().toString();
        mDate = ((EditText) getActivity().findViewById(R.id.dateFrom)).getText().toString();

    }

}
