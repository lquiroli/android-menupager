package com.github.lquiroli.menupager.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that provides basic adapter behaviour. Extend this class or {@link com.github.lquiroli.menupager.widget.SimpleMenuFragmentAdapter}  to create your own implementation and provide it to an instance of
 * {@link com.github.lquiroli.menupager.widget.MenuPager}
 * <p>Created by lorenzo.quiroli</p>
 */
public abstract class BaseMenuFragmentAdapter implements Serializable {

    FragmentManager mFragmentManager;
    private ArrayList mItems;

    public BaseMenuFragmentAdapter(FragmentManager fm, ArrayList items) {
        mItems = items;
        mFragmentManager = fm;
    }

    /**
     * This method cycles through the provided user data and determines the collection for each menu page. Generally you don't need to override this method.
     * To customize the lookup of data from the collection it should be enough to override {@link #getItemCollection(Object)} method
     *
     * @param pageIndex the index of the page for which calculate the collection
     * @param menuStack the menu stack of choices
     * @return
     */
    public abstract ArrayList determinePageCollection(int pageIndex, int[] menuStack);

    Fragment getPageInternal(int pageIndex, int[] menuStack) {
        return getPage(pageIndex, determinePageCollection(pageIndex, menuStack));
    }

    /**
     * Returns the collection contained by the provided item. Override this method if you want to customize how the data is retrieved
     * from the provided object
     *
     * @param item the item
     * @return the collection
     */
    public abstract ArrayList getItemCollection(Object item);

    /**
     * Returns the instance of {@link android.support.v4.app.Fragment} that represents this menu page
     *
     * @param pageIndex the index of the page
     * @param data      the collection of data to build the page
     * @return the page's Fragment
     */
    protected abstract Fragment getPage(int pageIndex, ArrayList data);

    /**
     * Callback invoked when the adapter is deleting a page {@link android.support.v4.app.Fragment} from the stack
     *
     * @param page the instance of page being deleted
     */
    protected abstract void onDeletePage(Fragment page);

    /**
     * Callback method invoked when the MenuPager is about to move forward and switch pages. Override this method to customize the in and out animation
     * of the involved pages
     *
     * @param oldPageIndex the index of the old page
     * @param newPageIndex the index of the new page being displayed
     * @param animations   an array of 2 items. Fill this array at positions 0 and 1 with the resource id of your chosen animations
     */
    protected abstract void onForwardAnimation(int oldPageIndex, int newPageIndex, int[] animations);

    /**
     * Callback method invoked when the MenuPager is about to move backward and switch pages. Override this method to customize the in and out animation
     * of the involved pages
     *
     * @param oldPageIndex the index of the old page
     * @param newPageIndex the index of the new page being displayed
     * @param animations   an array of 2 items. Fill this array at positions 0 and 1 with the resource id of your chosen animations
     */
    protected abstract void onBackwardAnimation(int oldPageIndex, int newPageIndex, int[] animations);

    /**
     * Method that returns a specific item contained in the menu collection. The item research is based on the provided item index, page index and menu stack
     *
     * @param itemIndex the index of the requested item inside the page
     * @param pageIndex the index of the page that contains the item
     * @param menuStack the menu stack to perform the research
     * @return the item
     */
    public abstract Object getItem(int itemIndex, int pageIndex, int[] menuStack);

}

