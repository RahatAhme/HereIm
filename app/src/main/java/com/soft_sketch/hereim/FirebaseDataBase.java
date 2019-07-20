package com.soft_sketch.hereim;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soft_sketch.hereim.POJO.ChildInfo;
import com.soft_sketch.hereim.POJO.ParentInfo;

public class FirebaseDataBase {

    private boolean dataSavingStatus= false;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = database.getReference();

    public boolean SaveParent(String parentID, String parentName, String parentNumber){

        ParentInfo parentInfo = new ParentInfo(parentID,parentName,parentNumber);

        DatabaseReference parentRef = rootRef.child(parentID);

        parentRef.setValue(parentInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataSavingStatus = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("DATA_ENTRY", e.getLocalizedMessage());
            }
        });
        return dataSavingStatus;

    }

    public boolean ChildSave(String childID, String childName, String childNumber, DatabaseReference parentID){
        boolean vibrationCode = false;
        boolean recodingCode = false;
        double currentLocLate = 0.0;
        double currentLocLong = 0.0;
        double fencingLate = 0.0;
        double fencingLong = 0.0;

        ChildInfo childInfo = new ChildInfo(childID,childName,childNumber,vibrationCode,recodingCode,currentLocLate,currentLocLong,fencingLate,fencingLong);
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
}
