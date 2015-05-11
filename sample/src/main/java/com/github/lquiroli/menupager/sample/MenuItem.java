package com.github.lquiroli.menupager.sample;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.lquiroli.menupager.annotation.Collection;
import com.github.lquiroli.menupager.annotation.Label;

import java.util.ArrayList;

/**
 * Class that defines an item inside the menu. Since we are going to extend {@link com.github.lquiroli.menupager.widget.SimpleMenuFragmentAdapter}
 * and {@link com.github.lquiroli.menupager.widget.SimpleMenuRecyclerAdapter} as our adapters,
 * we decorate the fields of this class with correct annotations
 * <p/>
 * <p>Created by lorenzo.quiroli</p>
 */
public class MenuItem implements Parcelable {

    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public MenuItem createFromParcel(Parcel source) {
            return new MenuItem(source);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };
    /**
     * The ArrayList entries represents our collection, so we use the notation {@link com.github.lquiroli.menupager.annotation.Collection}
     */
    @Collection
    private ArrayList<MenuItem> entries;
    /**
     * The field label is the one we want to display inside each menu item, so we use the notation {@link com.github.lquiroli.menupager.annotation.Label}
     */
    @Label
    private String label;

    public MenuItem() {
        entries = new ArrayList<MenuItem>();
    }

    public MenuItem(Parcel in) {
        label = in.readString();
        in.readList(entries, MenuItem.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(label);
        out.writeList(entries);
    }
}
