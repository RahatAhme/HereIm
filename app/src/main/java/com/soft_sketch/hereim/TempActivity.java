package com.soft_sketch.hereim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.soft_sketch.hereim.POJO.InternetConnectionChecking;

import java.io.IOException;
import java.util.List;

public class TempActivity extends AppCompatActivity {
    private Button lastBtn,updateBTn;
    private TextView lastLocTV,updatelocTV;
    private static final int REQUEST_CODE = 111;

    private FusedLocationProviderClient client;
    private Geocoder geocoder;
    private List<Address> addresses;
    private LocationRequest locationRequest;
    private InternetConnectionChecking checking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        client = new FusedLocationProviderClient(this);
        geocoder = new Geocoder(this);
        checking = new InternetConnectionChecking();

        lastLocTV = findViewById(R.id.lastlocTVID);
        lastBtn = findViewById(R.id.lastlocID);
        updateBTn = findViewById(R.id.updatelocID);
        updatelocTV = findViewById(R.id.updatelocTVID);

        lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckPermission()) {
                    getCurrentLocation();
                }
            }
        });


        updateBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckPermission()) {
                    getUpdateLocation();
                }
            }
        });
    }

    private boolean CheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double currentlet = location.getLatitude();
                    double currentlog = location.getLongitude();
                    LatLng sydney = new LatLng(-currentlet, currentlog);
                    String address = getAddressName(currentlet, currentlog);
                    lastLocTV.setText(address);

                } else {
                    gotoLocSetting();
                    return;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        } else {
            Toast.makeText(this, "It is important to access your location", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUpdateLocation() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            CheckPermission();
        }else {
            client.requestLocationUpdates(getLocationRequest(),callback,null);
        }
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
    private LocationRequest getLocationRequest(){
        locationRequest= new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(2000);
        return locationRequest;
    }
    public void gotoLocSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TempActivity.this);
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

    private LocationCallback callback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()){
                if(location==null){
                    Toast.makeText(TempActivity.this, "Please turn on your device location", Toast.LENGTH_SHORT).show();

                    gotoLocSetting();
                    return;
                }
                double updateLet = location.getLatitude();
                double updateLong = location.getLongitude();
                String updateLocation = getAddressName(updateLet,updateLong);
                updatelocTV.setText(updateLocation);
            }
        }
    };
}
