package com.swc.onestop.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ColorSpace;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.kml.KmlLayer;
import com.swc.onestop.Models.SampleModel;
import com.swc.onestop.R;
import com.swc.onestop.directionhelpers.FetchURL;
import com.swc.onestop.directionhelpers.TaskLoadedCallback;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,TaskLoadedCallback {

    private GoogleMap mMap;
    private BottomSheetBehavior sheetBehavior;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    SupportMapFragment mapFragment;
    Marker prevMarker;
    MarkerOptions place,mylocation;
    LatLng latLng;
    String kmlurl;
    TextView Place_name;
    private Polyline currentPolyline;
    ProgressDialog progressDialog;
    Button naviagte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        naviagte = findViewById(R.id.navigate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,Main2Activity.class));
            }
        });


        LinearLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        kmlurl = "https://swciitg.github.io/OnestopKMLfile/IITG_Map.kml";

        Place_name = (TextView) findViewById(R.id.place_name);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Map ...");
        progressDialog.show();
        place = new MarkerOptions().position(new LatLng(26.196499, 91.686506)).title("IITG");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(MapsActivity.this, "Search...",
                        "What are you looking for...?", null, createSampleData(),
                        new SearchResultListener<SampleModel>() {
                            @Override
                            public void onSelected(
                                    BaseSearchDialogCompat dialog,
                                    SampleModel item, int position
                            ) {
                                switch (item.getTitle()) {
                                    case "IITG Main Gate" :
                                        place = new MarkerOptions().position(new LatLng(26.196499, 91.686506)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        // new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Food Court" :
                                        place = new MarkerOptions().position(new LatLng(26.192541, 91.699330)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Guest House" :
                                        place = new MarkerOptions().position(new LatLng(26.195710, 91.695624)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Khoka Market" :
                                        place = new MarkerOptions().position(new LatLng(26.185640, 91.701119)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Lecture Hall" :
                                        place = new MarkerOptions().position(new LatLng(26.189096, 91.691463)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 1" :
                                        place = new MarkerOptions().position(new LatLng(26.187741, 91.691554)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 2" :
                                        place = new MarkerOptions().position(new LatLng(26.186639, 91.691438)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 3" :
                                        place = new MarkerOptions().position(new LatLng(26.185691, 91.691667)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 4" :
                                        place = new MarkerOptions().position(new LatLng(26.184709, 91.691506)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 5" :
                                        place = new MarkerOptions().position(new LatLng(26.185943, 91.689639)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Barak Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.189322, 91.702116)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Brahmaputra Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.186446, 91.699090)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Dhansiri Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.194722, 91.698750)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Dibang Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.187844, 91.696210)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Dihing Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.187741, 91.699769)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Kameng Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.190761, 91.701596)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Kapili Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.188183, 91.696801)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Lohit Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.189208, 91.698097)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Manas Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.189011, 91.700122)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Siang Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.189593, 91.696919)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Subhansiri Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.192705, 91.694660)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Umiam Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.188891, 91.702085)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Married Scholar Hostel" :
                                        place = new MarkerOptions().position(new LatLng(26.195008, 91.698706)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Manas Community Hall" :
                                        place = new MarkerOptions().position(new LatLng(26.189332, 91.699875)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "BioTech Park" :
                                        place = new MarkerOptions().position(new LatLng(26.192258, 91.702055)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "New Sac" :
                                        place = new MarkerOptions().position(new LatLng(26.192538, 91.698939)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Serpentine Lake" :
                                        place = new MarkerOptions().position(new LatLng(26.197200, 91.694685)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Market Complex" :
                                        place = new MarkerOptions().position(new LatLng(26.195385, 91.687867)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Bhupen Hazarika Auditorium" :
                                        place = new MarkerOptions().position(new LatLng(26.190716, 91.693035)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Conference Hall" :
                                        place = new MarkerOptions().position(new LatLng(26.191042, 91.692442)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Administration Building" :
                                        place = new MarkerOptions().position(new LatLng(26.190219, 91.692075)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "SBI Bank" :
                                        place = new MarkerOptions().position(new LatLng(26.189951, 91.692199)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Canara Bank" :
                                        place = new MarkerOptions().position(new LatLng(26.189951, 91.692199)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "SBI ATM" :
                                        place = new MarkerOptions().position(new LatLng(26.189951, 91.692199)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "ICICI ATM (Market Complex)" :
                                        place = new MarkerOptions().position(new LatLng(26.195234, 91.687853)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "ICICI Bank" :
                                        place = new MarkerOptions().position(new LatLng(26.195234, 91.687853)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Canara ATM (Admin Building)" :
                                        place = new MarkerOptions().position(new LatLng(26.189951, 91.692199)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Canara ATM (New Sac)" :
                                        place = new MarkerOptions().position(new LatLng(26.192596, 91.699077)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Cafe Coffee Day" :
                                        place = new MarkerOptions().position(new LatLng(26.189539, 91.692622)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Library" :
                                        place = new MarkerOptions().position(new LatLng(26.189208, 91.692983)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Mechanical Engineering Workshop" :
                                        place = new MarkerOptions().position(new LatLng(26.187002, 91.689652)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Civil Engineering Laboratory" :
                                        place = new MarkerOptions().position(new LatLng(26.184643, 91.693183)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "KV School" :
                                        place = new MarkerOptions().position(new LatLng(26.184421, 91.696521)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Vinaigrette Canteen" :
                                        place = new MarkerOptions().position(new LatLng(26.185995, 91.692945)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Post Office" :
                                        place = new MarkerOptions().position(new LatLng(26.183675, 91.696030)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Hospital" :
                                        place = new MarkerOptions().position(new LatLng(26.196559, 91.697391)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "B-type Community Hall" :
                                        place = new MarkerOptions().position(new LatLng(26.199895, 91.694849)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "D-type Community Hall" :
                                        place = new MarkerOptions().position(new LatLng(26.196399, 91.690818)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Viewpoint" :
                                        place = new MarkerOptions().position(new LatLng(26.194434, 91.692335)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Subhansiri Busstop" :
                                        place = new MarkerOptions().position(new LatLng(26.191525, 91.694211)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Computer Centre (CC)" :
                                        place = new MarkerOptions().position(new LatLng(26.189218, 91.693136)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Old Sac" :
                                        place = new MarkerOptions().position(new LatLng(26.192825, 91.695846)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "KV Gate" :
                                        place = new MarkerOptions().position(new LatLng(26.183292, 91.696130)).title(item.getTitle());
                                        Place_name.setText(item.getTitle());
                                        //mMap.clear();
                                        mMap.addMarker(place);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(place.getPosition()));
                                        //new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;



                                }
                                dialog.dismiss();
                            }
                        }
                );
                dialog.show();
                dialog.getSearchBox().setTypeface(Typeface.SERIF);
            }
        });


        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        sheetBehavior.setHideable(false);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        sheetBehavior.setHideable(false);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        naviagte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AlphaAnimation buttonClick = new AlphaAnimation(2F, 0.8F);
                v.startAnimation(buttonClick);
                String latlng =  place.getPosition().toString();
                latlng = latlng.replace("lat/lng: (","");
                latlng = latlng.replace(")","");

               // Log.i("Positions","!"+latlng);
                //Toast.makeText(MapsActivity.this, latlng, Toast.LENGTH_SHORT).show();

                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?&daddr="+latlng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }
    private class DownloadKml extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKml(String url) {
            mUrl = url;
        }

        protected byte[] doInBackground(String... params) {
            try {
                InputStream is =  new URL(mUrl).openStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(byte[] byteArr) {
            try {

                KmlLayer kmlLayerD1 = new KmlLayer(mMap, new ByteArrayInputStream(byteArr),
                        getApplicationContext());

                if(kmlLayerD1.isLayerOnMap()){
                    kmlLayerD1.removeLayerFromMap();
                }

                Toast.makeText(MapsActivity.this, "KML added", Toast.LENGTH_SHORT).show();

                kmlLayerD1.addLayerToMap();
                progressDialog.dismiss();
                kmlLayerD1.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
                    @Override
                    public void onFeatureClick(Feature feature) {

                    }


                });


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.newcustom_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button retry = dialog.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isOnline()){
                    new DownloadKml(kmlurl).execute();
                }
                else {
                    dialog.show();
                }
            }
        });
        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivity.this, "Connect to the Internet to load your map completely.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });


        dialog.show();
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    private ArrayList<SampleModel> createSampleData() {
        ArrayList<SampleModel> items = new ArrayList<>();
        items.add(new SampleModel("IITG Main Gate"));
        items.add(new SampleModel("Food Court"));
        items.add(new SampleModel("New Guest House"));
        items.add(new SampleModel("Old Guest House"));
        items.add(new SampleModel("Khoka Market"));
        items.add(new SampleModel("Lecture Hall"));
        items.add(new SampleModel("Core 1"));
        items.add(new SampleModel("Core 2"));
        items.add(new SampleModel("Core 3"));
        items.add(new SampleModel("Core 4"));
        items.add(new SampleModel("Core 5"));
        items.add(new SampleModel("Barak Hostel"));
        items.add(new SampleModel("Brahmaputra Hostel"));
        items.add(new SampleModel("Dhansiri Hostel"));
        items.add(new SampleModel("Dibang Hostel"));
        items.add(new SampleModel("Dihing Hostel"));
        items.add(new SampleModel("Kameng Hostel"));
        items.add(new SampleModel("Kapili Hostel"));
        items.add(new SampleModel("Lohit Hostel"));
        items.add(new SampleModel("Manas Hostel"));
        items.add(new SampleModel("Siang Hostel"));
        items.add(new SampleModel("Subhansiri Hostel"));
        items.add(new SampleModel("Umiam Hostel"));
        items.add(new SampleModel("Married Scholar Hostel"));
        items.add(new SampleModel("Manas Community Hall"));
        items.add(new SampleModel("BioTech Park"));
        items.add(new SampleModel("New Sac"));
        items.add(new SampleModel("Serpentine Lake"));
        items.add(new SampleModel("Market Complex"));
        items.add(new SampleModel("Bhupen Hazarika Auditorium"));
        items.add(new SampleModel("Conference Hall"));
        items.add(new SampleModel("Administration Building"));
        items.add(new SampleModel("SBI Bank"));
        items.add(new SampleModel("ICICI Bank"));
        items.add(new SampleModel("Canara Bank"));
        items.add(new SampleModel("SBI ATM"));
        items.add(new SampleModel("ICICI ATM (Market Complex)"));
        items.add(new SampleModel("Canara ATM (Admin Building)"));
        items.add(new SampleModel("Canara ATM (New Sac)"));
        items.add(new SampleModel("Cafe Coffee Day"));
        items.add(new SampleModel("Library"));
        items.add(new SampleModel("Mechanical Engineering Workshop"));
        items.add(new SampleModel("Civil Engineering Laboratory"));
        items.add(new SampleModel("KV School"));
        items.add(new SampleModel("Vinaigrette Canteen"));
        items.add(new SampleModel("Post Office"));
        items.add(new SampleModel("Hospital"));
        items.add(new SampleModel("B-type Community Hall"));
        items.add(new SampleModel("D-type Community Hall"));
        items.add(new SampleModel("Viewpoint"));
        items.add(new SampleModel("Computer Centre (CC)"));
        items.add(new SampleModel("Old Sac"));
        items.add(new SampleModel("KV Gate"));
        items.add(new SampleModel("Subhansiri Busstop"));

        Collections.sort(items, new Comparator<SampleModel>() {
            @Override
            public int compare(SampleModel lhs, SampleModel rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });





        return items;
    }



    private class DownloadKmlFileDayOne extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKmlFileDayOne(String url) {
            mUrl = url;
        }

        protected byte[] doInBackground(String... params) {


            try {
                InputStream is =  new URL(mUrl).openStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(byte[] byteArr) {
            try {

                KmlLayer kmlLayerD1 = new KmlLayer(mMap, new ByteArrayInputStream(byteArr), MapsActivity.this.getApplicationContext());


                // kmlLayerD1.addLayerToMap();


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        if(isOnline()) {
            new DownloadKml(kmlurl).execute();
        }
        else {
            progressDialog.dismiss();
            showDialog(MapsActivity.this);
        }




//        // Setting a click event handler for the map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//                // Clears the previously touched position
//                Place_name.setText(item.getTitle());
        //mMap.clear();
//
//                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
//            }
//        });




//get latlong for corners for specified place
        LatLng one = new LatLng(26.201932, 91.684568);
        LatLng two = new LatLng(26.183738, 91.705171);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);


    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude

            latLng = new LatLng(location.getLatitude(), location.getLongitude());

            mylocation = new MarkerOptions().position(latLng).title("My Location");
            mMap.addMarker(mylocation);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mylocation = new MarkerOptions().position(latLng).title("Place");
        mMap.addMarker(mylocation);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();

            Geocoder geocoder = new Geocoder(MapsActivity.this.getApplicationContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        //mCurrLocationMarker = mMap.addMarker(markerOptions);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }


    }

}
