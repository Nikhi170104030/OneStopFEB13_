package com.swc.onestop.ui;

import android.Manifest;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Internet_settings.LanSet;
import com.swc.onestop.R;
import com.swc.onestop.TabFragments.SectionsPageAdapter;
import com.swc.onestop.TabFragments.Tab1Fragment;
import com.swc.onestop.TabFragments.Tab2Fragment;
import com.swc.onestop.TabFragments.Tab3Fragment;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private static final String TAG = "ContactActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(ContactActivity.this,Main2Activity.class));
            }
        });
        Log.d(TAG, "onCreate: Starting.");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},1);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        NavigationTabStrip tabLayout = (NavigationTabStrip) findViewById(R.id.tabs);
        tabLayout.setViewPager(mViewPager);


        //gymkhana

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "TAB1");
        adapter.addFragment(new Tab2Fragment(), "TAB2");
        adapter.addFragment(new Tab3Fragment(), "TAB3");
        viewPager.setAdapter(adapter);
    }

}
