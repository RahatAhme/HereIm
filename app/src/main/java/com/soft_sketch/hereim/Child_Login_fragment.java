package com.soft_sketch.hereim;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class Child_Login_fragment extends Fragment {
    private EditText childNameET,childPhoneET,parentidET;
    private Button childLogBtn;

    private String childName,childPhone;
    private String parentID;

    private FirebaseAuthOperation authOperation;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public Child_Login_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_child__login_fragment, container, false);
        authOperation = new FirebaseAuthOperation(getContext());

        preferences = getContext().getSharedPreferences(getContext().getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(getContext().getString(R.string.PreferenceUserKey),"Child");
        editor.apply();

        childNameET = view.findViewById(R.id.child_name_ET_id);
        childPhoneET = view.findViewById(R.id.child_phone_ET_id);
        parentidET = view.findViewById(R.id.parentUserID_ET_id);
        childLogBtn = view.findViewById(R.id.child_log_btn_id);

        childLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childName = childNameET.getText().toString();
                childPhone = childPhoneET.getText().toString();
                parentID =  parentidET.getText().toString();
                ChildVarification(childName,childPhone,parentID);
                Toast.makeText(getContext(), "Your data is in verification", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void ChildVarification(String childName, String childPhone, String parentID) {

       if (childName.isEmpty()){
           childNameET.setError("Provide Appropriate Name");
        }else if (childPhone.length()<11){
            childPhoneET.setError("Provide Appropriate Number");
        }else if (parentID.length()<7){
           parentidET.setError("Provide Your Parent Userid");
       }else {
           authOperation.HoldChild(childName,childPhone,parentID);
       }

    }

}
