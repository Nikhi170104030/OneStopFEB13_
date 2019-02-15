package com.swc.onestop;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.swc.onestop.Activities.Mess_Menu;

import java.io.File;

public class MyDownloadManager extends BroadcastReceiver implements OnLoadCompleteListener ,OnPageChangeListener {
    private Context mContext;
    private String url;

    String TAG="test";


    public MyDownloadManager(){

    }



    public void Download(Context context, String Hostel, String url){
        mContext = context;
        this.url = url;
        String serviceString = Context.DOWNLOAD_SERVICE;
        DownloadManager downloadManager;
        downloadManager = (DownloadManager)mContext.getSystemService(serviceString);

            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir("OneStop", Hostel + ".pdf");
            Log.i("shivam", "");
            long reference = downloadManager.enqueue(request);





    }


    @Override
    public void onReceive(Context context, Intent intent) {

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);

        Intent localIntent = new Intent("Download complete");
// Send local broadcast
        localBroadcastManager.sendBroadcast(localIntent);

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}