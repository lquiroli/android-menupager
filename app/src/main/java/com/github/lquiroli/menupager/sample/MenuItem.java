package com.github.lquiroli.menupager.sample;

import com.github.lquiroli.menupager.annotation.Collection;
import com.github.lquiroli.menupager.annotation.Label;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 05/05/2015.
 */
public class MenuItem implements Serializable {

    @Collection
    private ArrayList<MenuItem> entries = new ArrayList<MenuItem>();
    @Label
    private String label;

    public ArrayList<MenuItem> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<MenuItem> entries) {
        this.entries = entries;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
