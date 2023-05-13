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

    private FusedLocationProviderClient client;
    private Location currentLocation;
    SupportMapFragment supportMapFragment;
    //initialize marker options
    MarkerOptions markerOptions = new MarkerOptions();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //initialze map fragment
        supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        asyncMap();
/*
        //check permission
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission grated
            //call method
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        asyncMap();
                    }
                }
            });
        }*/

        return view;
    }

    private void asyncMap(){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                //when map is loaded
                LatLng latLng = new LatLng(/*currentLocation.getLatitude(), currentLocation.getLongitude()*/ 31.955100735125836, 34.803954184130575);



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
                        setNewPosition(googleMap, latLng);

                    }
                });
            }
        });
    }

    public void setNewPosition(GoogleMap googleMap, LatLng latLng){
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

