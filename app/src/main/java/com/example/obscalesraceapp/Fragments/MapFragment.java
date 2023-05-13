package com.example.obscalesraceapp.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.obscalesraceapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapFragment extends Fragment {

    private SupportMapFragment supportMapFragment;
    //initialize marker options
    private MarkerOptions markerOptions = new MarkerOptions();
    private GoogleMap googleMap;
    private LatLng latLng;

    public MapFragment(LatLng latLng){
        this.latLng = latLng;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //initialze map fragment
        supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.google_map);
        //LatLng latLng = new LatLng(/* 31.955100735125836, 34.803954184130575*/);
        asyncMap(latLng);

        return view;
    }

    public void asyncMap(LatLng latLng){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap1) {
                //when map is loaded
                googleMap = googleMap1;


                //set current position of marker
                markerOptions.position(latLng);

                //set title of marker
                markerOptions.title("I Am Here");

                //remove all markers
                googleMap.clear();

                //animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        latLng, 10
                ));
                //add marker on map
                googleMap.addMarker(markerOptions);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        //when clicked on map
                        setNewPosition(latLng);

                    }
                });
            }
        });
    }

    public void zoomToMarker(LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    public void setNewPosition(LatLng latLng){
        //set position of marker
        markerOptions.position(latLng);

        //set title of marker
        markerOptions.title("I Am Here");

        //remove all markers
        googleMap.clear();

        //animating to zoom the marker
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                latLng, 10
        ));
        //add marker on map
        googleMap.addMarker(markerOptions);
    }
}

