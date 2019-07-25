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
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;


public class Child_Message extends Fragment {

    private Button parentMgsBtn, policeMgsBtn, parentpoliceMgsBtn;
    private Context context;
    private FirebaseDataBase dataBase;

    private String id = "";

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

        id = getArguments().getString("idForMgs");

        parentMgsBtn = view.findViewById(R.id.mgsToPrent_id);
        policeMgsBtn = view.findViewById(R.id.mgsToPolice_id);
        parentpoliceMgsBtn = view.findViewById(R.id.mgsToBoth_id);

        parentMgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(dataBase.GetParentNumber(id), null, "this is the mgs", null, null);
                    Toast.makeText(getContext(), "Your Message Sent",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        policeMgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "This part will be completed after collecting map Api. ", Toast.LENGTH_SHORT).show();
            }
        });
        parentpoliceMgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "This part will be completed after collecting map Api. ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
