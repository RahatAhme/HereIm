package com.soft_sketch.hereim;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildEmergency_Callig extends Fragment {
    private Button parentCallBtn, policeCallBtn, helplineCallBtn;

    private FirebaseDataBase dataBase;

    private  String id = "";

    public ChildEmergency_Callig() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_emergency__callig, container, false);

        id = getArguments().getString("idForCall");

        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((ChildActivity) Objects.requireNonNull(getActivity())).setTitle("Emergency Calling");
        }

        dataBase = new FirebaseDataBase(getContext());

        parentCallBtn = view.findViewById(R.id.callToParent_id);
        policeCallBtn = view.findViewById(R.id.callToPolice_id);
        helplineCallBtn = view.findViewById(R.id.callToHelpLine_id);

        parentCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dataBase.GetParentNumber(id)));
                startActivity(intent);
            }
        });

        policeCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Missing Map Api.\nSo system can detect nearest police station.", Toast.LENGTH_SHORT).show();
            }
        });

        helplineCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 888));
                startActivity(intent);
            }
        });
        return view;
    }


}
