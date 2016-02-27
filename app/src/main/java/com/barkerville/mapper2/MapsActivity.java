package com.barkerville.mapper2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.PolylineOptions;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks,
                        OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest mLocationRequest = new LocationRequest();
        int permissionCheck = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.
                ACCESS_FINE_LOCATION);
        {
            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
        }
        showLocation(mCurrentLocation);
    }

    protected void showLocation(Location mCurrentLocation) {


        if (mCurrentLocation != null) {
            Log.i("Where am I?", "Latitude: " + mCurrentLocation.getLatitude() + ", Longitude:" + mCurrentLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 19));
            if (mLastLocation != null) {
                mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
                                .add(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                );
            }
            mLastLocation = mCurrentLocation;
        }
    }

    protected void startLocationUpdates() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public void onLocationChanged(Location mCurrentLocation) {

    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        int permissionCheck = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.
                ACCESS_FINE_LOCATION);
        {
            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
                mMap.setMyLocationEnabled(true);

        }     showLocation(mCurrentLocation);
              startLocationUpdates();


        // Add a marker in Sydney and move the camera
        /* LatLng sydney = new LatLng(-34, 151);
        LatLng thinkfulHq = new LatLng(40.72493,-73.996599);

        Marker thinkfulMarker = mMap.addMarker(new MarkerOptions()
                                .position(thinkfulHq)
                                .title("Thinkful Headquarters")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.thinkful))
                                .snippet("On a mission to reinvent education")
        );
        thinkfulMarker.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thinkfulHq, 12));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){

            mMap.animateCamera(CameraUpdateFactory.zoomTo(19),2000,null);
        }
    }, 2000);
    }

































*/
    }
}