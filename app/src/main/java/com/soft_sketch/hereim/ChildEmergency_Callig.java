package com.soft_sketch.hereim;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildEmergency_Callig extends Fragment {
    private FirebaseAuthOperation authOperation;
    private FirebaseDataBase dataBase;

    private SharedPreferences preferences;

    public ChildEmergency_Callig() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_child_emergency__callig, container, false);

        authOperation = new FirebaseAuthOperation(getContext());
        dataBase = new FirebaseDataBase();

        preferences = getContext().getSharedPreferences(getContext().getString(R.string.app_name), Context.MODE_PRIVATE);
        String childName = preferences.getString("ChildName", "Error");
        String childPhone = preferences.getString("ChildPhone", "Error");
        String temp = preferences.getString("ParentID","Error");
        String childID = authOperation.GetToken();
        DatabaseReference parentID = FirebaseDatabase.getInstance().getReference(temp);

        if(dataBase.ChildSave(childID,childName,childPhone,parentID)){
            Toast.makeText(getContext(), "Your data is save successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Don't save data", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

}
