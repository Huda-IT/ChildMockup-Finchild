package com.example.tmp_sda_1184.child_mockup;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseHelpClass firebaseHelper = new FirebaseHelpClass();
    private List<ChildAccount> childValues = new ArrayList<>();
    List<String> name_List = new ArrayList<>();
    List<String> childName_List = new ArrayList<>();
    List<String> parentNo_list = new ArrayList<>();
    ArrayList<Item> items_list = new ArrayList<>();

    HashMap<String, ChildInfo> childHashmap = new HashMap<String, ChildInfo>();

    ChildAccount childAccount;
    ChildInfo childInfo;

    String choice1, choice2, choice3, key;
    int Qchoice1, Qchoice2, Qchoice3;
    double balance, totalAmount, currentbalance;
    String choice, accountNo, chidName, market_choice, name, parentAccNo, childAccNoID;


    private Button confirmBtn;
    private EditText purchaseDate;
    private EditText totalPrice;
    private EditText itemPrice1;
    private EditText itemPrice2;
    private EditText itemPrice3;
    private EditText messageText;
    private Button sendMessage;
    // final Session session=new Session(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialising child spinner names
        Values();
        //initialiseChildSpinner();
        //Values();
        //getChildInfo(childHashmap);


        // Save purchase info to firebase
        confirmBtn = (Button) findViewById(R.id.purchase_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childPurchase();
            }
        });
        marketSpinner();
        itemsSpinner1();
        itemsSpinner2();
        itemsSpinner3();
        QtySpinner1();
        QtySpinner2();
        QtySpinner3();
    }


    // Method for connecting child spinner to read from the firebase database
    public void initialiseChildSpinner() {
        final DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().child("child").child("2132324");
        childAccRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childValues.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    childAccount = dataSnapshot1.getValue(ChildAccount.class);
                    childValues.add(childAccount);
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
                        System.out.println(chidName);

                        //balance = childAccount.getBalance();
                        //accountNo = childAccount.getAccountNo();
                        //System.out.println("BALANCE : " + balance + " ACCOUNTNO : " + accountNo);
                       /* if (chidName.equals("Siqi's child")) accountNo = "098832998382";
                         else if (chidName.equals("Siqi's child2")) accountNo = "32343203092";
                        else if (chidName.equals("Siqi's Child3")) accountNo = "0765833996";
                        else if (chidName.equals("Siqi's Child4")) accountNo = "45474474";
                        System.out.println(" Child Account no " + accountNo);*/
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

    // Retriving data from firebase
    public void Values() {
        // Retreving parents Account
        DatabaseReference parentAccRef = FirebaseDatabase.getInstance().getReference().child("account");
        parentAccRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot parentAccSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot parentAccNoSnapshot : parentAccSnapshot.getChildren()) {
                        String parentAccNo = parentAccNoSnapshot.getKey();
                        //System.out.println("Parent Account " +parentAccNo);
                        parentNo_list.add(parentAccNo);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //Retreving children for each parent
        DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().child("child");
        childAccRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childValues.clear();
                for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                    String parentAccNo = childsnap.getKey();
                    System.out.println("Parent Account " + parentAccNo);

                    for (DataSnapshot childsnap2 : childsnap.getChildren()) {
                        childAccNoID = childsnap2.getKey();
                        System.out.println("child account ID " + childAccNoID);
                        System.out.println("child Balance ::: " + childsnap2.child("balance").getValue());
                        System.out.println("child Name ::::: " + childsnap2.child("name").getValue());
                        name = (String) childsnap2.child("name").getValue();
                        childName_List.add(name);

                        balance = Double.valueOf((childsnap2.child("balance").getValue().toString()));
                        accountNo = String.valueOf(childsnap2.child("accountNo").getValue());
                        ChildInfo childInfo = new ChildInfo(accountNo, balance, parentAccNo, childAccNoID);

                        //saving child name and info into a hashmap
                        childHashmap.put(name, childInfo);


                        final Spinner child_spinner = (Spinner) findViewById(R.id.childID_spinner);
                        ArrayAdapter<String> child_Adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, childName_List);
                        //child_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name_List));
                        child_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        child_spinner.setAdapter(child_Adapter);
                        child_Adapter.notifyDataSetChanged();

                        // Selecting a child name from the spinner so he can make a purchase
                        child_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                chidName = child_spinner.getSelectedItem().toString();
                                System.out.println(chidName);

                                // Retreving Child accountNo and Balance
                                getChildInfo(chidName);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public ChildInfo getChildInfo(String chidName) {

        System.out.println(" Selected child is ::::" + chidName);
        ChildInfo newInfo2 = childHashmap.get(chidName);
        accountNo = String.valueOf(newInfo2.getChildAccNo());
        currentbalance = newInfo2.getBalance();
        parentAccNo = newInfo2.getParentAccNo();
        childAccNoID = newInfo2.getChildAccNoID();

        System.out.println("array infoooo" + accountNo + " ---currentbalance----" + currentbalance + "parentAccNo ::" + parentAccNo + "childAccNoID ::" +childAccNoID);
        return newInfo2;
    }

    // method to make the purchase and save them to firebase
    public void childPurchase() {
        final String childAccountNo = accountNo;
        System.out.println(" -------------------------------");
        System.out.println("childAccountNo in the purchase process" + accountNo);
        final DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().
                child("expenses").child(childAccountNo);

        childAccRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Getting the purchase data from the user input
                purchaseDate = (EditText) findViewById(R.id.purchase_date);
                totalPrice = (EditText) findViewById(R.id.total_price);
                itemPrice1 = (EditText) findViewById(R.id.item_price1);
                itemPrice2 = (EditText) findViewById(R.id.item_price2);
                itemPrice3 = (EditText) findViewById(R.id.item_price3);

                String purchase_date = purchaseDate.getText().toString().trim();
                double item_price1 = Double.parseDouble(itemPrice1.getText().toString().trim());
                double item_price2 = Double.parseDouble(itemPrice2.getText().toString().trim());
                double item_price3 = Double.parseDouble(itemPrice3.getText().toString().trim());


                // Calculating the total price for the purchase
                totalAmount = (Qchoice1 * item_price1) + (Qchoice2 * item_price2) + (Qchoice3 * item_price3);
                totalPrice.setText(String.valueOf(totalAmount));

                System.out.println(" total price =" + totalAmount);

                items_list.add(new Item(choice1, Qchoice1, item_price1, "Fruit"));
                items_list.add(new Item(choice2, Qchoice2, item_price2, "Soft Drink"));
                items_list.add(new Item(choice3, Qchoice3, item_price3, "Healthy Drink"));

                Expenses purchase_list = new Expenses(key, market_choice, purchase_date, totalAmount, items_list);

                // Pushing all purchase details into the firebase

                childAccRef.push().setValue(purchase_list);
                BalanceUpdate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public double BalanceUpdate() {
        System.out.println("Updating balance +++++++");
        System.out.println("Child Account No :  " + accountNo);
        System.out.println("current balance :  " + currentbalance);
        System.out.println("Parent Account " + parentAccNo);
        System.out.println("child account ID " + childAccNoID);
        balance = currentbalance -totalAmount;
        System.out.println("new balance :  " + balance);

        final DatabaseReference childAccRef = FirebaseDatabase.getInstance().getReference().child("child").child(parentAccNo).child(childAccNoID);
        childAccRef.child("balance").setValue(balance);

        return balance;

    }

    // Creating Market Spinner
    public String marketSpinner() {
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
    public String itemsSpinner1() {
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
    public String itemsSpinner2() {
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
    public String itemsSpinner3() {
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
    public int QtySpinner1() {
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
    public int QtySpinner2() {
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
    public int QtySpinner3() {
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