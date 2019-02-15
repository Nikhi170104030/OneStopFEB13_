package com.swc.onestop.Timings_activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.Timings_activity;
import com.swc.onestop.R;


import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class IITG_Bus extends AppCompatActivity {

    private ExpandingList mExpandingList;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iitg__bus);

        mExpandingList = findViewById(R.id.expanding_list_main);
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);

        t1.setText("^^ -> 3 Buses from Sixmile,Beltola and Noonmati");
        t2.setText("** -> Available Only On Friday");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.back_btn_color));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(IITG_Bus.this,Timings_activity.class));
            }
        });
        final NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("Monday to Friday (Working Days)", "Saturday, Sunday and Institute Holidays"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setSelectedIndex(0);

        createworkingdays();


        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(position==0){
                    mExpandingList.removeAllViews();
                    createworkingdays();
                }
                else {
                    mExpandingList.removeAllViews();
                   createholidays();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void createworkingdays() {
        addItem("Campus To City", new String[]{"8:00 am","9:00 am","10:00 am","11:00 am ** ","12:00 noon","1:00 pm","2:00 pm","3:00 pm","5:00 pm","5:15 pm","5:40 pm","6:45 pm","8:00 pm","8:40 pm","9:00 pm","9:15 pm","9:30 pm","9:35 pm"}, R.color.yellow, R.drawable.ic_bus);
        addItem("City To Campus", new String[]{"6:45 am","7:45 am ^^","8:15 am","9:00 am **","10:00 am","12:00 noon","1:00 pm","2:00 pm","2:30 pm **","3:00 pm","4:00 pm","5:15 pm","6:45 pm","7:30 pm","8:00 pm","8:30 pm","8:45 pm **","8:55 pm"}, R.color.yellow, R.drawable.ic_bus);

    }

    private void createholidays() {
        addItem("Campus To City", new String[]{"8:00 am","9:00 am","10:00 am","10:30 am","12:00 noon","1:00 pm","2:00 pm","3:00 pm","3:30 pm","4:00 pm","5:00 pm","5:40 pm","6:45 pm","8:00 pm","8:40 pm","9:00 pm","9:15 pm","9:30 pm"}, R.color.yellow, R.drawable.ic_bus);
        addItem("City To Campus", new String[]{"6:45 am","7:45 am","8:15 am","10:00 am","11:00 pm","1:00 pm","2:00 pm","3:00 pm","4:00 pm","5:00 pm","6:00 pm","6:30 pm","7:15 pm","8:00 pm","8:30 pm","8:45 pm","8:55 pm","9:00 pm"}, R.color.yellow, R.drawable.ic_bus);
    }

    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

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
//            item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showInsertDialog(new Internal_bus.OnItemCreated() {
//                        @Override
//                        public void itemCreated(String title) {
//                            View newSubItem = item.createSubItem();
//                            configureSubItem(item, newSubItem, title);
//                        }
//                    });
//                }
//            });


        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
//        view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                item.removeSubItem(view);
//            }
//        });
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


}
