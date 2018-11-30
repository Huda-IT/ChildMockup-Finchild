package com.example.tmp_sda_1184.child_mockup;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseHelpClass firebaseHelper = new FirebaseHelpClass();
    private List<ChildAccount> childNameList = new ArrayList<>();
    List<String> name_List = new ArrayList<>();

    ArrayList<Expenses>  total_purchases = new ArrayList<>();
    ArrayList<Item> items_list = new ArrayList<>();
    String choice1, choice2, choice3, key;
    int Qchoice1, Qchoice2, Qchoice3;
    String choice, item_choice, accountNo, chidName ,market_choice;
    List<String> item_choice1 = new ArrayList<>();

    private Button confirmBtn;
    private EditText purchaseDate;
    private EditText totalPrice;
    private EditText itemPrice1;
    private EditText itemPrice2;
    private EditText itemPrice3;
    private EditText messageText;
    private Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialising child spinner names
        initialiseChildSpinner();


        // Save purchase info to firebase
        confirmBtn = (Button) findViewById(R.id.purchase_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childPurchase();
            }
        });
        marketSpinner();
        itemsSpinner1(); itemsSpinner2(); itemsSpinner3();
        QtySpinner1();  QtySpinner2();  QtySpinner3();
    }


    // Method for connecting child spinner to read from the firebase database
    public void initialiseChildSpinner() {
        final DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().child("child").child("2132324");
        childAccRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childNameList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ChildAccount childAccount = dataSnapshot1.getValue(ChildAccount.class);
                    childNameList.add(childAccount);
                    name_List.add(childAccount.getName());
                }

                final Spinner child_spinner = (Spinner) findViewById(R.id.childID_spinner);
                ArrayAdapter<String> child_Adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, name_List);
                //child_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name_List));
                child_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                child_spinner.setAdapter(child_Adapter);
                child_Adapter.notifyDataSetChanged();

                // Selecting a child name from the spinner so he can make a purchase
                child_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        chidName = child_spinner.getSelectedItem().toString();
                        System.out.println (chidName);
                        if (chidName.equals("Siqi's child"))   accountNo = "098832998382";
                        else if (chidName.equals("Siqi's child2"))    accountNo = "32343203092";
                        else if (chidName.equals("Siqi's Child3"))    accountNo = "0765833996";
                        else if (chidName.equals("Siqi's Child4"))    accountNo = "45474474";
                        System.out.print(accountNo);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // method to make the purchase and save them to firebase
    public void childPurchase() {
        final String childAccountNo = accountNo;
        System.out.println( "childAccountNo" + accountNo);
        final DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().
                child("expenses").child(childAccountNo);

        childAccRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // getting the Expenses ID for this purchase from firebase to push it with the it's purchase
                // 1 -  push the empty expenses to the datebase to create a new stack for the new purchase
                //2- get the Expanse ID to add to the arraylist for purchase to make the push for the items with it's ID
             /*   childAccRef.child("expenses").child(accountNo).push();
                DatabaseReference expenseRef = childAccRef.child("expenses");
                expenseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                             key = dataSnapshot1.getKey();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }); */

                // Getting the purchase data from the user input
                purchaseDate = (EditText) findViewById(R.id.purchase_date);
                totalPrice = (EditText) findViewById(R.id.total_price);
                itemPrice1 = (EditText) findViewById(R.id.item_price1);
                itemPrice2 = (EditText) findViewById(R.id.item_price2);
                itemPrice3 = (EditText) findViewById(R.id.item_price3);

                String purchase_date =purchaseDate.getText().toString().trim();
                double item_price1 = Double.parseDouble(itemPrice1.getText().toString().trim());
                double item_price2 = Double.parseDouble(itemPrice2.getText().toString().trim());
                double item_price3 = Double.parseDouble(itemPrice3.getText().toString().trim());


                // Calculating the total price for the purchase
                double totalAmount = (Qchoice1*item_price1) + (Qchoice2*item_price2) + (Qchoice3*item_price3);
                totalPrice.setText(String.valueOf(totalAmount));

                System.out.println( " total price =" + totalAmount);

                items_list.add(new Item ( choice1, Qchoice1,item_price1,"Fruit" ));
                items_list.add(new Item (choice2, Qchoice2 ,item_price2,"Soft Drink" ));
                items_list.add(new Item (choice3,Qchoice3,item_price3,"Healthy Drink"));

                Expenses purchase_list = new Expenses(key ,market_choice,purchase_date, totalAmount, items_list);
                //total_purchases.add(purchase_list);

                // Pushing all purchase details into the firebase

                childAccRef.push().setValue(purchase_list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // Creating Market Spinner
    public String marketSpinner (){
        final Spinner spinner = (Spinner) findViewById(R.id.market_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Market_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                market_choice = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return market_choice;
    }

    // Creating Items Spinner
    public String itemsSpinner1(){
        final Spinner spinner = (Spinner) findViewById(R.id.item_spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Item_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice1 = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return choice1;
    }

    // Creating Items Spinner
    public String itemsSpinner2(){
        final Spinner spinner = (Spinner) findViewById(R.id.item_spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Item_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice2 = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return choice2;
    }

    // Creating Items Spinner
    public String itemsSpinner3(){
        final Spinner spinner = (Spinner) findViewById(R.id.item_spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Item_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice3 = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return choice3;
    }

    // Creating QTY Spinner
    public int QtySpinner1(){
        final Spinner spinner = (Spinner) findViewById(R.id.qty_spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.QTY_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Qchoice1 = Integer.parseInt(spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return Qchoice1;
    }


    // Creating QTY Spinner
    public int QtySpinner2(){
        final Spinner spinner = (Spinner) findViewById(R.id.qty_spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.QTY_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Qchoice2 = Integer.parseInt(spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return Qchoice2;
    }


    // Creating QTY Spinner
    public int QtySpinner3(){
        final Spinner spinner = (Spinner) findViewById(R.id.qty_spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.QTY_list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Qchoice3 = Integer.parseInt(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return Qchoice3;
    }
       /*
        String message = messageText.getText().toString().trim();
         messageText =(EditText) findViewById(R.id.mssage);
 */



}
