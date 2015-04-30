package com.github.lquiroli.menupager.sample;

import com.github.lquiroli.menupager.model.MenuItem;

import java.util.ArrayList;

/**
 * MenuItem
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class Book implements MenuItem {

    private String title;
    private ArrayList<Book> children;

    public Book() {
        children = new ArrayList<Book>();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Book> children) {
        this.children = children;
    }
}
