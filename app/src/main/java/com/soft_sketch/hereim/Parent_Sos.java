package com.soft_sketch.hereim;


import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Parent_Sos extends Fragment {

    private Button sosBtn;

    private FirebaseDataBase dataBase;

    private String idatSOS;

    public Parent_Sos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parent__sos, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((ParentActivity) Objects.requireNonNull(getActivity())).setTitle("SOS");
        }

        dataBase = new FirebaseDataBase(getContext());

       idatSOS = getArguments().getString("parentIDpasser");
        Toast.makeText(getContext(), "id", Toast.LENGTH_SHORT).show();

        sosBtn = view.findViewById(R.id.sosBtn_id);
        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBase.UpdateVibrate(idatSOS);
            }
        });
        return view;
    }

}
