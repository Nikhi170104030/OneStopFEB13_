package com.swc.onestop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.swc.onestop.R;


public class SplashScreen extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
        setContentView(R.layout.activity_splash_screen);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionManager sessionManager;
                sessionManager = new SessionManager(getApplicationContext());
                if(sessionManager.isLoggedIn()){
                    startActivity(new Intent(SplashScreen.this,Main2Activity.class));
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },500);
    }
}
