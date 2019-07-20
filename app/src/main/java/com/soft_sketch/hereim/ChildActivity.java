package com.soft_sketch.hereim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ChildActivity extends AppCompatActivity {

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ChildEmergency_Callig childCalling = new ChildEmergency_Callig();
        ft.add(R.id.FragmentHolder_3_id, childCalling);
        ft.commit();
    }
}
