package com.inventory.liker.Model;

import androidx.annotation.NonNull;

public class Location {
    String location;

    public Location() {
    }

    public Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return "Location [location=" + location +"]";
    }
}
