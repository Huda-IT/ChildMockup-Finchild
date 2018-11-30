package com.example.tmp_sda_1184.child_mockup;

import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelpClass {

    private List<ChildAccount> childNameList = new ArrayList<>();
    List<String>  name_List = new ArrayList<>();
    private Button confirmBtn;



    // Method for connecting child spinner to read from the firebase database
    public void initialiseChildSpinner() {
     /*  final DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().child("child").child("2132324");
        childAccRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childNameList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ChildAccount childAccount = dataSnapshot1.getValue(ChildAccount.class);
                    childNameList.add(childAccount);
                    name_List.add(childAccount.getName());
                }

                /*Spinner child_spinner = (Spinner) findViewById(R.id.childID_spinner);
                ArrayAdapter<String> child_Adapter = new ArrayAdapter<String>(FirebaseHelpClass.this, android.R.layout.simple_list_item_1, name_List);
                child_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                child_spinner.setAdapter(child_Adapter);
                child_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String searchAccount(){
        String accountNo = "hjh";
        return accountNo;
    }

    // method to make the purchase and save them to firebase
    public void childPurchase(){
        String accountNo =searchAccount();
        final DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().
                child("expenses").child(accountNo);
        childAccRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
          }
          */

    }}


