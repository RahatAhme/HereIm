package com.soft_sketch.hereim;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.soft_sketch.hereim.POJO.ChildInfo;
import com.soft_sketch.hereim.POJO.ParentInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseDataBase {

    private boolean dataSavingStatus = false;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = database.getReference();

    private List<String> childIDList = new ArrayList<>();
    private List<ChildInfo> childInfoList = new ArrayList<>();
    private ParentActivity parentActivity = new ParentActivity();
    private String phone;

    private Context context;

    public FirebaseDataBase(Context context) {
        this.context = context;
    }

    public boolean SaveParent(String parentID, String parentName, String parentNumber) {

        ParentInfo parentInfo = new ParentInfo(parentID, parentName, parentNumber);
        DatabaseReference parentRef = rootRef.child(parentID);
        parentRef.setValue(parentInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataSavingStatus = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e("DATA_ENTRY", Objects.requireNonNull(e.getLocalizedMessage()));
                }
            }
        });
        return dataSavingStatus;
    }

    public boolean ChildSave(String childID, String childName, String childNumber, DatabaseReference parentID) {
        boolean vibrationCode = false;
        boolean recodingCode = false;
        double currentLocLate = 0.0;
        double currentLocLong = 0.0;
        double fencingLate = 0.0;
        double fencingLong = 0.0;

        ChildInfo childInfo = new ChildInfo(childID, childName, childNumber, vibrationCode, recodingCode, currentLocLate, currentLocLong, fencingLate, fencingLong);
        DatabaseReference childRef = parentID.child(childID);
        childRef.setValue(childInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataSavingStatus = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dataSavingStatus = false;
            }
        });

        return dataSavingStatus;
    }


    public String GetParentNumber(String parentId) {

        rootRef.child(parentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phone = dataSnapshot.getValue(ParentInfo.class).getParentNumber();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return phone;
    }

    public void UpdateVibrate(String id) {

        DatabaseReference childSOSRef = rootRef.child(id).child("5257f44");
        Map<String, Object> sosupdate = new HashMap<>();
        sosupdate.put("vibrationCode", true);

        childSOSRef.updateChildren(sosupdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpdateLocation(String parentID, String chiildID, double lat, double Long) {

        DatabaseReference updateloc = rootRef.child(parentID).child(chiildID);
        Map<String, Object> locUpdate = new HashMap<>();
        locUpdate.put("currentLocLati", lat);
        locUpdate.put("currentLocLong", Long);

        updateloc.updateChildren(locUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<String> getChildIDList(final String parentID) {

        return childIDList;
    }


}
