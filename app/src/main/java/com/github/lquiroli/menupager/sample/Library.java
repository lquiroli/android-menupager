package com.github.lquiroli.menupager.sample;

import java.util.ArrayList;

/**
 * Library
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class Library {

    private ArrayList<BookElement> menuItems;

    public Library() {
        menuItems = new ArrayList<BookElement>();
    }

    public ArrayList<BookElement> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<BookElement> menuItems) {
        this.menuItems = menuItems;
    }

}
