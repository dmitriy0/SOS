package com.example.searchandrescue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.junior.stronger197.sos.AddNewTask;
import com.junior.stronger197.sos.NewTaskAfterAdd;

import java.util.List;

public class Blog extends Fragment {

    private String conterOfFragment = "0";
    private DatabaseReference mRef;
    private List<String> mTasks;

    private ListView listTasks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_blog, container, false);

        listTasks = (ListView) rootView.findViewById(R.id.discr_for_task_my);
        Bundle bundle = getArguments();
        if(bundle != null){
            conterOfFragment = bundle.getString("ValueOFPositionParentTask", "0");
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                mTasks = dataSnapshot.child("tasks").child(conterOfFragment).child("someTask").getValue(t);
                updateUINew();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error cod" + databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        Button back = (Button) rootView.findViewById(R.id.back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Task();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

                Bundle bundle = new Bundle();
                String valueOfReplace = conterOfFragment;
                bundle.putString("Value", valueOfReplace);
                fragment.setArguments(bundle);
            }
        });

        Button addNewTask = (Button) rootView.findViewById(R.id.button3);// добавить новую задачу
        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddNewTask();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

                Bundle bundle = new Bundle();
                String valueOfReplace = conterOfFragment;
                bundle.putString("ValueToNewTask", valueOfReplace);
                fragment.setArguments(bundle);
            }
        });

        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // получаем текст нажатого элемента
                //Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                Fragment fragment = new NewTaskAfterAdd();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                Bundle bundle = new Bundle();
                String valueOfReplace = position+"";
                bundle.putString("ValueForNewTaskAfterAdd", valueOfReplace);
                bundle.putString("ValueOfStartFragment", conterOfFragment);
                fragment.setArguments(bundle);
            }
        });
        return rootView;
    }

    public void updateUINew(){
        if(getActivity() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_text_view, mTasks);
            listTasks.setAdapter(adapter);
        }
    }

}
