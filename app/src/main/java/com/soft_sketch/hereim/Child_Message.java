package com.soft_sketch.hereim;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Child_Message extends Fragment {

    private Button parentMgsBtn, policeMgsBtn, parentpoliceMgsBtn;
    private final int SMS_CODE = 1;
    private Context context;

    private FirebaseDataBase dataBase;

    public Child_Message() {
        // Required empty public constructor
    }

    public Child_Message(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child__message, container, false);
        ((ChildActivity) getActivity()).setTitle("Message");


        dataBase = new FirebaseDataBase(getContext());

        parentMgsBtn = view.findViewById(R.id.mgsToPrent_id);
        policeMgsBtn = view.findViewById(R.id.mgsToPolice_id);
        parentpoliceMgsBtn = view.findViewById(R.id.mgsToBoth_id);

        parentMgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parentNumber = dataBase.GetParentPhone(((ChildActivity) getActivity()).childID);
                SmsManager smsManager = SmsManager.getDefault();
                Log.e("Number",parentNumber);

                    smsManager.sendTextMessage(parentNumber, null,
                            "I am in danger.Please help me at(Location).\n Here I'm",
                            null, null);
                Log.e("Mgs","I am in danger.Please help me at(Location).\n Here I'm");

            }
        });
        policeMgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        parentpoliceMgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }


}
