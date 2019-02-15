package com.swc.onestop.Timings_activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.swc.onestop.Activities.Complaints.AddComplaint;
import com.swc.onestop.Activities.Complaints.ComplaintActivity;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.Timings_activity;
import com.swc.onestop.R;


import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Ferry extends AppCompatActivity {
    TextView title;
    String day;
    private ExpandingList mExpandingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ferry);
        mExpandingList = findViewById(R.id.expanding_list_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.back_btn_color));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(Ferry.this,Timings_activity.class));
            }
        });


        title = (TextView) findViewById(R.id.ferry_tv);
        final NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday","Friday","Saturday"));
        niceSpinner.attachDataSource(dataset);


        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        switch(currentDay) {
            case Calendar.MONDAY:
                niceSpinner.setSelectedIndex(1);
                createItemsforweekdays();
                title.setText("Ferry Timings : Monday");
                break;
            case Calendar.TUESDAY:
                niceSpinner.setSelectedIndex(2);
                createItemsforweekdays();
                title.setText("Ferry Timings : Tuesday");
                break;
            case Calendar.WEDNESDAY:
                niceSpinner.setSelectedIndex(3);
                createItemsforweekdays();
                title.setText("Ferry Timings : Wednesday");
                break;
            case Calendar.THURSDAY:
                niceSpinner.setSelectedIndex(4);
                createItemsforweekdays();
                title.setText("Ferry Timings : Thursday");
                break;
            case Calendar.FRIDAY:
                niceSpinner.setSelectedIndex(5);
                createItemsforweekdays();
                title.setText("Ferry Timings : Friday");
                break;
            case Calendar.SATURDAY:
                niceSpinner.setSelectedIndex(6);
                createItemsforweekdays();
                title.setText("Ferry Timings : Saturday");
                break;
            case Calendar.SUNDAY:
                niceSpinner.setSelectedIndex(0);
                createItemsforSunday();
                title.setText("Ferry Timings : Sunday");
                break;
        }






        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        day="Sunday";break;
                    case 1:
                        day="Monday";break;
                    case 2:
                        day="Tuesday";break;
                    case 3:
                        day="Wednesday";break;
                    case 4:
                        day="Thursday";break;
                    case 5:
                        day="Friday";break;
                    case 6:
                        day="Saturday";break;


                }
                title.setText("Ferry Timings : "+day);
                if(position==0){

                    mExpandingList.removeAllViews();
                    createItemsforSunday();
                }
                else {
                    mExpandingList.removeAllViews();
                    createItemsforweekdays();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void createItemsforweekdays() {
        addItem("Guwahati to North Guwahati",new String[]{"7:00 am","7:40 am","8:45 am","9:45 am","11:30 am","1:00 pm","2:30 pm","3:30 pm","5:00 pm","6:15 pm","7:45 pm"}, R.color.yellow, R.drawable.ic_home);
        addItem("North Guwahati to Guwahati", new String[]{"7:15 am","8:00 am","9:15 am","10:15 am","12:15 pm","1:30 pm","3:00 pm","4:00 pm","5:30 pm","6:45 pm","8:00 pm"}, R.color.yellow, R.drawable.ic_location_city);

    }
    private void createItemsforSunday() {
        addItem("Guwahati to North Guwahati", new String[]{"8:45 am","11:30 am","2:30 pm", "5:30 pm"}, R.color.yellow, R.drawable.ic_home);
        addItem("North Guwahati to Guwahati", new String[]{"9:30 am","1:00 pm","3:30 pm", "6:00 pm"}, R.color.yellow, R.drawable.ic_location_city);

    }

    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout

        final ExpandingItem item = mExpandingList.createNewItem(R.layout.ferry_expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems[i]);
            }



        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.removeSubItem(view);
            }
        });
    }

    private void showInsertDialog(final Internal_bus.OnItemCreated positive) {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(text);
        builder.setTitle(R.string.enter_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positive.itemCreated(text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    interface OnItemCreated {
        void itemCreated(String title);
    }
}
