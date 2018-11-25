package com.example.searchandrescue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class Volonters2 extends Fragment {
    private DatabaseReference mRef;
    private List<String> mVolonters;
    ListView listVolonters;
    private List<String> mRating;
    ListView listRating;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_volonters2, container, false);
        listVolonters = (ListView) rootView.findViewById(R.id.discr_for_volonters);
        mRef = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                GenericTypeIndicator<List<String>> t1 = new GenericTypeIndicator<List<String>>() {};
                mVolonters = dataSnapshot.child("ratingOfVolonterNames").getValue(t);
                mRating = dataSnapshot.child("ratingOfVolonterAchivs").getValue(t1);
                //Toast.makeText(getActivity(), dataSnapshot.child("allTasks").child("1").getValue(String.class), Toast.LENGTH_SHORT).show();updateUI();
                updateUI();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
    public void updateUI(){
        if (getActivity() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getBaseContext()), android.R.layout.simple_list_item_1, mVolonters);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getBaseContext()), android.R.layout.simple_list_item_2, mRating);
            listVolonters.setAdapter(adapter);
            listRating.setAdapter(adapter2);
        }
    }
}
