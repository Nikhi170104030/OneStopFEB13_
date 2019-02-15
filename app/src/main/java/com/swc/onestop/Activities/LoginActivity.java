package com.swc.onestop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swc.onestop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login_outlook;
    JSONObject jso;
    JSONArray cart_user_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        login_outlook = (Button) findViewById(R.id.lgn_outlook);
        login_outlook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Outlook_API_Activity.class);
                startActivity(intent);
            }
        });

    }

    public void main_open(View v)
    {
        Intent main_activity = new Intent(LoginActivity.this, Main2Activity.class);
        startActivity(main_activity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://202.141.80.161/gsec/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("abc", response);
                        try {
                            jso = new JSONObject(response);
                            cart_user_ticket = jso.getJSONArray("");
                            Log.e("abc", String.valueOf(jso));

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            public static final String TAG = "PV";


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("username", "shivam");
                params.put("password","abcdefgh");
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
