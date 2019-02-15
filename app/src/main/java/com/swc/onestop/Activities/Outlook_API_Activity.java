package com.swc.onestop.Activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.microsoft.aad.adal.ADALError;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.IDispatcher;
import com.microsoft.aad.adal.Logger;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.aad.adal.Telemetry;
import com.microsoft.aad.adal.UserInfo;
import com.swc.onestop.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class Outlook_API_Activity extends AppCompatActivity {
    ProgressDialog progressdialog;
    /* UI & Debugging Variables */
    private static final String TAG = Outlook_API_Activity.class.getSimpleName();


    private SharedPreferences myPreferences;
    String UserId;
    SharedPreferences.Editor myEditor;

    FirebaseFirestore db;
    private String[] hostelList={"Lohit","Barak","Umiam","Dihing","Siang","Manas","Kameng","Brahmaputra","Dibang","Kapili","Dhansiri","Subansiri"};
    private int selectedHostel;
    private SessionManager sessionManager;

    /* Azure AD Constants */
    /* Authority is in the form of https://login.microsoftonline.com/yourtenant.onmicrosoft.com */
    private static final String AUTHORITY = "https://login.microsoftonline.com/common";
    /* The clientID of your application is a unique identifier which can be obtained from the app registration portal */
    private static final String CLIENT_ID = "89ec20b5-bdc4-45ef-9a29-3e8877745f09";
    /* Resource URI of the endpoint which will be accessed */
    private static final String RESOURCE_ID = "https://graph.microsoft.com/";
    /* The Redirect URI of the application (Optional) */
    private static final String REDIRECT_URI = "http://localhost";

    /* Microsoft Graph Constants */
    private final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/me";
    private final static String MSGRAPH_PHOTO_URL = "https://outlook.office.com/api/v2.0/me/photo/$value";

    /* Azure AD Variables */
    private AuthenticationContext mAuthContext;
    private AuthenticationResult mAuthResult;

    /* Handler to do an interactive sign in and acquire token */
    private Handler mAcquireTokenHandler;
    /* Boolean variable to ensure invocation of interactive sign-in only once in case of multiple  acquireTokenSilent call failures */
    private static AtomicBoolean sIntSignInInvoked = new AtomicBoolean();
    /* Constant to send message to the mAcquireTokenHandler to do acquire token with Prompt.Auto*/
    private static final int MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO = 1;
    /* Constant to send message to the mAcquireTokenHandler to do acquire token with Prompt.Always */
    private static final int MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS = 2;

    /* Constant to store user id in shared preferences */
    private static final String USER_ID = "user_id";

    /* Telemetry variables */
    // Flag to turn event aggregation on/off
    private static final boolean sTelemetryAggregationIsRequired = true;

    /* Telemetry dispatcher registration */
    static {
        Telemetry.getInstance().registerDispatcher(new IDispatcher() {
            @Override
            public void dispatchEvent(Map<String, String> events) {
                // Events from ADAL will be sent to this callback
                for(Map.Entry<String, String> entry: events.entrySet()) {
                    Log.d(TAG, entry.getKey() + ": " + entry.getValue());
                }
            }
        }, sTelemetryAggregationIsRequired);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("methodName").equals("onSignOutClicked")){
            onSignOutClicked();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlook__api_);
        progressdialog = new ProgressDialog(Outlook_API_Activity.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        db = FirebaseFirestore.getInstance();
        sessionManager = new SessionManager(getApplicationContext());


        myPreferences = PreferenceManager.getDefaultSharedPreferences(Outlook_API_Activity.this);
        myEditor = myPreferences.edit();

        mAuthContext = new AuthenticationContext(getApplicationContext(), AUTHORITY, false);

        /* Instantiate handler which can invoke interactive sign-in to get the Resource
         * sIntSignInInvoked ensures interactive sign-in is invoked one at a time */

        mAcquireTokenHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if( sIntSignInInvoked.compareAndSet(false, true)) {
                    if (msg.what == MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO){
                        mAuthContext.acquireToken(getActivity(), RESOURCE_ID, CLIENT_ID, REDIRECT_URI, PromptBehavior.Auto, getAuthInteractiveCallback());
                    }else if(msg.what == MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS){
                        mAuthContext.acquireToken(getActivity(), RESOURCE_ID, CLIENT_ID, REDIRECT_URI, PromptBehavior.Always, getAuthInteractiveCallback());
                    }
                }
            }
        };
