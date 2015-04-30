package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.lquiroli.menupager.widget.MenuPager;
import com.github.lquiroli.menupager.widget.SimpleMenuAdapter;

/**
 * SimpleMenuActivity
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class SimpleMenuActivity extends AppCompatActivity {

    private MenuPager mMenuPager;
    private BooksMenu menu;
    private SimpleMenuAdapter menuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_menu);

        generateMenu();

        mMenuPager = (MenuPager) findViewById(R.id.menu_pager);
        menuAdapter = new SimpleMenuAdapter(getSupportFragmentManager(), menu.getMenuItems());
        mMenuPager.setAdapter(menuAdapter);

    }

    private void generateMenu() {

        menu = new BooksMenu();

        //Generate menu
        for (int count = 1; count <= 3; count++) {
            Book item = new Book();
            item.setTitle("Item " + count);
            Log.d("MenuActivity", item.getTitle());

            for (int subcount = 1; subcount <= 4; subcount++) {
                Book subItem = new Book();
                subItem.setTitle("Item " + count + "-" + subcount);
                item.getChildren().add(subItem);
            }

            menu.getMenuItems().add(item);
        }

    }

}
