package com.swc.onestop.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.swc.onestop.R;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {
    String feedbackurl ="https://goo.gl/forms/jk1nzkzHSXbBoa542";
    String reportbugurl ="https://goo.gl/forms/BLPlCF1FlTRonoXU2";
    String tusharfb ="https://www.facebook.com/ydv.tushar";
    String nikhilfb ="https://www.facebook.com/nikhil.gaddam.5";
    String ashwinfb ="https://www.facebook.com/ashwin.kulkarni.90";
    String ankurfb ="https://www.facebook.com/ankur.ingale.35";
    String anshumanfb ="https://www.facebook.com/anshuman.dhar.9";
    String shivamfb ="https://www.facebook.com/shivamkr286";
    String vivekfb ="https://www.facebook.com/SuperVivekRaj";
    String rishabfb ="https://www.facebook.com/rishabh.sood.98";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(AboutUs.this,Main2Activity.class));
                finish();
            }
        });
        ImageView tusharpic = findViewById(R.id.tusharpic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2F50502200_1530490733720412_3046734879342460928_n.jpg?alt=media&token=bcce5a9b-1740-4b8b-816e-18bc19e67308").into(tusharpic);

        ImageView nikhilpic = findViewById(R.id.nikhilpic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2F20190203_210454_4.jpg?alt=media&token=bb3dc130-b388-418f-8a46-0ae1280f0555").into(nikhilpic);

        ImageView ashwinpic = findViewById(R.id.ashwinpic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2F29496883_1756852191042325_5958279705923682304_n.jpg?alt=media&token=f0d19652-80fe-446e-8211-9144fb7d6e2e").into(ashwinpic);

        ImageView ankurpic = findViewById(R.id.ankurpic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2F45866477_2109294219136911_544194844851961856_n.jpg?alt=media&token=dadb111d-1eb3-4910-a0c8-7cd60aa9b9b4").into(ankurpic);

        ImageView anshumanpic = findViewById(R.id.anshumanpic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2F50160322_10210378196171550_1965820966764281856_n.jpg?alt=media&token=90e2300f-3604-4d19-94db-27e6df703726").into(anshumanpic);

        ImageView vivekpic = findViewById(R.id.vivekpic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2F21231339_1589910027790735_7480924332032344277_n.jpg?alt=media&token=26985348-4b67-4961-9c28-a5752402993d").into(vivekpic);

        ImageView shivampic = findViewById(R.id.shivampic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2F40675716_885072011686383_5229715384071880704_n.jpg?alt=media&token=ac72b79b-4cb1-4204-8793-f4e4d4dc1275").into(shivampic);

        ImageView rishabpic = findViewById(R.id.rishabpic);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/Aboutus%20Pics%2Frishab.jpg?alt=media&token=f7777f86-e571-4bde-8d3c-55da1ae26002").into(rishabpic);

        final LinearLayout feedback = (LinearLayout) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(feedbackurl));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout reportbug = (LinearLayout) findViewById(R.id.reportbug);
        reportbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(reportbugurl));
                startActivity(i);
                finish();
            }
        });

        final LinearLayout tushar = (LinearLayout) findViewById(R.id.tushar);
        tushar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(tusharfb));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout nikhil = (LinearLayout) findViewById(R.id.nikhil);
        nikhil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(nikhilfb));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout ashwin = (LinearLayout) findViewById(R.id.ashwin);
        ashwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ashwinfb));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout ankur = (LinearLayout) findViewById(R.id.ankur);
        ankur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ankurfb));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout anshuman = (LinearLayout) findViewById(R.id.anshunam);
        anshuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(anshumanfb));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout shivam = (LinearLayout) findViewById(R.id.shivam);
        shivam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(shivamfb));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout vivek = (LinearLayout) findViewById(R.id.vivek);
        vivek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(vivekfb));
                startActivity(i);
                finish();
            }
        });
        final LinearLayout rishab = (LinearLayout) findViewById(R.id.rishab);
        rishab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(rishabfb));
                startActivity(i);
                finish();
            }
        });





    }
    public void onClick(View v){

    }

}
