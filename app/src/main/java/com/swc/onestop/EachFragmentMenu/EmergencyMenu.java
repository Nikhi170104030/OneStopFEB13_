package com.swc.onestop.EachFragmentMenu;

import android.graphics.drawable.Drawable;

public class EmergencyMenu {
    private String nameOfConcerned;
    private String phoneOfConcerned;
    private Drawable d;



    public EmergencyMenu(String nameOfConcerned, String phoneOfConcerned,Drawable d) {
        this.nameOfConcerned = nameOfConcerned;
        this.phoneOfConcerned = phoneOfConcerned;
        this.d = d;

    }

    public EmergencyMenu() {

    }

    public void setD(Drawable d){
        this.d = d;
    }

    public Drawable getD() {
        return d;
    }

    public String getNameOfConcerned() {
        return nameOfConcerned;
    }

    public void setNameOfConcerned(String nameOfConcerned) {
        this.nameOfConcerned = nameOfConcerned;
    }

    public String getPhoneOfConcerned() {
        return phoneOfConcerned;
    }

    public void setPhoneOfConcerned(String phoneOfConcerned) {
        this.phoneOfConcerned = phoneOfConcerned;
    }
}
