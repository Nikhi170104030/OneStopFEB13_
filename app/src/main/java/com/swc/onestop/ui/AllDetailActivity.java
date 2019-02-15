package com.swc.onestop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.swc.onestop.R;
import com.swc.onestop.SmallRecyclers.CallClass;
import com.swc.onestop.SmallRecyclers.CallClassAdapter;
import com.swc.onestop.SmallRecyclers.OtherAndCallClass;
import com.swc.onestop.SmallRecyclers.OtherAndCallClassAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllDetailActivity extends Activity implements OnItemSelectedListener {

    private static final String TAG = "Tab1Fragment";
    private Spinner sItems;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> spinnerArray;
    private ArrayAdapter<String> adapter;
    private List<CallClass> mDatasetCC;
    private List<OtherAndCallClass> mDatasetOC;
    private int p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String whichFrag = i.getStringExtra("whichFrag");
        p = i.getIntExtra("position",0);
        this.setFinishOnTouchOutside(true);
        mDatasetCC = new ArrayList<>();
        mDatasetOC = new ArrayList<>();
        TextView t,s;
        ImageView im;

        if(whichFrag.charAt(0)=='G') {
            Log.v(AllDetailActivity.class.getSimpleName(),whichFrag+Integer.toString(p));
            switch (p) {

                case 0:
                    setContentView(R.layout.activity_all_detail);
                    t = findViewById(R.id.mainName) ; s = findViewById(R.id.sideText1);
                    t.setText(i.getStringExtra("title"));
                    s.setText(i.getStringExtra("status"));
                    im = findViewById(R.id.mainAvatar);
                    im.setImageDrawable(getDrawable(R.drawable.executives_and_senate_illustration_big));

                    mDatasetOC.clear();
                    mDatasetOC.add(new OtherAndCallClass(1,"N. Sarath Chandra","Vice President","+91 8142151097",
                            "vp@iitg.ac.in"));
                    mDatasetOC.add(new OtherAndCallClass(1,"Vamshi Reddy","Technical Secretary","+91 8142151097",
                            "techsec@iitg.ac.in"));
                    mDatasetOC.add(new OtherAndCallClass(1,"Shivank Shridhar","Cultural Secretary","+91 995798707",
                            "cultsec@iitg.ac.in"));
                    mDatasetOC.add(new OtherAndCallClass(1,"Nikunj Mittal","Sports Secretary","+91 8319973095",
                            "sportsec@iitg.ac.in"));
                    mDatasetOC.add(new OtherAndCallClass(1,"Nishant Bharti","Welfare Secretary","+91 9954250421"
                            ,"gensec_welfare@iitg.ac.in"));
                    mDatasetOC.add(new OtherAndCallClass(1,"Sahil Sareen","Gen Secretary, HAB","+91 8860734057"
                            ,"gensec_hostel@iitg.ac.in"));
                    mDatasetOC.add(new OtherAndCallClass(1,"Tushar Yadav","Gen Secretary, SWC","+91 9435685762"
                            ,"swc@iitg.ac.in"));
                    mRecyclerView = findViewById(R.id.recycler_view_for_recycler_view);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new OtherAndCallClassAdapter(mDatasetOC, this);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                    break;
                case 1:
                    setContentView(R.layout.activity_all_detail);
                    t = findViewById(R.id.mainName) ; s = findViewById(R.id.sideText1);
                    t.setText(i.getStringExtra("title"));
                    s.setText(i.getStringExtra("status"));
                    im = findViewById(R.id.mainAvatar);
                    im.setImageDrawable(getDrawable(R.drawable.executives_and_senate_illustration_big));
                    mDatasetCC.clear();
                    mDatasetCC.add(new CallClass(1,"Kaila H. Reddy","Undergrad Senator","+91 8309467880"));
                    mDatasetCC.add(new CallClass(1,"Anmol Deep","Undergrad Senator","+91 9126119003"));
                    mDatasetCC.add(new CallClass(1,"Mayank Kumar","Undergrad Senator","+91 9957941859"));
                    mDatasetCC.add(new CallClass(1,"Nikhil Panse","Undergrad Senator","+91 9401633870"));
                    mDatasetCC.add(new CallClass(1,"Samarth Chauhan","Undergrad Senator","+91 9954350215"));
                    mDatasetCC.add(new CallClass(1,"Prashant Singh","Undergrad Senator","+91 8630023818"));
                    mDatasetCC.add(new CallClass(1,"Namrata Gupta","Undergrad Senator","+91 9424703047"));
                    mDatasetCC.add(new CallClass(1,"Jigyasa","Girls Senator","+91 9954341898"));
                    mDatasetCC.add(new CallClass(1,"Parul Bansal","Girls Senator","+91 9954349714"));
                    mDatasetCC.add(new CallClass(1,"Pushya Bansal","Girls Senator","+91 9954349720"));
                    mDatasetCC.add(new CallClass(1,"Navneet Redhu","Post-grad Senator","+91 8901382830"));
                    mDatasetCC.add(new CallClass(1,"Anirban Das","Post-grad Senator","+91 9774385306"));
                    mDatasetCC.add(new CallClass(1,"Anirban Bhowal","Post-grad Senator","+91 7086867372"));
                    mDatasetCC.add(new CallClass(1,"Himanshu Raturi","Post-grad Senator","+91 7906903934"));
                    mDatasetCC.add(new CallClass(1,"Krishan Kumar","Post-grad Senator","+91 8812062555"));
                    mDatasetCC.add(new CallClass(1,"Shivani Gupta","Post-grad Senator","+91 9654513352"));
                    mDatasetCC.add(new CallClass(1,"Upasana Mahanta","Post-grad Senator","+91 8486219487"));
                    mRecyclerView = findViewById(R.id.recycler_view_for_recycler_view);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new CallClassAdapter(mDatasetCC, this);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

                    break;
                case 2:
                    setContentView(R.layout.activity_all_detail_spinner);
                    t = findViewById(R.id.mainName) ; s = findViewById(R.id.sideText1_spinner);
                    t.setText(i.getStringExtra("title"));
                    s.setText(i.getStringExtra("status"));
                    im = findViewById(R.id.mainAvatar);
                    im.setImageDrawable(getDrawable(R.drawable.hostel_secy_illustration_big));
                    spinnerArray =  new ArrayList<String>();
                    spinnerArray.clear();
                    spinnerArray.add("Select Hostel");
                    spinnerArray.add("Manas");
                    spinnerArray.add("Brahmaputra");
                    spinnerArray.add("Kameng");
                    spinnerArray.add("Siang");
                    spinnerArray.add("Kapili");
                    spinnerArray.add("Dihing");
                    spinnerArray.add("Lohit");
                    spinnerArray.add("Dibang");
                    spinnerArray.add("Barak");
                    spinnerArray.add("Umiam");
                    spinnerArray.add("Subansiri");
                    spinnerArray.add("Dhansiri");
                    spinnerArray.add("Married Scholars");
                    adapter = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, spinnerArray);

                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    sItems = (Spinner) findViewById(R.id.spinner);
                    sItems.setAdapter(adapter);
                    sItems.setOnItemSelectedListener(this);

                    mRecyclerView = findViewById(R.id.recycler_view_for_recycler_view2);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                    break;
                case 3:
                    setContentView(R.layout.activity_all_detail_spinner);
                    t = findViewById(R.id.mainName) ; s = findViewById(R.id.sideText1_spinner);
                    t.setText(i.getStringExtra("title"));
                    s.setText(i.getStringExtra("status"));
                    im = findViewById(R.id.mainAvatar);
                    im.setImageDrawable(getDrawable(R.drawable.culb_secy_illustration));
                    spinnerArray =  new ArrayList<String>();
                    spinnerArray.clear();
                    spinnerArray.add("Select Board");
                    spinnerArray.add("Technical Board");
                    spinnerArray.add("Cultural Board");
                    spinnerArray.add("Sports Board");
                    spinnerArray.add("Welfare Board");
                    adapter = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, spinnerArray);

                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    sItems = (Spinner) findViewById(R.id.spinner);
                    sItems.setAdapter(adapter);
                    sItems.setOnItemSelectedListener(this);

                    mRecyclerView = findViewById(R.id.recycler_view_for_recycler_view2);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                    break;
                default: ;
            }
        }
        else if(whichFrag.charAt(0)=='T') {
            setContentView(R.layout.activity_all_detail);


            switch (p) {
                case 1:
                    t = findViewById(R.id.mainName);
                    s = findViewById(R.id.sideText1);
                    t.setText(i.getStringExtra("title"));
                    s.setText(i.getStringExtra("status"));
                    im = findViewById(R.id.mainAvatar);
                    im.setImageDrawable(getDrawable(R.drawable.book_cab_illustration_big));
                    mDatasetOC.clear();
                    mDatasetOC.add(new OtherAndCallClass(2, "Ola", "", "080 67350900", "https://www.olacabs.com/"));
                    mDatasetOC.add(new OtherAndCallClass(2, "Uber", "", "1300 091 272", "https://www.uber.com/in/en/"));
                    mRecyclerView = findViewById(R.id.recycler_view_for_recycler_view);
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new OtherAndCallClassAdapter(mDatasetOC, this);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                    break;
                case 0:
                    t = findViewById(R.id.mainName);
                    s = findViewById(R.id.sideText1);
                    t.setText(i.getStringExtra("title"));
                    s.setText(i.getStringExtra("status"));
                    im = findViewById(R.id.mainAvatar);
                    im.setImageDrawable(getDrawable(R.drawable.e_rickshaw_illustration_big));
                    mDatasetCC.clear();
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 1", "", "+91 8472882165"));
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 2", "", "+91 7086238534"));
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 3", "", "+91 7670022244"));
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 4", "", "+91 9101789822"));
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 5", "", "+91 8240159280"));
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 6", "", "+91 7985418774"));
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 7", "", "+91 8876625039"));
                    mDatasetCC.add(new CallClass(1, "E-rickshaw 8", "", "+91 8474085136"));
                    mRecyclerView = findViewById(R.id.recycler_view_for_recycler_view);
                    mLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new CallClassAdapter(mDatasetCC, this);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                    break;
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(p){
            case 2:

                mDatasetCC.clear();

                switch(position){

                    case 0:
                        //mRecyclerView.setVisibility(View.INVISIBLE);
                        break;
                    case 1://manas
                        mDatasetCC.add(new CallClass(1,
                                "Pakshal Jain","General Secretary","+91 9833891384"));
                        mDatasetCC.add(new CallClass(1,
                                "Ankur Ingale","Literary Secretary","+91 8011991539"));
                        mDatasetCC.add(new CallClass(1,
                                "Anirudh Pratap","Welfare Secretary","+91 7896660199"));
                        mDatasetCC.add(new CallClass(1,
                                "Anant Sachan","Technical Secretary","+91 735535679"));
                        mDatasetCC.add(new CallClass(1,
                                "Devendra Barman","Mess Convenor","+91 9009906291"));
                        mDatasetCC.add(new CallClass(1,
                                "Anurag Kumar","Cultural Secretary","+91 7991149855"));
                        mDatasetCC.add(new CallClass(1,
                                "Hari OM Singh","Ass. Mess Convenor","+91 8486394372"));
                        mDatasetCC.add(new CallClass(1,
                                "Diwakar Sharma","Sports Secretary","+91 9718931163"));
                        mDatasetCC.add(new CallClass(1,
                                "Kanishq Agrawal","Maintenance Secretary","+91 7587529223"));
                        break;
                    case 2://brahma
                        mDatasetCC.add(new CallClass(1,
                                "Naman Pal","General Secretary","+91 9084666889"));
                        mDatasetCC.add(new CallClass(1,
                                "Garvit Sarjare","Literary Secretary","+91 9365495368"));
                        mDatasetCC.add(new CallClass(1,
                                "Vedant Bharti","Welfare Secretary","+91 8402024211"));
                        mDatasetCC.add(new CallClass(1,
                                "Yashraj Badiani","Technical Secretary","+91 9521416932"));
                        mDatasetCC.add(new CallClass(1,
                                "Vinod Kumar","Mess Convenor","+91 9954248867"));
                        mDatasetCC.add(new CallClass(1,
                                "Ganne Karthik","Cultural Secretary","+91 7896666212"));
                        mDatasetCC.add(new CallClass(1,
                                "Naresh Meena","Sports Secretary","+91 9001055246"));
                        mDatasetCC.add(new CallClass(1,
                                "Michael Samuel","Maintenance Secretary","+91 9701931320"));
                        break;
                    case 3://Kameng
                        mDatasetCC.add(new CallClass(1,
                                "Shubhansh Awasthi","General Secretary","+91 8319581128"));
                        mDatasetCC.add(new CallClass(1,
                                "Mohit Doddi","Literary Secretary","+91 9963193749"));
                        mDatasetCC.add(new CallClass(1,
                                "Sabbireddy Raghuram","Welfare Secretary","+91 8402024211"));
                        mDatasetCC.add(new CallClass(1,
                                "Vasu Goyal","Technical Secretary","+91 8376069678"));
                        mDatasetCC.add(new CallClass(1,
                                "Ankur Santoria","Mess Convenor","+91 9785940102"));
                        mDatasetCC.add(new CallClass(1,
                                "Ashish Sonavane","Cultural Secretary","+91 9365672486"));
                        mDatasetCC.add(new CallClass(1,
                                "Devansh Gupta","Sports Secretary","+91 8721001018"));
                        mDatasetCC.add(new CallClass(1,
                                "Parveen Jakhar","Maintenance Secretary","+91 9896839638"));
                        mDatasetCC.add(new CallClass(1,
                                "Chetan Kundra","J. Maintenance Secretary","+91 8058440717"));
                        break;
                    case 4://siang
                        mDatasetCC.add(new CallClass(1,
                                "Alankrat Shivhare","General Secretary","+91 9957955776"));
                        mDatasetCC.add(new CallClass(1,
                                "Mujahid Bari","Literary Secretary","+91 7896650578"));
                        mDatasetCC.add(new CallClass(1,
                                "Aashutosh Agarwal","Welfare Secretary","+91 9126870179"));
                        mDatasetCC.add(new CallClass(1,
                                "Aranya Aryaman","Technical Secretary","+91 7896657541"));
                        mDatasetCC.add(new CallClass(1,
                                "Akash Gupta","Mess Convenor","+91 8924809305"));
                        mDatasetCC.add(new CallClass(1,
                                "Rishabh Agarwal","Cultural Secretary","+91 9820968371"));
                        //mDatasetCC.add(new CallClass(1,
                        //"Hari OM Singh","Ass. Mess Convenor","+91 8486394372"));
                        mDatasetCC.add(new CallClass(1,
                                "Durgesh Dhakad","Sports Secretary","+91 7689803746"));
                        mDatasetCC.add(new CallClass(1,
                                "Aniket Sinha","Maintenance Secretary","+91 9525039083"));
                        break;
                    case 5://Kapili
                        mDatasetCC.add(new CallClass(1,
                                "Sujal Ranjan","General Secretary","+91 8486772158"));
                        mDatasetCC.add(new CallClass(1,
                                "J.V.Rithvik Varma","Literary Secretary","+91 7896662234"));
                        mDatasetCC.add(new CallClass(1,
                                "Sangam Sharma","Welfare Secretary","+91 8486808779"));
                        mDatasetCC.add(new CallClass(1,
                                "Manish Gurnani","Technical Secretary","+91 9649977558"));
                        mDatasetCC.add(new CallClass(1,
                                "Krishna Priyatam","Mess Convenor","+91 7896668644"));
                        mDatasetCC.add(new CallClass(1,
                                "Sankalp Gupta","Cultural Secretary","+91 9413081699"));
                        //mDatasetCC.add(new CallClass(1,
                        //     "Hari OM Singh","Ass. Mess Convenor","+91 8486394372"));
                        mDatasetCC.add(new CallClass(1,
                                "Divyanshu Chauhan","Sports Secretary","+91 7607037620"));
                        mDatasetCC.add(new CallClass(1,
                                "Krishkant Singh ","Maintenance Secretary","+91 7379438136"));
                        break;
                    case 6://dihing
                        mDatasetCC.add(new CallClass(1,
                                "Siddharth Jain","General Secretary","+91 9439505162"));
                        mDatasetCC.add(new CallClass(1,
                                "Sriharsha Nakka","Literary Secretary",""));
                        mDatasetCC.add(new CallClass(1,
                                "Sumant Kumar","Welfare Secretary","+91 9957964162"));
                        mDatasetCC.add(new CallClass(1,
                                "Chinmay Kothari","Technical Secretary","+91 8239374026"));
                        mDatasetCC.add(new CallClass(1,
                                "Purushottam Kumar","Mess Convenor","+91 8298863434"));
                        mDatasetCC.add(new CallClass(1,
                                "Mrutyunjaya Mohapatra","Cultural Secretary","+91 8402082840"));
                        //mDatasetCC.add(new CallClass(1,
                        //        "Hari OM Singh","Ass. Mess Convenor","+91 8486394372"));
                        mDatasetCC.add(new CallClass(1,
                                "Nitin Khandal","Sports Secretary","+91 8011992816"));
                        mDatasetCC.add(new CallClass(1,
                                "Yash Agrawal","Maintenance Secretary","+91 7663903041"));
                        break;
                    case 7://lohit
                        mDatasetCC.add(new CallClass(1,
                                "Shubhanker Jauhari","General Secretary","+91 9401157144"));
                        mDatasetCC.add(new CallClass(1,
                                "Pramod Reddy","Literary Secretary","+91 9435686035"));
                        mDatasetCC.add(new CallClass(1,
                                "Sai Kiran Reddy","Welfare Secretary","+91 9954349638"));
                        mDatasetCC.add(new CallClass(1,
                                "Suryansh Prabhat","Technical Secretary","+91 7896665242"));
                        mDatasetCC.add(new CallClass(1,
                                "Nikhil","Mess Convenor","+91 7636806554"));
                        mDatasetCC.add(new CallClass(1,
                                "Alok Urmaliya","Cultural Secretary","+91 7896664348"));

                        mDatasetCC.add(new CallClass(1,
                                "Kamal Kant","Sports Secretary","+91 8544185859"));
                        mDatasetCC.add(new CallClass(1,
                                "Jivesh Dahiya","Maintenance Secretary","+91 8199012961"));
                        break;
                    case 8://dibang
                        mDatasetCC.add(new CallClass(1,
                                "Pyarimohan Dehury","General Secretary","+91 +91 8280008066"));
                        mDatasetCC.add(new CallClass(1,
                                "Kunwar Singh","Literary Secretary","+91 7664846184"));
                        mDatasetCC.add(new CallClass(1,
                                "Adil Rather","Welfare Secretary","+91 7670082595"));
                        mDatasetCC.add(new CallClass(1,
                                "Bikash Sah","Technical Secretary","+91 9863057512"));
                        mDatasetCC.add(new CallClass(1,
                                "Milan Nayek","Mess Convenor","+91 9886573884"));
                        mDatasetCC.add(new CallClass(1,
                                "Gurpreet S. Sodhi","Cultural Secretary","+91 8638767062"));

                        mDatasetCC.add(new CallClass(1,
                                "Gaurav Talukdar","Sports Secretary","+91 8472979101"));
                        mDatasetCC.add(new CallClass(1,
                                "Sunayan Deka","Maintenance Secretary","+91 8752092174"));
                        break;
                    case 9://barak
                        mDatasetCC.add(new CallClass(1,
                                "Potini Aditya","General Secretary","+91 9492308431"));
                        mDatasetCC.add(new CallClass(1,
                                "Aditya Mehndiratta","Literary Secretary","+91 8712737542"));
                        mDatasetCC.add(new CallClass(1,
                                "Abhishek Purohit","Welfare Secretary","+91 9126918907"));
                        mDatasetCC.add(new CallClass(1,
                                "Venkata Puneeth","Technical Secretary","+91 8099534114"));
                        mDatasetCC.add(new CallClass(1,
                                "Hansraj Patel","Jt. Technical Secretary","+91 9079337823"));
                        mDatasetCC.add(new CallClass(1,
                                "Shivam Bansal","Mess Convenor","+91 8054104525"));
                        mDatasetCC.add(new CallClass(1,
                                "Pratyaya Singh","Cultural Secretary","+91 8011990983"));
                        mDatasetCC.add(new CallClass(1,
                                "Pankaj kumar","Jt. Cultural Secretary","+91 7663930832"));
                        mDatasetCC.add(new CallClass(1,
                                "Nilesh Khalse","Jt. Mess Convenor","+91 8486394372"));
                        mDatasetCC.add(new CallClass(1,
                                "Ashish Khoshya","Sports Secretary","+91 9468279347"));
                        mDatasetCC.add(new CallClass(1,
                                "Mukesh Bhangale","Jt. Sports Secretary","+91 9617817323"));
                        mDatasetCC.add(new CallClass(1,
                                "Deepak Meena","Maintenance Secretary","+91 6351321994"));
                        mDatasetCC.add(new CallClass(1,
                                "Azad Kumar","Jt. Maintenance Secretary","+91 9130647242"));
                        break;
                    case 10://umiam
                        mDatasetCC.add(new CallClass(1,
                                "Aditya Raj","General Secretary","+91 9430593445"));
                        mDatasetCC.add(new CallClass(1,
                                "Bipin Kumar","Literary Secretary","+91 9610543850"));
                        mDatasetCC.add(new CallClass(1,
                                "Mayank Patel","Welfare Secretary","+91 9954341464"));
                        mDatasetCC.add(new CallClass(1,
                                "Aryan Agarwal","Technical Secretary","+91 9304045973"));
                        mDatasetCC.add(new CallClass(1,
                                "Shubham Goel","Mess Convenor","+91 8402081106"));
                        mDatasetCC.add(new CallClass(1,
                                "Anurag Kumar","Cultural Secretary","+91 7991149855"));

                        mDatasetCC.add(new CallClass(1,
                                "Atharva Belapurkar","Sports Secretary","+91 9404678414"));
                        mDatasetCC.add(new CallClass(1,
                                "Okesh Chauhan","Maintenance Secretary","+91 9525039083"));
                        break;
                    case 11://subansiri
                        mDatasetCC.add(new CallClass(1,
                                "Swati Sarkar","General Secretary","+91 9435083681"));
                        mDatasetCC.add(new CallClass(1,
                                "Nandita Varshney","Literary Secretary","+91 7896657726"));
                        mDatasetCC.add(new CallClass(1,
                                "Shalini Kumari","Welfare Secretary","+91 8461975019"));
                        mDatasetCC.add(new CallClass(1,
                                "K Sri Achala","Technical Secretary","+91 8130947907"));
                        mDatasetCC.add(new CallClass(1,
                                "Hema Priya Manne","Mess Convenor","+91 8309177606"));
                        mDatasetCC.add(new CallClass(1,
                                "Ghanishtha Gargav","Cultural Secretary","+91 9575961634"));

                        mDatasetCC.add(new CallClass(1,
                                "Bhawana Parmar","Sports Secretary","+91 9755871073"));
                        mDatasetCC.add(new CallClass(1,
                                "Aanisha Akhtar","Maintenance Secretary","+91 8404036028"));
                        break;
                    case 12://dhansiri
                        mDatasetCC.add(new CallClass(1,
                                "Smriti Rekha","General Secretary","+91 9508850085"));
                        mDatasetCC.add(new CallClass(1,
                                "Manisha Srivastava","Literary Secretary","+91 8588872847"));
                        mDatasetCC.add(new CallClass(1,
                                "Lakshmisaidurga","Welfare Secretary","+91 9515356757"));
                        mDatasetCC.add(new CallClass(1,
                                "Neda Shamim","Technical Secretary","+91 7896657645"));
                        mDatasetCC.add(new CallClass(1,
                                "Juna Devi","Mess Convenor","+91 8473868926"));
                        mDatasetCC.add(new CallClass(1,
                                "Madhurima Chakraborty","Cultural Secretary","+91 7896668956"));

                        mDatasetCC.add(new CallClass(1,
                                "Pratibha Maurya","Sports Secretary","+91 8137082207 "));
                        mDatasetCC.add(new CallClass(1,
                                "Simran Soni","Maintenance Secretary","+91 7086867427"));
                        break;

                }

                // specify an adapter (see also next example)
                mAdapter = new CallClassAdapter(mDatasetCC, this);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 3:
                mDatasetCC.clear();


                switch(position){
                    case 0:
                        //mRecyclerView.setVisibility(View.INVISIBLE);
                        break;
                    case 1://technical
                        mDatasetCC.add(new CallClass(1,"Sri Harsha Vadathya","Events Manager","+91 912938122"));

                        mDatasetCC.add(new CallClass(1,"Nagireddy Pravardhan","Aeromodelling Club","+91 7002863704"));
                        mDatasetCC.add(new CallClass(1,"Prashanth Ravichandar","Astromnomy Club","+91 8486801868"));
                        mDatasetCC.add(new CallClass(1,"Pavan Kumar","Coding Club","+91 8309329731"));
                        mDatasetCC.add(new CallClass(1,"Prateek Manocha","Electronics Club","+91 9711090451"));
                        mDatasetCC.add(new CallClass(1,"Venkata Manideep","EDC","+91 9948958249"));
                        mDatasetCC.add(new CallClass(1,"Himansh Mittal","Automobile Club","+91 9957997158"));
                        mDatasetCC.add(new CallClass(1,"Shubhang Shukla","Quiz Club","+91 9407839924"));
                        mDatasetCC.add(new CallClass(1,"Shrey Jain","Finance & Economics Club","+91 8003825647"));
                        mDatasetCC.add(new CallClass(1,"Ashwin Devanga","Prakriti Club","+91 9127512231"));
                        break;
                    case 2://cultural
                        mDatasetCC.add(new CallClass(1,"Deekshant","Events Manager","+91 7073631850"));

                        mDatasetCC.add(new CallClass(1,"Mahit Munakala","Anchorenza Club","+91 9957961234"));
                        mDatasetCC.add(new CallClass(1,"Dilip Saini","Astronomy Club","+91 7002342115"));
                        mDatasetCC.add(new CallClass(1,"Agrata Patel","Xpressions Club","+91 9957956954"));
                        mDatasetCC.add(new CallClass(1,"Dhiti Singh","Fine Arts Club","+91 9999655568"));
                        mDatasetCC.add(new CallClass(1,"Jishnu Chander","LitSoc - DebSoc","+91 7086052915"));
                        mDatasetCC.add(new CallClass(1,"Yash Kulkarni","Lumiere Club","+91 9957955322"));
                        mDatasetCC.add(new CallClass(1,"Avik Biswas","Octaves Club","+91 9479875344"));
                        mDatasetCC.add(new CallClass(1,"Kartikey Kant","Montage Club","+91 7577994159"));
                        break;
                    case 3://sports
                        mDatasetCC.add(new CallClass(1,"Nitin Chauhan","Events Manager","+91 8504083929"));

                        mDatasetCC.add(new CallClass(1,"Karthik Jayachandran","Aquatics Club","+91 9400354209"));
                        mDatasetCC.add(new CallClass(1,"Manohar Paraliya","Athletics Club","+91 9957993719"));
                        mDatasetCC.add(new CallClass(1,"Pratik Ghongade","Badminton Club","+91 9126918863"));
                        mDatasetCC.add(new CallClass(1,"Ajay Singh","Basketball Club","+91 9126870231"));
                        mDatasetCC.add(new CallClass(1,"Krishan Chand","Cricket Club","+91 8011991005"));
                        mDatasetCC.add(new CallClass(1,"Dyuman Joshi","Football Club","+91 8486808798"));
                        mDatasetCC.add(new CallClass(1,"Bhuvana Datta","Hockey Club","+91 9957991539"));
                        mDatasetCC.add(new CallClass(1,"Rudra Parikh","Squash Club","+91 9167870825"));
                        mDatasetCC.add(new CallClass(1,"Aman Deo","Lawn Tennis Club","+91 8473811815"));
                        mDatasetCC.add(new CallClass(1,"U. Preetham Varma","Table Tennis Club","+91 8011991487"));
                        mDatasetCC.add(new CallClass(1,"Uday Kiran","Volleyball Club","+91 9505932249"));
                        mDatasetCC.add(new CallClass(1,"D Raghuprasad","Weightlifting Club","+91 9957959300"));
                        break;
                    case 4://welfare

                        mDatasetCC.add(new CallClass(1,"Ayush Choudhary","Saathi Club","+91 8486814024"));
                        mDatasetCC.add(new CallClass(1,"Ayush Vijay","Rights and Responsibilities Club","+91 8011025655"));
                        mDatasetCC.add(new CallClass(1,"Glory Basumatal","Red and Ribbon Club","+91 7026252121"));
                        mDatasetCC.add(new CallClass(1,"Umesh Goyal","Academic Initiative Club","+91 9166127840"));
                        mDatasetCC.add(new CallClass(1,"Shantanu Kumar","Social Service Club","+91 8986714142"));
                        mDatasetCC.add(new CallClass(1,"G Rohith Kumar","Youth Empowerment Club","+91 9967966852"));

                        break;
                }
                mAdapter = new CallClassAdapter(mDatasetCC, this);
                mRecyclerView.setAdapter(mAdapter);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        switch(p){
            case 2:
                break;
            case 3:
                break;
        }
    }
}
