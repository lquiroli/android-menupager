package com.github.lquiroli.menupager.sample;

import com.github.lquiroli.menupager.annotation.Children;
import com.github.lquiroli.menupager.annotation.Label;

import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 05/05/2015.
 */
public class ShopEntry {

    @Children
    private ArrayList<ShopEntry> entries = new ArrayList<ShopEntry>();
    @Label
    private String label;

    public ArrayList<ShopEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<ShopEntry> entries) {
        this.entries = entries;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
