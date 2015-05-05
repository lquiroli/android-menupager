package com.github.lquiroli.menupager.sample;

import com.github.lquiroli.menupager.annotation.Label;

/**
 * Created by lorenzo.quiroli on 30/04/2015.
 */
public abstract class BookElement {

    @Label
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
