package com.swc.onestop.Activities.Complaints;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddComplaint extends AppCompatActivity {


    private SessionManager sessionManager;
    private HashMap<String,String> userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(AddComplaint.this,ComplaintActivity.class));
            }
        });
        toolbar.setTitle("New Complaint");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        sessionManager = new SessionManager(getApplicationContext());
        userDetails = sessionManager.getUserDetails();

//        final EditText sub = findViewById(R.id.subject_et);
//        final EditText body = findViewById(R.id.body_et);
        Button bt = findViewById(R.id.addComButton);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(sub.getText().toString().isEmpty())
//                    Toast.makeText(AddComplaint.this, "No Subject.", Toast.LENGTH_SHORT).show();
//                else if(body.getText().toString().isEmpty())
//                    Toast.makeText(AddComplaint.this, "No Message.", Toast.LENGTH_SHORT).show();
//                else {
                    registercomplain();
//                }
            }
        });
    }
    public void registercomplain(){
        Log.i("Shivam","enter kr gaya");
        final EditText complainInput = findViewById(R.id.subject_et);
        final EditText body = findViewById(R.id.body_et);
        complainInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        body.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        Log.i("Shivam","enterncdnd");
//        EditText complainInput  = (EditText) findViewById(R.id.complaintext);
        String complain = complainInput.getText().toString();
        String bodytext = body.getText().toString();
        Log.i("Shivam",complain);



        String name,hostel,userid;
        ArrayList <String> supports;
        int support;
        support=0;
        userid=userDetails.get("id");
        name=userDetails.get("name");
        hostel=userDetails.get("hostel");
        Log.i("Shivam",userid);
        Map<String,Object> newComplain = new HashMap<>();
        newComplain.put("user",hostel);
        Calendar cal = Calendar. getInstance();
        Date date=cal. getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM HH:mm");
        String formattedDate=dateFormat. format(date);
        newComplain.put("time_stamp",formattedDate  );
        newComplain.put("complain",complain);
        newComplain.put("body",bodytext);
        newComplain.put("support",support);
        newComplain.put("name",name);
        newComplain.put("supports",Arrays.asList(userDetails.get("rollNo")));


        Log.i("Shivam","ho gaya");
        FirebaseFirestore.getInstance().collection("Complaint").add(newComplain).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i("Shivam","Complaint added");
                startActivity(new Intent(AddComplaint.this,ComplaintActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Shivam","failed");
            }

        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
