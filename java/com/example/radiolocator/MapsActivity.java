package com.example.radiolocator;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final UiSettings uiSettings = mMap.getUiSettings();
        if(checkPermission())
            mMap.setMyLocationEnabled(true);
        else askPermission();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onMapLongClick(final LatLng latLng) {
                mMap.clear();
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("https://maps.googleapis.com/")
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();
                ElevationApi elevationApi = restAdapter.create(ElevationApi.class);
                elevationApi.getElev(latLng.latitude + "," + latLng.longitude, "AIzaSyBA2A8LKVMg0IwR67AlSXTx8ox1NluaFrE", new Callback<Object>() {
                    @Override
                    public void success(Object object, Response response) {
                        result = "success";
                        Log.w("res", object.toString());
                        ObjectMapper mapper = new ObjectMapper();
                        String json = "";
                        try {
                            json = mapper.writeValueAsString(object);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            JSONObject jsonElevation = jsonArray.getJSONObject(0);
                            result = jsonElevation.getString("elevation");
                            Log.w("res", jsonElevation.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                                .draggable(true)
                                .title(getIntent().getExtras().get("mark").toString()+" "+ getIntent().getExtras().get("model").toString())
                                .snippet("Висота: " + result));
                        marker.showInfoWindow();
                        double radius = 4.12*(Math.sqrt(Double.parseDouble(result)/1000) + Math.sqrt(0.0035)) * 1000;
                        Log.w("radius", String.valueOf(radius));
                        mMap.addCircle(new CircleOptions().center(marker.getPosition())
                                .radius(radius * 1.3)
                                .fillColor(Color.parseColor("#55FF0000"))
                                .visible(true))
                                .setStrokeWidth(5);
                        mMap.addCircle(new CircleOptions().center(marker.getPosition())
                                .radius(radius * 1.2)
                                .fillColor(Color.parseColor("#55FFFF00"))
                                .visible(true))
                                .setStrokeWidth(5);
                        mMap.addCircle(new CircleOptions().center(marker.getPosition())
                                .radius(radius)
                                .fillColor(Color.parseColor("#5500FF00"))
                                .visible(true))
                                .setStrokeWidth(5);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        result = error.getCause().toString();
                        Log.w("error", error.toString());
                    }
                });

            }
        });
    }

    private boolean checkPermission() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PackageManager.GET_PERMISSIONS
        );
    }
}
