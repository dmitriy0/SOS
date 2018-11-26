package com.example.searchandrescue;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.searchandrescue.R;


public class Map extends Fragment {
    private String coords;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        Button sendOnCloud = (Button) rootView.findViewById(R.id.button);
        sendOnCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coords = ((EditText) getActivity().findViewById(R.id.textCoordinates)).getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+coords));
                startActivity(intent);
            }
        });

        return rootView;


    }

}