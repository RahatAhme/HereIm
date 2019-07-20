package com.soft_sketch.hereim;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Police_login_Fragment extends Fragment {

    private EditText stationNameET,stationPhoneET;
    private Button stationLoginBtn;

    private String stationName,stationPhone;

    public Police_login_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_police_login_, container, false);
        stationNameET = view.findViewById(R.id.station_name_ET_id);
        stationPhoneET = view.findViewById(R.id.station_phone_ET_id);
        stationLoginBtn = view.findViewById(R.id.station_log_btn_id);
        stationLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationName = stationNameET.getText().toString();
                stationPhone = stationPhoneET.getText().toString();

                if (StationVarification(stationName,stationPhone)){

                }
            }
        });
        return view;
    }

    private boolean StationVarification(String stationName, String stationPhone) {
        boolean status=true;
        if (stationName.length()==0 || stationPhone.length()<11){
            stationNameET.setError("Provide appropriate Data");
            stationPhoneET.setError("Provide Appropriate Data");
            status = false;
        }else{

        }
        return status;
    }

}
