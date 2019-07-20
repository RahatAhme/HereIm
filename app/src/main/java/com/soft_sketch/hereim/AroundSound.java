package com.soft_sketch.hereim;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AroundSound extends Fragment {


    public AroundSound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_around_sound, container, false);
        ((ParentActivity) getActivity())
                .setTitle("Sound Around Child");
        return view;
    }

}
