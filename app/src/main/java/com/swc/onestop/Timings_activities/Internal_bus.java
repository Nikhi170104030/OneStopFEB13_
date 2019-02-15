package com.swc.onestop.Timings_activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.Timings_activity;
import com.swc.onestop.R;


public class Internal_bus extends AppCompatActivity {


    private ExpandingList mExpandingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_bus);

        mExpandingList = findViewById(R.id.expanding_list_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.back_btn_color));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(Internal_bus.this,Timings_activity.class));
            }
        });
        createItems();
    }

    private void createItems() {
        addItem("7:30 AM", new String[]{"7:30 am: E Type","7:32 am: D Type","7:34 am: C Type","7:36 am: A/B Type","7:38 am: GuestHouse","7:40 am: Hospital","7:42 am: Dhansiri","7:44 am: Kameng","7:46 am: Manas","7:48 am: Dihing","7:49 am: Kapili","7:52 am: Core IV ","7:54 am :Core III","7:55 am: Core I","7:58 am: Admin"}, R.color.yellow, R.drawable.ic_bus);
        addItem("8:30 AM", new String[]{"8:30 am: E Type","8:32 am: D Type","8:34 am: C Type","8:36 am: A/B Type","8:38 am: GuestHouse","8:40 am: Hospital","8:42 am: Dhansiri","8:44 am: Kameng","8:46 am: Manas","8:48 am: Dihing","8:49 am: Kapili","8:52 am: Core IV ","8:54 am :Core III","8:55 am: Core I","8:58 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("8:40 AM", new String[]{"8:40 am: Dhansiri/MSH","8:42 am: Kameng","8:44 am: Manas","8:46 am: Dihing","8:48 am: Kapili","8:52 am: Core IV ","8:53 am: Core III","8:55 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("9:40 AM", new String[]{"9:40 am: Dhansiri/MSH","9:42 am: Kameng","9:44 am: Manas","9:46 am: Dihing","9:48 am: Kapili","9:52 am: Core IV ","9:53 am: Core III","9:55 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("9:50 AM", new String[]{"9:50 am: E Type","9:52 am: D Type","9:54 am: C Type","9:56 am: A/B Type","9:58 am: GuestHouse","10:00 am: Hospital","10:02 am: Dhansiri","10:04 am: Kameng","10:06 am: Manas","10:08 am: Dihing","10:09 am: Kapili","10:12 am: Core IV ","10:14 am :Core III","10:15 am: Core I","10:18 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("10:40 AM", new String[]{"10:40 am: Dhansiri/MSH","10:42 am: Kameng","10:44 am: Manas","10:46 am: Dihing","10:48 am: Kapili","10:52 am: Core IV ","10:53 am: Core III","10:55 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("11:40 AM", new String[]{"11:40 am: Dhansiri/MSH","11:42 am: Kameng","11:44 am: Manas","11:46 am: Dihing","11:48 am: Kapili","11:52 am: Core IV ","11:53 am: Core III","11:55 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("12:05 PM", new String[]{"12:05 pm: Admin","12:08 pm: Core I","12:11 pm: Core III","12:13 pm: Core IV","12:17 pm: Kapili","12:19 pm: Dihing","12:21 pm: Manas","12:23 pm: Kameng","12:25 pm: New Badminton Court","12:29 pm: E Type ","12:31 pm: D Type ","12:32pm: C Type ","12:34 pm: A/B Type","12:36 pm: Guest House","12:38 pm: Hospital "}, R.color.yellow, R.drawable.ic_bus);
        addItem("12:15 PM", new String[]{"12:15 pm: Core I","12:17 pm: Core III","12:19 pm: Core IV","12:23 pm: Kapili","12:25 pm: Dihing","12:28 pm: Manas","12:30 pm: Kameng ","12:32 pm: Dhansiri/MSH "}, R.color.yellow, R.drawable.ic_bus);
        addItem("1:05 PM", new String[]{"1:05 pm: Admin","1:08 pm: Core I","1:11 pm: Core III","1:13 pm: Core IV","1:17 pm: Kapili","1:19 pm: Dihing","1:21 pm: Manas","1:23 pm: Kameng","1:25 pm: New Badminton Court","1:29 pm: E Type ","1:31 pm: D Type ","1:32pm: C Type ","1:34 pm: A/B Type","1:36 pm: Guest House","1:38 pm: Hospital "}, R.color.yellow, R.drawable.ic_bus);
        addItem("1:40 AM", new String[]{"1:40 am: Dhansiri/MSH","1:42 am: Kameng","1:44 am: Manas","1:46 am: Dihing","1:48 am: Kapili","1:52 am: Core IV ","1:53 am: Core III","1:55 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("1:45 PM", new String[]{"1:45 pm: Kameng","1:47 pm: Manas","1:49 pm: Dihing","1:50 pm: Kapili","1:53 pm: Core IV","1:55 pm: Core III","1:57 pm: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("2:40 PM", new String[]{"2:40 pm: Core IV ","2:42 pm: Core III ","2:44 pm: Core I ","2:46 pm: Admin","2:48 pm: E Type","2:50 pm: D Type ","2:52 pm: C Type","2:54 pm: A/B Type","2:56 pm: Guest House","2:58 pm: Hospital","3:00 pm: Dhansiri/MSH","3:02 pm: Kameng","3:04 pm: Manas","3:06 pm: Dihing","3:08 pm: Kapili","3:10 pm: KV"}, R.color.yellow, R.drawable.ic_bus);
        addItem("2:40 AM*", new String[]{"2:40 am: Dhansiri/MSH","2:42 am: Kameng","2:44 am: Manas","2:46 am: Dihing","2:48 am: Kapili","2:52 am: Core IV ","2:53 am: Core III","2:55 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("4:40 AM", new String[]{"4:40 am: Dhansiri/MSH","4:42 am: Kameng","4:44 am: Manas","4:46 am: Dihing","4:48 am: Kapili","4:52 am: Core IV ","4:53 am: Core III","4:55 am: Core I"}, R.color.yellow, R.drawable.ic_bus);
        addItem("5:10 PM", new String[]{"5:10 pm: Core I","5:12 pm: Core III","5:14 pm: Core IV","5:15 pm: Kapili","5:18 pm: Dihing","5:20 pm: Manas","5:22 pm: Kameng","5:24 pm: New Badminton Court","5:27 pm: E Type ","5:29 pm: D Type ","5:31pm: C Type ","5:33 pm: A/B Type","5:35 pm: Guest House","5:37 pm: Hospital "}, R.color.yellow, R.drawable.ic_bus);
        addItem("5:15 PM", new String[]{"5:15 pm: Core I","5:17 pm: Core III ","5:19 pm: Core IV ","5:23 pm: Kapili","5:25 pm: Dihing","5:27 pm: Manas","5:29 pm: Kameng","5:31 pm: Dhansiri/MSH ","5:35 pm: Guest House","5:38 pm: Subansiri Bus Stop"}, R.color.yellow, R.drawable.ic_bus);
        addItem("5:50 PM", new String[]{"5:50 pm: Admin","5:52 pm: Core I","5:54 pm: Core III","5:56 pm: Core IV","5:58 pm: Kapili","6:00 pm: Dihing","6:02 pm: Manas","6:04 pm: Kameng","6:06 pm: New Badminton Court","6:09 pm: E Type ","6:11 pm: D Type ","6:13 pm: C Type ","6:15  pm: A/B Type","6:17 pm: Guest House","6:19 pm: Hospital "}, R.color.yellow, R.drawable.ic_bus);
        addItem("6:35 PM", new String[]{"6:35 pm: Admin","6:37 pm: Core I","6:39 pm: Core III","6:41 pm: Core IV","6:43 pm: Kapili","6:45 pm: Dihing","6:47 pm: Manas","6:49 pm: Kameng","6:51 pm: New Badminton Court","6:53 pm: E Type ","6:55 pm: D Type ","6:57 pm: C Type ","6:59  pm: A/B Type","7:01 pm: Guest House","7:03 pm: Hospital "}, R.color.yellow, R.drawable.ic_bus);
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
//                    showInsertDialog(new OnItemCreated() {
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

    }

    private void showInsertDialog(final OnItemCreated positive) {
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

    public interface OnItemCreated {
        void itemCreated(String title);
    }
}
