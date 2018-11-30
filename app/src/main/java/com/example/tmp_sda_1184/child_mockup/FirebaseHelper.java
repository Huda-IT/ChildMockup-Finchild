package com.example.tmp_sda_1184.child_mockup;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {

    private List<ChildAccount> childNameList = new ArrayList<>();

    DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().child("child").child("2132324");

    public FirebaseHelper(DatabaseReference childAccRef){
        this.childAccRef = childAccRef;
    }

boolean saved;
    // Saving to database

    public Boolean saveBD (ChildName childName){
        if ( childName == null){
            boolean saved = false;
        }else
        {
            try{
                childAccRef.child("child").child("2132324").push().setValue(childName);
                boolean saved = true;
            } catch (DatabaseException e){
                e.printStackTrace();
                boolean saved = false;
            }

        }
        return  saved;
    }

    // Reading from Database
    public ArrayList<String> readDB (){
        final ArrayList<String> childname = new ArrayList<>();
        childAccRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    fetchData(dataSnapshot, childname);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    return null;}

    private void fetchData (DataSnapshot snapshot, ArrayList<String> childnamelist ){
        childnamelist.clear();
        for ( DataSnapshot snapshot1 : snapshot.getChildren()){
            String name = snapshot1.getValue(ChildName.class).getChildName();
            childnamelist.add(name);
        }
    }

}
