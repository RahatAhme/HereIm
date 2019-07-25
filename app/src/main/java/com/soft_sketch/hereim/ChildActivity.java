package com.soft_sketch.hereim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soft_sketch.hereim.POJO.ParentInfo;

public class ChildActivity extends AppCompatActivity {

    private FragmentManager manager;
    private FirebaseAuthOperation authOperation;
    private FirebaseDataBase db;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Bundle bundle;

    public String childID;
    public String phone;
    private String temp;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        authOperation = new FirebaseAuthOperation(this);
        db = new FirebaseDataBase(this);

        preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = preferences.edit();

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Child_Current_Loc child_current_loc = new Child_Current_Loc();
        ft.add(R.id.FragmentHolder_3_id, child_current_loc);
        ft.commit();

        BottomNavigationView bottomNav = findViewById(R.id.child_bottom_navigation_id);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                try {
                    bundle = new Bundle();

                    switch (menuItem.getItemId()) {

                        case R.id.nav_loc:
                            selectedFragment = new Child_Current_Loc();
                            menuItem.getIcon().setBounds(20, 20, 20, 20);
                            break;
                        case R.id.nav_calling:
                            selectedFragment = new ChildEmergency_Callig();
                            bundle.putString("idForCall", preferences.getString("parentID", "Error"));
                            selectedFragment.setArguments(bundle);
                            menuItem.getIcon().setBounds(20, 20, 20, 20);
                            break;
                        case R.id.nav_message:
                            selectedFragment = new Child_Message();
                            bundle.putString("idForMgs", preferences.getString("parentID", "Error"));
                            selectedFragment.setArguments(bundle);
                            menuItem.getIcon().setBounds(20, 20, 20, 20);
                            break;

                    }
                } catch (Exception ex) {
                    Toast.makeText(ChildActivity.this, ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

            String childName = preferences.getString("ChildName", "Error");
            String childPhone = preferences.getString("ChildPhone", "Error");
            temp = preferences.getString("ParentID", "Error");

            DatabaseReference parentID = FirebaseDatabase.getInstance().getReference(temp);
            childID = authOperation.GetToken();

            db.ChildSave(childID, childName, childPhone, parentID);

            editor.putString("parentID", temp);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.core_acitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logOut_id) {
            authOperation.LogOut();
            finish();
        }
        return true;
    }
}
