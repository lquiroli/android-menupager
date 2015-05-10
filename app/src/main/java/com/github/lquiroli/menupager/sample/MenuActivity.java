package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.lquiroli.menupager.widget.MenuPager;

import java.util.ArrayList;

/**
 * SimpleMenuActivity
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class MenuActivity extends AppCompatActivity {

    private MenuPager mMenuPager;
    private MyFragmentAdapter menuAdapter;
    private ArrayList<MenuItem> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_menu);

        generateMenu();

        mMenuPager = (MenuPager) findViewById(R.id.menu_pager);
        menuAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mItems);
        mMenuPager.setAdapter(menuAdapter);

    }

    @Override
    public void onBackPressed() {

        if (!mMenuPager.moveBackward()) {
            super.onBackPressed();
        }

    }

    private void generateMenu() {

        mItems = new ArrayList<MenuItem>();

        for (int count = 1; count < 5; count++) {
            MenuItem entry = new MenuItem();
            entry.setLabel("Shop " + count);

            for (int c = 1; c < 8; c++) {
                MenuItem subEntry = new MenuItem();
                subEntry.setLabel("Shop " + count + "-" + c);

                for (int cc = 1; cc < 6; cc++) {
                    MenuItem subSubEntry = new MenuItem();
                    subSubEntry.setLabel("Shop " + count + "-" + c + "-" + cc);
                    subEntry.getEntries().add(subSubEntry);
                }

                entry.getEntries().add(subEntry);
            }

            mItems.add(entry);
        }

    }

}
