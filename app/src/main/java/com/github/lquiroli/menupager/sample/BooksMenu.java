package com.github.lquiroli.menupager.sample;

import com.github.lquiroli.menupager.model.Menu;

import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class BooksMenu implements Menu {

    private ArrayList<Book> menuItems;

    public BooksMenu() {
        menuItems = new ArrayList<Book>();
    }

    public ArrayList<Book> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<Book> menuItems) {
        this.menuItems = menuItems;
    }

}
