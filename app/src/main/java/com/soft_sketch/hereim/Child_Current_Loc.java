package com.soft_sketch.hereim;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Child_Current_Loc extends Fragment {


    public Child_Current_Loc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child__current__loc, container, false);

        ((ChildActivity) getActivity())
                .setTitle("Child Current Location");
        return view;
    }

}
