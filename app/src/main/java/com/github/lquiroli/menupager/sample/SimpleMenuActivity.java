package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.lquiroli.menupager.widget.MenuPager;
import com.github.lquiroli.menupager.widget.SimpleMenuAdapter;

/**
 * SimpleMenuActivity
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class SimpleMenuActivity extends AppCompatActivity {

    private MenuPager mMenuPager;
    private Library menu;
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

        menu = new Library();

        BookClassification topClassification = new BookClassification();
        topClassification.setName("Genre");
        BookClassification childClassification = new BookClassification();
        childClassification.setName("Fantasy");
        Book book = new Book();
        book.setName("Eragon");
        childClassification.getClassifications().add(book);
        book = new Book();
        book.setName("Lord of the rings");
        childClassification.getClassifications().add(book);
        book = new Book();
        book.setName("A game of thrones");
        childClassification.getClassifications().add(book);
        topClassification.getClassifications().add(childClassification);
        childClassification = new BookClassification();
        childClassification.setName("Horror");
        BookClassification childchildclassification = new BookClassification();
        childchildclassification.setName("Psychological");
        book = new Book();
        book.setName("IT");
        childchildclassification.getClassifications().add(book);
        book = new Book();
        book.setName("Shining");
        childchildclassification.getClassifications().add(book);
        childClassification.getClassifications().add(childchildclassification);
        topClassification.getClassifications().add(childClassification);
        childClassification = new BookClassification();
        childClassification.setName("Comic");
        topClassification.getClassifications().add(childClassification);
        childClassification = new BookClassification();
        childClassification.setName("Drama");
        topClassification.getClassifications().add(childClassification);
        menu.getMenuItems().add(topClassification);

        topClassification = new BookClassification();
        topClassification.setName("Age");
        childClassification = new BookClassification();
        childClassification.setName("Mature");
        topClassification.getClassifications().add(childClassification);
        childClassification = new BookClassification();
        childClassification.setName("Baby");
        topClassification.getClassifications().add(childClassification);
        childClassification = new BookClassification();
        childClassification.setName("Teenager");
        topClassification.getClassifications().add(childClassification);
        menu.getMenuItems().add(topClassification);

        //Generate menu
        /*for (int count = 1; count <= 3; count++) {
            Book item = new Book();
            item.setTitle("Item " + count);
            Log.d("MenuActivity", item.getTitle());

            for (int subcount = 1; subcount <= 4; subcount++) {
                Book subItem = new Book();
                subItem.setTitle("Item " + count + "-" + subcount);

                for(int subsubcount=1;subsubcount<=3;subsubcount++){
                    Book subsubItem = new Book();
                    subsubItem.setTitle("Item " + count + "-" + subcount + "-"+subsubcount);
                    subItem.getChildren().add(subsubItem);
                }

                item.getChildren().add(subItem);
            }

            menu.getMenuItems().add(item);
        }*/

    }

}
