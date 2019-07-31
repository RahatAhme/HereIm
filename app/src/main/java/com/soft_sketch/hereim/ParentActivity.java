package com.soft_sketch.hereim;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.print.PrinterId;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soft_sketch.hereim.POJO.ChildInfo;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ParentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private FirebaseAuthOperation authOperation;
    private FirebaseDataBase dataBase;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = database.getReference();

    private Bundle bundle;

    public String parentID = "";
    public String childID = "";
    private List<String> childIDlist;
    private List<ChildInfo> childList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.ParentUID);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        authOperation = new FirebaseAuthOperation(this);
        bundle = new Bundle();
        childIDlist = new ArrayList<>();
        childList = new ArrayList<>();
        dataBase = new FirebaseDataBase(this);

        preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = preferences.edit();

        String temp1 = getIntent().getStringExtra("data");

        if (temp1 != null && temp1.equals("perform")) {
            String parentName = preferences.getString("ParentName", "Error");
            String parentPhone = preferences.getString("ParentPhone", "Error");
            parentID = authOperation.GetToken() + "";

            navUsername.setText(parentID);

            if (dataBase.SaveParent(parentID, parentName, parentPhone)) {
                Toast.makeText(this, "Your providing data has saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something is going wrong.\nPlease check you network setting.", Toast.LENGTH_SHORT).show();
            }
            editor.putString("parentID",parentID);
            editor.apply();
        }

        //first fragment setup
        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        bundle.putString("parentIDpasser",preferences.getString("parentID","Error"));
        ParentMap parentMap = new ParentMap();
        parentMap.setArguments(bundle);
        ft.add(R.id.FragmentHolder_2_id, parentMap);
        ft.commit();

        rootRef.child(preferences.getString("parentID","Error")).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childIDlist.clear();
              if (dataSnapshot.exists()){
                  for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                      String id = snapshot.getKey();
                      if (!id.equals("parentID") && !id.equals("parentName") && !id.equals("parentNumber")) {
                          childIDlist.add(id);
                      }
                  }
                  if (!childIDlist.isEmpty()){
                      childID = childIDlist.get(0);
                  }else {
                      ShowNoChild();
                  }
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ParentActivity.this, "Network problem!", Toast.LENGTH_SHORT).show();
            }
        });

        //bottomNav setup
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_id);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = new ParentMap();

                switch (menuItem.getItemId()) {

                    case R.id.nav_loc:
                        selectedFragment = new ParentMap();
                        bundle.putString("parentIDpasser",preferences.getString("parentID","Error"));
                        selectedFragment.setArguments(bundle);
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_clock:
                        selectedFragment = new ParentTimeMonitoring();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_sos:
                        bundle.putString("parentIDpasser",preferences.getString("parentID","Error"));
                        selectedFragment.setArguments(bundle);
                        selectedFragment = new Parent_Sos();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_sound:
                        selectedFragment = new AroundSound();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                }

                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.FragmentHolder_2_id, selectedFragment);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.core_acitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut_id) {
            authOperation.LogOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_switchChild_id) {
            showRadioButtonDialog();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void showRadioButtonDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.child_switch_dialogue);
        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.childlist_radio_id);

        for (int i = 0;i<childIDlist.size();i++){
            int p = i +1;
            RadioButton rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText("Child: " +p);
            rg.addView(rb);
        }
        dialog.show();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                childID = childIDlist.get(checkedId);
            }
        });
    }

    public void ShowNoChild(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_child_dialogue);

        String title = R.string.nochildtitle+preferences.getString("parentID","error");
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        TextView diologTV = dialog.findViewById(R.id.text_dialog);
        diologTV.setText(title);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }


}
