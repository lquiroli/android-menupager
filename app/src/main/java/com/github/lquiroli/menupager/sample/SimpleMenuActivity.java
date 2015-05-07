package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.github.lquiroli.menupager.widget.MenuPager;
import com.github.lquiroli.menupager.widget.SimpleMenuAdapter;

/**
 * SimpleMenuActivity
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class SimpleMenuActivity extends AppCompatActivity implements MenuPager.OnMenuItemClickListener {

    private MenuPager mMenuPager;
    private Shop menu;
    private SimpleMenuAdapter menuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_menu);

        Log.d(SimpleMenuActivity.class.getSimpleName(), "" + getSupportFragmentManager());

        generateMenu();

        mMenuPager = (MenuPager) findViewById(R.id.menu_pager);
        menuAdapter = new MyFragmentAdapter(getSupportFragmentManager(), menu.getEntries());
        mMenuPager.setAdapter(menuAdapter);
        mMenuPager.setOnMenuItemClickListener(this);

        MenuPager mMenuPager2 = (MenuPager) findViewById(R.id.menu_pager2);
        SimpleMenuAdapter menuAdapter2 = new MyFragmentAdapter(getSupportFragmentManager(), menu.getEntries());
        mMenuPager2.setAdapter(menuAdapter2);
        mMenuPager2.setOnMenuItemClickListener(this);

    }

    @Override
    public void onBackPressed() {

        if (!menuAdapter.moveBackward()) {
            super.onBackPressed();
        }

    }

    private void generateMenu() {

        menu = new Shop();

        for (int count = 1; count < 5; count++) {
            ShopEntry entry = new ShopEntry();
            entry.setLabel("Shop " + count);

            for (int c = 1; c < 8; c++) {
                ShopEntry subEntry = new ShopEntry();
                subEntry.setLabel("Shop " + count + "-" + c);

                for (int cc = 1; cc < 6; cc++) {
                    ShopEntry subSubEntry = new ShopEntry();
                    subSubEntry.setLabel("Shop " + count + "-" + c + "-" + cc);
                    subEntry.getEntries().add(subSubEntry);
                }

                entry.getEntries().add(subEntry);
            }

            menu.getEntries().add(entry);
        }

    }

    @Override
    public void onMenuItemClick(View itemView, Object item) {

        ShopEntry entry = (ShopEntry) item;

        CheckBox ch = ((CheckBox) itemView.findViewById(R.id.menu_check));
        ch.setChecked(!ch.isChecked());
    }
}
