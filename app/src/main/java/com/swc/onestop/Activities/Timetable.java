package com.swc.onestop.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.swc.onestop.MyDownloadManager;
import com.swc.onestop.R;

import java.io.File;

public class Timetable extends AppCompatActivity implements OnLoadCompleteListener, OnPageChangeListener {

    ProgressDialog progressdialog;
    PDFView pdfview;
    String dir =Environment.getExternalStorageDirectory().toString()+"/OneStop/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        pdfview = (PDFView) findViewById(R.id.pdf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(Timetable.this,Main2Activity.class));
            }
        });

        progressdialog = new ProgressDialog(Timetable.this);
        progressdialog.setMessage("Timetable Downloading....");
        LoadPDF("https://firebasestorage.googleapis.com/v0/b/onestopiitg.appspot.com/o/timetable.pdf?alt=media&token=0dfd9335-0735-4dc8-9696-7025ef2b920b");

    }


    public void LoadPDF(String url){
        if(isExternalStorageReadable() && isExternalStorageWritable()){

            File file = new File(dir, "/"+"Timetable.pdf");

            // Log.i("shivam",dir+"/"+ Hostel + ".pdf");
            // Log.i("Testing",getFilesDir().toString());
            if (!file.exists()){
                Log.i("shivam","File Does not exists");

                if (isOnline()) {
                    beginDownload("Timetable",url);
                    progressdialog.show();
                }

                else{
                    showSnackbar();
                }


            }
            else {
                SetPDF("Timetable");
                Log.i("shivam","File exists");

            }





        }
    }


    private void beginDownload(final String Hostel, String URL) {


        BroadcastReceiver listener = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                //String data = intent.getStringExtra("Download complete");
                Log.i("shivam",Hostel+" File Downloading at"+dir);
                progressdialog.hide();
                SetPDF(Hostel);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(listener,
                new IntentFilter("Download complete"));

        MyDownloadManager downloadManager = new MyDownloadManager();

        downloadManager.Download(getApplicationContext(),"Timetable", URL);





    }
    public void SetPDF(String Hostel){
        Log.e("test","PDF Loaded");
        File file = new File(dir+"/"+Hostel + ".pdf");
        pdfview.fromFile(file)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onLoad(Timetable.this)
                .onPageChange(Timetable.this)
                .load();

    }
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.i("Internet" ,"Online");
            return true;
        }
        Log.i("Internet" ,"Offline");
        return false;
    }

    public void showSnackbar(){
        final Snackbar snackbar = Snackbar
                .make((LinearLayout)findViewById(R.id.remote_pdf_root), "You are Offline", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isOnline()){

                        }
                        else
                            showSnackbar();
                    }
                });
        snackbar.show();

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}
