package com.example.ZzusRide;

/**
 * Created by winto on 8/6/2017.
 */

public class RidePost
{
    private String text;
    private String name;
    private String photoUrl;
    private String LONGITUDE;
    private String LATITUDE;

    public RidePost() {
    }

    public RidePost(String text, String name, String photoUrl, String LONGITUDE, String LATITUDE)
    {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }
}
