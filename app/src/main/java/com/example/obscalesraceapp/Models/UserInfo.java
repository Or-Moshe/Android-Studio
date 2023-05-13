package com.example.obscalesraceapp.Models;

import com.google.android.gms.maps.model.LatLng;

public class UserInfo {

    private String name;
    private int icon;
    private LatLng latLng;

    public UserInfo(String name, int icon, LatLng latLng) {
        this.name = name;
        this.icon = icon;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
