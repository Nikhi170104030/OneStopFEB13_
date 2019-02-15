package com.swc.onestop.Activities.Complaints;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintActivity extends AppCompatActivity {

    private static final String TAG = "ComplaintActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ComplaintCard> myDataset;
    private  SessionManager sessionManager;
    private HashMap<String,String> userDetails;
    private ArrayList<String> supports;

     String Totcomments = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_complaint);
//        setContentView(R.layout.activity_complaint);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(ComplaintActivity.this,Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        Log.d(TAG, "onCreate: Starting.");
        sessionManager = new SessionManager(getApplicationContext());
        userDetails = sessionManager.getUserDetails();
        fetchcomplaints();

//        Map<String,Object> ComplaintDetailsfetchcomplaints();
//        mRecyclerView = findViewById(R.id.complaintRecycler);

        // Set up the ViewPager with the sections adapter.

//        myDataset = new ArrayList<>();

//
//
//        myDataset.add(new ComplaintCard("Ankur","07 Jan",getDrawable(R.drawable.ic_launcher_foreground),
//                "Subject","Body","12","14",false));
//
//        myDataset.add(new ComplaintCard("Ankur","07 Jan",getDrawable(R.drawable.ic_launcher_foreground),
//                "Subject","Body","12","14",false));
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//
//        // specify an adapter (see also next example)
//        mAdapter = new ComplaintAdapter(myDataset, this);
//        mRecyclerView.setAdapter(mAdapter);
//
//        FloatingActionButton fab = findViewById(R.id.complaintAdderFab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ComplaintActivity.this,AddComplaint.class));
//            }
//        });
        FloatingActionButton fab = findViewById(R.id.complaintAdderFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComplaintActivity.this,AddComplaint.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });

        //gymkhana

    }

    public void fetchcomplaints(){
        String hostel;

        mRecyclerView = findViewById(R.id.complaintRecycler);

        // Set up the ViewPager with the sections adapter.

        myDataset = new ArrayList<>();


        hostel=userDetails.get("hostel");

        final Map<String,Object> complaintsDetail = new HashMap<>();

        FirebaseFirestore.getInstance().collection("Complaint").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        Log.i("Shivam",documentSnapshot.getId() + "=> " + documentSnapshot.getData().toString());
//                        ArrayList<String> new arr;
                         supports =  new ArrayList<>();
//                        supports = documentSnapshot.get("supports");

                        List<String> supports;
                        supports = (List<String>)documentSnapshot.getData().get("supports");
                        int supportCount;
                        supportCount= supports.size();
                        final String supportSize;
                        supportSize= Integer.toString(supportCount);
                        Log.i("Shivam","Support"+supports+supportSize);
                        boolean supported;
                        String s;
                        String username;
                        username =userDetails.get("rollNo");
//                        Arrays.stream(supports).anyMatch(t -> t.equals(userDetails.get("name")));
                        supported=false;
                        s="false";
                        for (String str : supports){
//                            Log.i("shivam",str);
                            if (username.equals(str)) {
                                supported=true;
                                s="true";
                                break;
                            }
                        }
                        final Drawable supported_img;

                        Log.i("isSupported",Boolean.toString(supported));
                        Log.i("shivam","msg"+s);

                        if(supported) {
                            supported_img = getResources().getDrawable(R.drawable.ic_favorite_black_24dp);

                          //  Log.i("isSupportedImg","True");
                            Log.i("shivam","supported");
                        }
                        else {

                         supported_img = getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
//                            Log.i("isSupportedImg","False");
                            Log.i("shivam","not supported");
                        }
                        final String ComplainerName = documentSnapshot.getData().get("name").toString();
                        final String ComplaintTime = documentSnapshot.getData().get("time_stamp").toString();
                        final String Complaint = documentSnapshot.getData().get("complain").toString();
                        final String ComplaintBody = documentSnapshot.getData().get("body").toString();
                        final String id =documentSnapshot.getId();

                        FirebaseFirestore.getInstance().collection("Complaint").document(documentSnapshot.getId()).collection("comment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Totcomments = String.valueOf(task.getResult().size());
                                myDataset.add(new ComplaintCard(ComplainerName,ComplaintTime,getDrawable(R.drawable.ic_launcher_foreground),
                                        Complaint,ComplaintBody,id,supportSize,Totcomments, supported_img));
                                Log.i("Total comments",Totcomments);
                                mRecyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mRecyclerView.setLayoutManager(mLayoutManager);
                               // specify an adapter (see also next example)
                                mAdapter = new ComplaintAdapter(myDataset, getApplicationContext());
                                mRecyclerView.setAdapter(mAdapter);

                            }
                        });
                    }
                } else {
                    Log.i("Shivam", "failed");
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ComplaintActivity.this, Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