//
//        complainButton = (Button) findViewById(R.id.complain);
//        Button messmenuButton = (Button) findViewById(R.id.messmenu);
//        complainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i=new Intent(Outlook_API_Activity.this , complaints.class);
//                startActivity(i);
//
//            }
//        });
//        messmenuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Outlook_API_Activity.this , MessMenu.class);
//                startActivity(i);
//            }
//        });

        /* ADAL Logging callback setup */

        Logger.getInstance().setExternalLogger(new Logger.ILogger() {
            @Override
            public void Log(String tag, String message, String additionalMessage, Logger.LogLevel level, ADALError errorCode) {
                // You can filter the logs  depending on level or errorcode.
                Log.d(TAG, message + " " + additionalMessage);
            }
        });

        /*Attempt an acquireTokenSilent call to see if we're signed in*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userId = preferences.getString(USER_ID, "");
        if(!TextUtils.isEmpty(userId)){
            mAuthContext.acquireTokenSilentAsync(RESOURCE_ID, CLIENT_ID, userId, getAuthSilentCallback());
        }
        onCallGraphClicked();
    }

    //
    // Core Auth methods used by ADAL
    // ==================================
    // onActivityResult() - handles redirect from System browser
    // onCallGraphClicked() - attempts to get tokens for graph, if it succeeds calls graph & updates UI
    // callGraphAPI() - called on successful token acquisition which makes an HTTP request to graph
    // onSignOutClicked() - Signs user out of the app & updates UI
    //

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAuthContext.onActivityResult(requestCode, resultCode, data);
    }

    /*
     * End user clicked call Graph API button, time for Auth
     * Use ADAL to get an Access token for the Microsoft Graph API
     */
    private void onCallGraphClicked() {
        mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO);
        progressdialog.dismiss();
    }

    private void callGraphAPI() {
        Log.d(TAG, "Starting volley request to graph");

        /* Make sure we have a token to send to graph */
        if (mAuthResult.getAccessToken() == null) {return;}

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();

        try {
            parameters.put("key", "value");
        } catch (Exception e) {
            Log.d(TAG, "Failed to put parameters: " + e.toString());
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MSGRAPH_URL,
                parameters,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /* Successfully called graph, process data and send to UI */
                Log.d(TAG, "Response: " + response.toString());

                updateGraphUI(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + mAuthResult.getAccessToken());
                return headers;
            }
        };

        Log.d(TAG, "Adding HTTP GET to Queue, Request: " + request.toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void callGraphAPI_Photo() {
        Log.d(TAG, "Starting volley request to graph");

        /* Make sure we have a token to send to graph */
        if (mAuthResult.getAccessToken() == null) {return;}

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();

        try {
            parameters.put("key", "value");
        } catch (Exception e) {
            Log.d(TAG, "Failed to put parameters: " + e.toString());
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://outlook.office.com/api/v2.0/Users('5a341191-76be-4702-bdf2-2a03b73ae24c')/photo/$value",
                parameters,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /* Successfully called graph, process data and send to UI */
                Log.i(TAG, "Response PHOTO: " + response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + mAuthResult.getAccessToken());
                return headers;
            }
        };

        Log.d(TAG, "Adding HTTP GET to Queue, Request: " + request.toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void onSignOutClicked() {
        // End user has clicked the Sign Out button
        // Kill the token cache
        // Optionally call the signout endpoint to fully sign out the user account
        mAuthContext.getCache().removeAll();
        sessionManager.logoutUser();
        //startActivity(new Intent(Outlook_API_Activity.this,LoginActivity.class));
    }

    //
    // UI Helper methods
    // ================================
    // updateGraphUI() - Sets graph response in UI
    // updateSuccessUI() - Updates UI when token acquisition succeeds
    // updateSignedOutUI() - Updates UI when app sign out succeeds
    //

    private void updateGraphUI(JSONObject response) {


        // Called on success from /me endpoint
        // Writes graph data to the UI
       // TextView graphText = (TextView) findViewById(R.id.graphData);
    //    graphText.setText(response.toString());
        try {
            Log.i("User Json",response.toString());
            myEditor.putString("mail",response.get("mail").toString());
            myEditor.putString("rollNo",response.get("surname").toString());
            UserId = response.get("id").toString();
            myEditor.putString("id",response.get("id").toString());
            myEditor.commit();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        callGraphAPI_Photo();
    }

    @SuppressLint("SetTextI18n")
    private void updateSuccessUI() {
        // Called on success from /me endpoint
        // Removed call Graph API button and paint Sign out


//        Toast.makeText(getApplicationContext(),"HELLO",Toast.LENGTH_SHORT).show();

        db.collection("users").document(mAuthResult.getUserInfo().getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();


                    if (document.exists()) {
                        Toast.makeText(getApplicationContext(),document.getData().get("name").toString(),Toast.LENGTH_LONG).show();
                        sessionManager.createLoginSession(document.getData().get("name").toString(),
                                document.getData().get("mail").toString(),
                                document.getData().get("rollNo").toString(),
                                document.getData().get("hostel").toString(),
                                mAuthResult.getUserInfo().getUserId());
                        Intent main_activity = new Intent(Outlook_API_Activity.this, Main2Activity.class);
                        startActivity(main_activity);
                    } else {
                        showHostelSelectDialog();

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    public void showHostelSelectDialog(){
        Toast.makeText(getApplicationContext(),"Hey",Toast.LENGTH_LONG).show();
        AlertDialog.Builder alert_dialog_builder_course_selection = new AlertDialog.Builder(getActivity());
        alert_dialog_builder_course_selection.setTitle("Select Hostel").setSingleChoiceItems(hostelList, -1, new DialogInterface.OnClickListener() {
            @Override
            //when selected identify the course index selected
            public void onClick(DialogInterface dialog, int selected_index) {
                selectedHostel= selected_index;
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            //on positive response allow session password taking
            public void onClick(DialogInterface dialog, int selected_index) {

                UserInfo user = mAuthResult.getUserInfo();
                sessionManager.createLoginSession(user.getGivenName(),
                        myPreferences.getString("mail","")
                        ,myPreferences.getString("rollNo","")
                        ,hostelList[selectedHostel],mAuthResult.getUserInfo().getUserId());
                Map<String, Object> currentUser = new HashMap<>();
                currentUser.put("name", user.getGivenName());
                currentUser.put("mail", myPreferences.getString("mail",""));
                currentUser.put("rollNo", myPreferences.getString("rollNo",""));
                currentUser.put("hostel", hostelList[selectedHostel]);
                currentUser.put("id", mAuthResult.getUserInfo().getUserId());

                db.collection("users").document(mAuthResult.getUserInfo().getUserId())
                        .set(currentUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Intent main_activity = new Intent(Outlook_API_Activity.this, Main2Activity.class);
                                startActivity(main_activity);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

            }
        });
        AlertDialog alert_dialog_course_selection = alert_dialog_builder_course_selection.create();

        // show it
        alert_dialog_course_selection.show();
    }
    @SuppressLint("SetTextI18n")

    //
    // ADAL Callbacks
    // ======================
    // getActivity() - returns activity so we can acquireToken within a callback
    // getAuthSilentCallback() - callback defined to handle acquireTokenSilent() case
    // getAuthInteractiveCallback() - callback defined to handle acquireToken() case
    //

    public Activity getActivity() {
        return this;
    }

    /* Callback used in for silent acquireToken calls.
     * Looks if tokens are in the cache (refreshes if necessary and if we don't forceRefresh)
     * else errors that we need to do an interactive request.
     */
    private AuthenticationCallback<AuthenticationResult> getAuthSilentCallback() {
        return new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                if(authenticationResult==null || TextUtils.isEmpty(authenticationResult.getAccessToken())
                        || authenticationResult.getStatus()!= AuthenticationResult.AuthenticationStatus.Succeeded){
                    Log.d(TAG, "Silent acquire token Authentication Result is invalid, retrying with interactive");
                    /* retry with interactive */
                    mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO);
                    return;
                }
                /* Successfully got a token, call graph now */
                Log.d(TAG, "Successfully authenticated");
                /* Store the mAuthResult */
                mAuthResult = authenticationResult;
                /* call graph */
                callGraphAPI();

                /* update the UI to post call graph state */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateSuccessUI();
                    }
                });
            }

            @Override
            public void onError(Exception exception) {
                /* Failed to acquireToken */
                Log.e(TAG, "Authentication failed: " + exception.toString());
                if (exception instanceof AuthenticationException) {
                    AuthenticationException authException = ((AuthenticationException) exception);
                    ADALError error = authException.getCode();
                    logHttpErrors(authException);
                    /*  Tokens expired or no session, retry with interactive */
                    if (error == ADALError.AUTH_REFRESH_FAILED_PROMPT_NOT_ALLOWED ) {
                        mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO);
                    }else if(error == ADALError.NO_NETWORK_CONNECTION_POWER_OPTIMIZATION){
                        /* Device is in Doze mode or App is in stand by mode.
                           Wake up the app or show an appropriate prompt for the user to take action
                           More information on this : https://github.com/AzureAD/azure-activedirectory-library-for-android/wiki/Handle-Doze-and-App-Standby */
                        Log.e(TAG, "Device is in doze mode or the app is in standby mode");
                    }
                    return;
                }
                /* Attempt an interactive on any other exception */
                mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO);
            }
        };
    }

    private void logHttpErrors(AuthenticationException authException){
        int httpResponseCode = authException.getServiceStatusCode();
        Log.d(TAG , "HTTP Response code: " + authException.getServiceStatusCode());
        if(httpResponseCode< 200 || httpResponseCode >300) {
            // logging http response headers in case of a http error.
            HashMap<String, List<String>> headers = authException.getHttpResponseHeaders();
            if (headers != null) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                    sb.append(entry.getKey());
                    sb.append(":");
                    sb.append(entry.getValue().toString());
                    sb.append("; ");
                }
                Log.e(TAG, "HTTP Response headers: " + sb.toString());
            }
        }
    }

    /* Callback used for interactive request.  If succeeds we use the access
     * token to call the Microsoft Graph. Does not check cache
     */
    private AuthenticationCallback<AuthenticationResult> getAuthInteractiveCallback() {
        return new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                if(authenticationResult==null || TextUtils.isEmpty(authenticationResult.getAccessToken())
                        || authenticationResult.getStatus()!= AuthenticationResult.AuthenticationStatus.Succeeded){
                    Log.e(TAG, "Authentication Result is invalid");
                    return;
                }
                /* Successfully got a token, call graph now */
                Log.d(TAG, "Successfully authenticated");
                Log.d(TAG, "ID Token: " + authenticationResult.getIdToken());

                /* Store the auth result */
                mAuthResult = authenticationResult;

                /* Store User id to SharedPreferences to use it to acquire token silently later */
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                preferences.edit().putString(USER_ID, authenticationResult.getUserInfo().getUserId()).apply();

                /* call graph */
                callGraphAPI();

                /* update the UI to post call graph state */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateSuccessUI();
                    }
                });
                /* set the sIntSignInInvoked boolean back to false  */
                sIntSignInInvoked.set(false);
            }

            @Override
            public void onError(Exception exception) {
                /* Failed to acquireToken */
                Log.e(TAG, "Authentication failed: " + exception.toString());
                if (exception instanceof AuthenticationException) {
                    ADALError  error = ((AuthenticationException)exception).getCode();
                    if(error==ADALError.AUTH_FAILED_CANCELLED){
                        Log.e(TAG, "The user cancelled the authorization request");
                    }else if(error== ADALError.AUTH_FAILED_NO_TOKEN){
                        // In this case ADAL has found a token in cache but failed to retrieve it.
                        // Retry interactive with Prompt.Always to ensure we do an interactive sign in
                        mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS);
                    }else if(error == ADALError.NO_NETWORK_CONNECTION_POWER_OPTIMIZATION){
                        /* Device is in Doze mode or App is in stand by mode.
                           Wake up the app or show an appropriate prompt for the user to take action
                           More information on this : https://github.com/AzureAD/azure-activedirectory-library-for-android/wiki/Handle-Doze-and-App-Standby */
                        Log.e(TAG, "Device is in doze mode or the app is in standby mode");
                    }
                }
                /* set the sIntSignInInvoked boolean back to false  */
                sIntSignInInvoked.set(false);
            }
        };
    }

}
