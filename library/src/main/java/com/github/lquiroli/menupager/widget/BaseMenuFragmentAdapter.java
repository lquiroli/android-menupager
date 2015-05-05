package com.github.lquiroli.menupager.widget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.lquiroli.menupager.R;
import com.github.lquiroli.menupager.annotation.Children;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MenuPagerFragmentAdapter
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public abstract class BaseMenuFragmentAdapter {

    private static final String TAG = BaseMenuFragmentAdapter.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private int mCurrentPage = 0;
    private MenuPager mMenuPager;
    private ArrayList<Integer> mPageSelections;
    private List mItems;
    private int mBackStackStep = 0;
    private FragmentManager.OnBackStackChangedListener mOnBackStackChangeListenerInternal = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if (mBackStackStep > mFragmentManager.getBackStackEntryCount()) {
                //User went back
                mCurrentPage--;
                mPageSelections.remove(mPageSelections.size() - 1);
            }
            mBackStackStep = mFragmentManager.getBackStackEntryCount();
        }
    };

    public BaseMenuFragmentAdapter(FragmentManager fm, List items) {
        mItems = items;
        mFragmentManager = fm;
        mFragmentManager.addOnBackStackChangedListener(mOnBackStackChangeListenerInternal);
        mPageSelections = new ArrayList<Integer>();
    }

    private final MenuFragment getFragmentItem() {

        List pageItems = onCalculateFragmentData(mCurrentPage, mPageSelections, mItems);
        MenuFragment fragment = new MenuFragment();
        fragment.mAdapter = this;
        fragment.mData = pageItems;
        return fragment;

    }

    private List onCalculateFragmentData(int pageIndex, List<Integer> pageSelections, List topLevelData) {

        List pageItems = topLevelData;
        Log.d(TAG, "Page : " + pageIndex);
        Log.d(TAG, "PageSelections : " + pageSelections.size());
        for (int count = 0; count < pageIndex; count++) {
            Log.d(TAG, "Count : " + count);
            try {
                Object obj = pageItems.get(pageSelections.get(count));
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Annotation[] annotations = field.getDeclaredAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Children) {
                            //Found data, check type
                            Log.d(TAG, "Found children");
                            if (field.getType().newInstance() instanceof Collection) {
                                field.setAccessible(true);

                                pageItems = (List) field.get(obj);
                                Log.d(TAG, "Children size : " + pageItems.size());
                                field.setAccessible(false);
                            }

                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return pageItems;

    }

    protected void onViewReady(MenuPager menuPager) {

        mMenuPager = menuPager;
        mFragmentManager.beginTransaction().add(menuPager.getId(), getFragmentItem()).commit();
    }

    protected int[] onChangePageAnimation(int oldPageIndex, int newPageIndex) {

        int[] animations = new int[4];
        animations[0] = R.anim.menu_pager_current_in;
        animations[1] = R.anim.menu_pager_current_out;
        animations[2] = R.anim.menu_pager_back_in;
        animations[3] = R.anim.menu_pager_back_out;

        return animations;

    }

    public void moveForward(int selection) {

        mPageSelections.add(selection);
        mCurrentPage++;
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        int[] animations = onChangePageAnimation(mCurrentPage - 1, mCurrentPage);

        switch (animations.length) {
            case 2:
                fTransaction.setCustomAnimations(animations[0], animations[1]);
                break;
            case 4:
                fTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
                break;

        }
        fTransaction.replace(mMenuPager.getId(), getFragmentItem());
        fTransaction.addToBackStack(null).commit();
    }

    public void moveBackward() {
        mFragmentManager.popBackStackImmediate();
    }

    protected abstract RecyclerView onCreateView(int pageIndex, List data, MenuPager parent);

    public int getCurrentPageIndex() {
        return mCurrentPage;
    }

    public MenuFragment getCurrentPage() {
        return null;//TODO
    }

    MenuPager getMenuPager() {
        return mMenuPager;
    }
}

