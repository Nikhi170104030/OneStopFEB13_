package com.swc.onestop.EachFragmentMenu;

import android.graphics.drawable.Drawable;

public class FragmentMenu {
    private Drawable draw;
    private String nameOfConcerned;
    private String statusOfConcerned;
    private String typeOfData;


    public FragmentMenu(Drawable drawable, String nameOfConcerned, String statusOfConcerned, String typeOfData) {
        this.draw = drawable;
        this.nameOfConcerned = nameOfConcerned;
        this.statusOfConcerned = statusOfConcerned;
        this.typeOfData = typeOfData;

    }

    public FragmentMenu() {

    }

    public Drawable getDraw() {
        return draw;
    }

    public void setDraw(Drawable drawable) {
        this.draw = drawable;
    }

    public String getNameOfConcerned() {
        return nameOfConcerned;
    }

    public void setNameOfConcerned(String nameOfConcerned) {
        this.nameOfConcerned = nameOfConcerned;
    }

    public String getStatusOfConcerned() {
        return statusOfConcerned;
    }

    public void setStatusOfConcerned(String statusOfConcerned) {
        this.statusOfConcerned = statusOfConcerned;
    }
    public String getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(String typeOfData) {
        this.typeOfData = typeOfData;
    }
}
