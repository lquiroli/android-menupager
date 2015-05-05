package com.github.lquiroli.menupager.sample;

import com.github.lquiroli.menupager.annotation.Children;

import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 30/04/2015.
 */
public class BookClassification extends BookElement {

    @Children
    private ArrayList<BookElement> classifications;

    public BookClassification() {
        classifications = new ArrayList<BookElement>();
    }

    public ArrayList<BookElement> getClassifications() {
        return classifications;
    }

    public void setClassifications(ArrayList<BookElement> classifications) {
        this.classifications = classifications;
    }
}
