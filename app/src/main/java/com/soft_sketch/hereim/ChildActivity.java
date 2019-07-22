package com.soft_sketch.hereim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChildActivity extends AppCompatActivity {

    private FragmentManager manager;
    private FirebaseAuthOperation authOperation;
    private FirebaseDataBase dataBase;
    private SharedPreferences preferences;

    public String childID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Child_Current_Loc child_current_loc = new Child_Current_Loc();
        ft.add(R.id.FragmentHolder_3_id, child_current_loc);
        ft.commit();

        BottomNavigationView bottomNav = findViewById(R.id.child_bottom_navigation_id);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = new ChildEmergency_Callig();

                switch (menuItem.getItemId()) {
                    case R.id.nav_loc:
                        selectedFragment = new Child_Current_Loc();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_calling:
                        selectedFragment = new ChildEmergency_Callig();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_message:
                        selectedFragment = new Child_Message();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;

                }

                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.FragmentHolder_3_id, selectedFragment);
                ft.addToBackStack(null);
                ft.commit();

                return true;
            }
        });

        String temp1 = getIntent().getStringExtra("data");

        if (temp1 != null && temp1.equals("perform")) {
            authOperation = new FirebaseAuthOperation(this);
            dataBase = new FirebaseDataBase(this);

            preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            String childName = preferences.getString("ChildName", "Error");
            String childPhone = preferences.getString("ChildPhone", "Error");
            String temp = preferences.getString("ParentID", "Error");
            childID = authOperation.GetToken();
            DatabaseReference parentID = FirebaseDatabase.getInstance().getReference(temp);

            if (dataBase.ChildSave(childID, childName, childPhone, parentID)) {
                Toast.makeText(this, "Your data is save successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Don't save data", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
