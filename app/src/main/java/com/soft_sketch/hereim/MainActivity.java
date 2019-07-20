package com.soft_sketch.hereim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements WellComeScreen.SplashScreenIntf, User_Selection_fragment.UserSelectionIntf {

    private FragmentManager manager;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private FirebaseAuthOperation authOperation;

    private String user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authOperation = new FirebaseAuthOperation(this);

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        WellComeScreen wellComeScreen = new WellComeScreen();
        ft.add(R.id.FragmentHolder_1_id, wellComeScreen);
        ft.commit();

    }

    @Override
    public void OnTimeOut() {
        User_Selection_fragment userSelectionFragment = new User_Selection_fragment();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.FragmentHolder_1_id, userSelectionFragment);
        ft.commit();
    }

    @Override
    public void ParentLogedIn() {
        Intent intent = new Intent(this,ParentActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void ChildLogedIn() {
        Intent intent = new Intent(this,ChildActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void OnCompleteUserSelection(int position) {
        FragmentTransaction ft = manager.beginTransaction();
        if (position == 1) {
            Police_login_Fragment policeLogINFragment = new Police_login_Fragment();
            ft.replace(R.id.FragmentHolder_1_id, policeLogINFragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (position == 2) {
            Parent_Login_fragment parentLogINFragment = new Parent_Login_fragment();
            ft.replace(R.id.FragmentHolder_1_id, parentLogINFragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (position == 3) {
            Child_Login_fragment childLogINFragment = new Child_Login_fragment();
            ft.replace(R.id.FragmentHolder_1_id, childLogINFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }


}
