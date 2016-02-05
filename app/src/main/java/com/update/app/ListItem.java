package com.update.app;

/**
 * Created by Windows on 5/2/2016.
 */
public class ListItem {

    private String title,subtitle;
    private String date;
    private String imageurl,iconurl;

    public String getImageUrl() {
        return imageurl;
    }

    public void setImageUrl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubTitle() {
        return subtitle;
    }

    public void setSubTitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getIconUrl() {
        return iconurl;
    }

    public void setIconUrl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "";
    }
}
