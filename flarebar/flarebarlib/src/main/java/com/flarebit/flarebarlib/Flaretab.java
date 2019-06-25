package com.flarebit.flarebarlib;
/*
 * Created by Vaibhav Patel on 20-06-2019.
 */

import android.graphics.drawable.Drawable;

public class Flaretab {

    private Drawable tabImage;
    private String tabText;
    private String tabColorString;
    private String badge;

    public Flaretab(Drawable tabImage, String tabText, String tabColorString) {
        this.tabImage = tabImage;
        this.tabText = tabText;
        this.tabColorString = tabColorString;
    }
    public Flaretab(Drawable tabImage, String tabText, String tabColorString,String badge) {
        this.tabImage = tabImage;
        this.tabText = tabText;
        this.tabColorString = tabColorString;
        this.badge = badge;
    }

    public Drawable getTabImage() {
        return tabImage;
    }

    public void setTabImage(Drawable tabImage) {
        this.tabImage = tabImage;
    }

    public String getTabText() {
        return tabText;
    }

    public void setTabText(String tabText) {
        this.tabText = tabText;
    }

    public String getTabColorString() {
        return tabColorString;
    }

    public void setTabColorString(String tabColorString) {
        this.tabColorString = tabColorString;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public boolean isBadgeGiven(){
        if(badge != null && badge.length() > 0){
            return true;
        }
        return false;
    }
}
