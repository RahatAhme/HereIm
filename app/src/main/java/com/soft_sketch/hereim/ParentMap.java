package com.soft_sketch.hereim;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParentMap extends Fragment {

    private FirebaseDataBase dataBase;
    private FirebaseAuthOperation authOperation;
    private List<String> idlit;

    public ParentMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent_map, container, false);
        ((ParentActivity) getActivity()).setTitle("Child Current Location");

        TextView tv = view.findViewById(R.id.tempID);
        idlit = new ArrayList<>();
        dataBase = new FirebaseDataBase(getContext());
        idlit = dataBase.GetChildID(((ParentActivity) getActivity()).parentID);

        for (int i = 0;i<idlit.size();i++){
            tv.setText(idlit.get(i));
        }
        return view;
    }

}
