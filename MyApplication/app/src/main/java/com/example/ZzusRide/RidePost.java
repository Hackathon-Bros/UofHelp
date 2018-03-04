package com.example.ZzusRide;

/**
 * Created by winto on 8/6/2017.
 */

public class RidePost
{
    private String text;
    private String name;
    private String photoUrl;
    private int LONGITUDE;
    private int LATITUDE;

    public RidePost()
    {
    }

    public RidePost(String text, String name, String photoUrl, int LONGITUDE, int LATITUDE)
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

    public int getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(int LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public int getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(int LATITUDE) {
        this.LATITUDE = LATITUDE;
    }
}
