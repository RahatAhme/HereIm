package com.soft_sketch.hereim;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.soft_sketch.hereim.POJO.ChildInfo;
import com.soft_sketch.hereim.POJO.ParentInfo;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataBase {

    private boolean dataSavingStatus = false;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = database.getReference();

    private List<ChildInfo> childList = new ArrayList<>();
    private List<String> childIDList = new ArrayList<>();

    private String parentNumber;

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
                Log.e("DATA_ENTRY", e.getLocalizedMessage());
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

    public List<String> GetChildID(final String parentID) {
        DatabaseReference parent = rootRef.child(parentID);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String temp = ds.getKey();
                    if (!temp.equals("parentID") && !temp.equals("parentName") && !temp.equals("parentNumber")) {
                        Log.e("child", temp);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        parent.addListenerForSingleValueEvent(valueEventListener);
        return childIDList;
    }

    public String GetParentPhone(String childID) {

        rootRef.orderByChild("childID").equalTo(childID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String keys = childSnapshot.getKey();
                    String temp = childSnapshot.getValue(ParentInfo.class).getParentNumber();
                    if (temp != null) {
                        parentNumber = temp;
                    } else {
                        parentNumber = "empty";
                    }
                    Log.e("number",parentNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Somethis is going wrong.\nPlease check your network.", Toast.LENGTH_SHORT).show();
            }
        });
        return parentNumber;

    }
}
