package com.github.lquiroli.menupager.sample;

import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 05/05/2015.
 */
public class Shop {

    private ArrayList<ShopEntry> entries = new ArrayList<>();

    public ArrayList<ShopEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<ShopEntry> entries) {
        this.entries = entries;
    }
}
