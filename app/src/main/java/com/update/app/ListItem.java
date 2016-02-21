package com.update.app;

import android.media.tv.TvContract;

/**
 * Created by Windows on 5/2/2016.
 */
public class ListItem {

    private String Name;
    private String StartDate;
    private String EndDate;
    private String Id;
    private String HighLight;
    private String ImageUrl;
    private String LogoUrl;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;

    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;

    }
    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }
    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String LogoUrl) {
        this.LogoUrl = LogoUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getHighLight() {
        return HighLight;
    }

    public void setHighLight(String HighLight) {
        this.HighLight = HighLight;
    }

    @Override
    public String toString() {
        return "";
    }
}
