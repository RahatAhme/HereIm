package com.soft_sketch.hereim;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.soft_sketch.hereim.POJO.InternetConnectionChecking;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Child_Current_Loc extends Fragment {

    private String childCurrentLoc = "";
    private static final int REQUEST_CODE = 111;

    private FusedLocationProviderClient client;
    private Geocoder geocoder;
    private List<Address> addresses;
    private LocationRequest locationRequest;

    public Child_Current_Loc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child__current__loc, container, false);
        ((ChildActivity) getActivity()).setTitle("Child Current Location");

        client = new FusedLocationProviderClient(getContext());
        geocoder = new Geocoder(getContext());

        if (CheckPermission()) {
            getCurrentLocation();
        }

        return view;
    }

    private boolean CheckPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return false;
        }
        return true;
    }

    public String getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double currentlet = location.getLatitude();
                        double currentlog = location.getLongitude();
                        LatLng sydney = new LatLng(-currentlet, currentlog);
                        childCurrentLoc = getAddressName(currentlet, currentlog);
                        Toast.makeText(getContext(), childCurrentLoc, Toast.LENGTH_SHORT).show();

                    } else {
                        gotoLocSetting();
                        return;
                    }
                }
            });
        }
        return childCurrentLoc;
    }

    public String getAddressName(double currentlet, double currentlog) {
        String location = "";
        try {
            addresses = geocoder.getFromLocation(currentlet, currentlog, 1);
            Address cueentAddress = addresses.get(0);
            location = cueentAddress.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    public void gotoLocSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        String message = "Do you want open GPS setting?";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

}
