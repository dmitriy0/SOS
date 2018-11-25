package com.junior.stronger197.sos;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchandrescue.Blog;
import com.example.searchandrescue.Profile;
import com.example.searchandrescue.R;
import com.example.searchandrescue.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NewTaskAfterAdd extends Fragment {
    private TextView mNameTask;
    private TextView mDescrbingOfTask;
    private TextView mCoordiinate;
    private TextView mTime;
    private String idUser;

    public String ValueForNewTaskAfterAdd = "0";
    public String ValueOfStartFragment = "0";

    private DatabaseReference mRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_task_add, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            ValueForNewTaskAfterAdd = bundle.getString("ValueForNewTaskAfterAdd", "0");
            ValueOfStartFragment = bundle.getString("ValueOfStartFragment", "0");
        }

        mNameTask = (TextView) rootView.findViewById(R.id.nameNewTask);
        mDescrbingOfTask = (TextView) rootView.findViewById(R.id.descridingNewTask);
        mCoordiinate = (TextView) rootView.findViewById(R.id.coordinateNewTask);
        mTime = (TextView) rootView.findViewById(R.id.timeNewTask);

        changeText();

        Button back = (Button) rootView.findViewById(R.id.back4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Blog();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

                Bundle bundle = new Bundle();
                bundle.putString("ValueOFPositionParentTask", ValueOfStartFragment);
                fragment.setArguments(bundle);
            }
        });

        Button contact = (Button) rootView.findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Profile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

                Bundle bundle = new Bundle();
                String valueOfReplace = idUser;
                bundle.putString("ForProfile", idUser);
                bundle.putString("ForProfileValue1", ValueForNewTaskAfterAdd);
                bundle.putString("ForProfileValue2", ValueOfStartFragment);
                fragment.setArguments(bundle);
            }
        });
        return rootView;
    }

    public void changeText() {

        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNameTask.setText(dataSnapshot.child("tasks").child(ValueOfStartFragment).child("tasksNew").child(ValueForNewTaskAfterAdd).child("nameOfTask").getValue(String.class));
                mDescrbingOfTask.setText(dataSnapshot.child("tasks").child(ValueOfStartFragment).child("tasksNew").child(ValueForNewTaskAfterAdd).child("describing").getValue(String.class));
                mCoordiinate.setText(dataSnapshot.child("tasks").child(ValueOfStartFragment).child("tasksNew").child(ValueForNewTaskAfterAdd).child("coordinate").getValue(String.class));
                mTime.setText(dataSnapshot.child("tasks").child(ValueOfStartFragment).child("tasksNew").child(ValueForNewTaskAfterAdd).child("time").getValue(String.class));
                idUser = dataSnapshot.child("tasks").child(ValueOfStartFragment).child("tasksNew").child(ValueForNewTaskAfterAdd).child("user").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error" + databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
