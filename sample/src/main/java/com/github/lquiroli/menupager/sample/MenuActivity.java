package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.lquiroli.menupager.widget.MenuPager;

import java.util.ArrayList;

/**
 * A simple activity that displays a {@link com.github.lquiroli.menupager.widget.MenuPager}.
 * <p>Created by lorenzo.quiroli</p>
 */
public class MenuActivity extends AppCompatActivity implements MenuPager.OnMenuItemClickListener {

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
        //We listen for item click events
        mMenuPager.setOnMenuItemClickListener(this);

    }

    @Override
    public void onBackPressed() {

        //When the back button is pressed, we check if the MenuPager can scroll back
        if (!mMenuPager.moveBackward()) {
            super.onBackPressed();
        }

    }

    private void generateMenu() {

        //We generate the recursive collection of the menu

        mItems = new ArrayList<MenuItem>();

        for (int count = 1; count < 5; count++) {
            MenuItem entry = new MenuItem();
            entry.setLabel("Item " + count);

            for (int c = 1; c < 8; c++) {
                MenuItem subEntry = new MenuItem();
                subEntry.setLabel("Item " + count + "-" + c);

                for (int cc = 1; cc < 6; cc++) {
                    MenuItem subSubEntry = new MenuItem();
                    subSubEntry.setLabel("Item " + count + "-" + c + "-" + cc);
                    subEntry.getEntries().add(subSubEntry);
                }

                entry.getEntries().add(subEntry);
            }

            mItems.add(entry);
        }

    }

    @Override
    public void onMenuItemClick(View itemView, Object item, int index) {

        //If we reached the max depth of a menu branch, show the selected item value
        if (((MenuItem) item).getEntries().size() == 0) {
            Toast.makeText(this, "Clicked item " + ((MenuItem) item).getLabel(), Toast.LENGTH_SHORT).show();
        }

    }
}
