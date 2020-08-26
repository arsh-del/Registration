package com.example.registration;

public class User {
    private Double Location;
   // private Double longitude;

    public User() {
    }

    public User(Double Location ) {
        this.Location = Location;
      //  this.longitude = longitude;
    }

    public Double getLocation() {
        return Location;
    }

    public void setLocation(Double location) {
        Location = location;
    }
}