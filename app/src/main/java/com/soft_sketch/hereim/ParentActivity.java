package com.soft_sketch.hereim;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.print.PrinterId;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ParentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private SharedPreferences preferences;
    private FirebaseAuthOperation authOperation;
    private FirebaseDataBase dataBase;

    private String parentID = null;

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
        dataBase = new FirebaseDataBase();

        //first fragment setup
        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ParentMap parentMap = new ParentMap();
        ft.add(R.id.FragmentHolder_2_id, parentMap);
        ft.commit();

        //bottomNav setup
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_id);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = new ParentMap();

                switch (menuItem.getItemId()) {
                    case R.id.nav_loc:
                        selectedFragment = new ParentMap();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_clock:
                        selectedFragment = new ParentTimeMonitoring();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_sos:
                        selectedFragment = new Parent_Sos();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                    case R.id.nav_sound:
                        selectedFragment = new AroundSound();
                        menuItem.getIcon().setBounds(20, 20, 20, 20);
                        break;
                }

                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.FragmentHolder_2_id,selectedFragment);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            }
        });

        if (getIntent().getStringExtra("data").equals("perform")) {

            preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            String parentName = preferences.getString("ParentName", "Error");
            String parentPhone = preferences.getString("ParentPhone", "Error");
            parentID = authOperation.GetToken();

            navUsername.setText(parentID);

            if (dataBase.SaveParent(parentID, parentName, parentPhone)) {
                Toast.makeText(this, "Your providing data has saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something is going wrong.\nPlease check you network setting.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "noting", Toast.LENGTH_SHORT).show();
        }
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

        if (id == R.id.nav_home) {
            // Handle the camera action
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

}
