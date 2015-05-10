package com.github.lquiroli.menupager.widget;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Layout manager that allows to organize and interact with a hierarchical collection of data. The collection will be displayed in pages, each page consisted
 * by a {@link android.support.v4.app.Fragment}.
 * The data and structure of every new page will depend on the choices the user made in the previous ones.
 * <p/>
 * <p>Created by lorenzo.quiroli</p>
 */
public class MenuPager extends FrameLayout {


    private BaseMenuFragmentAdapter mAdapter;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private OnMenuPageChangeListener mOnMenuPageChangeListener;
    private OnMenuAdapterChangeListener mOnMenuAdapterChangeListener;
    private boolean isFirstLayout = true;
    /**
     * The current stack of choices the user made
     */
    private int[] mMenuStack;
    private int mCurrentPage;
    private Fragment mCurrentFragment;
    private boolean isAutoForward = true;

    public MenuPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMenuPager();
    }

    public MenuPager(Context context) {
        this(context, null);
    }

    void initMenuPager() {
        mCurrentPage = 0;
        mMenuStack = new int[0];
    }

    /**
     * Returns the index of the currently displayed page
     *
     * @return the page index
     */
    public int getCurrentPage() {
        return mCurrentPage;
    }

    /**
     * Returns the {@link android.support.v4.app.Fragment} of the currently displayed page
     *
     * @return the page Fragment
     */
    public Fragment getCurrentPageFragment() {
        return mCurrentFragment;
    }

    /**
     * Returns the current adapter
     *
     * @return the adapter
     */
    public BaseMenuFragmentAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * Set the adapter that will provide data to this MenuPager
     *
     * @param adapter the adapter instance
     */
    public void setAdapter(BaseMenuFragmentAdapter adapter) {

        BaseMenuFragmentAdapter oldAdapter = mAdapter;
        mAdapter = adapter;

        if (oldAdapter != null) {
            setMenuStack(new int[0]);
            if (mOnMenuAdapterChangeListener != null)
                mOnMenuAdapterChangeListener.onAdapterChanged(oldAdapter);
        }

    }

    /**
     * @return true if the MenuPager is currently auto forward, false otherwise
     */
    public boolean isAutoForward() {
        return isAutoForward;
    }

    /**
     * Choose if the MenuPager should automatically forward to the next page when an item is selected. Default is true
     *
     * @param autoForward true to enable auto forward, false to disable
     */
    public void setAutoForward(boolean autoForward) {
        isAutoForward = autoForward;
    }

    /**
     * Sets an instance of {@link com.github.lquiroli.menupager.widget.MenuPager.OnMenuItemClickListener} that will receive callbacks when an item
     * inside the page is clicked
     *
     * @param listener the listener
     */
    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mOnMenuItemClickListener = listener;
    }

    /**
     * Sets an instance of {@link com.github.lquiroli.menupager.widget.MenuPager.OnMenuPageChangeListener} that will receive callbacks when the current
     * page is changing
     *
     * @param listener the listener
     */
    public void setOnMenuPageChangeListener(OnMenuPageChangeListener listener) {
        mOnMenuPageChangeListener = listener;
    }

    /**
     * Sets an instance of {@link com.github.lquiroli.menupager.widget.MenuPager.OnMenuAdapterChangeListener} that will receive callback when the
     * adapter for this MenuPager is changed
     *
     * @param listener the listener
     */
    public void setOnMenuAdapterChangeListener(OnMenuAdapterChangeListener listener) {
        mOnMenuAdapterChangeListener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (isFirstLayout) {
            if (mAdapter != null) {
                setMenuStack(mMenuStack);
            }
        }

    }

    /**
     * Returns the current menu stack of this MenuPager. The stack is an array of int representing the choices the user has made to this point. Each choice
     * is the index of the item selected in the previous page.
     * <p>(Ex : assuming that the previous page was the first one and contained a list of 3 items, if the user selected the second item the index 1 will be
     * added to the menu stack at position 0. This means that the user selected the item indexed as 1 inside the page indexed as 0) </p>
     *
     * @return the current menu stack
     */
    public int[] getMenuStack() {
        return mMenuStack;
    }

    /**
     * Set a new menu stack. This new stack will replace the old one and the user will be brought to the last indexed page inside the new stack
     *
     * @param newStack the new menu stack
     */
    public void setMenuStack(int[] newStack) {
        setMenuStack(newStack, false);

    }

    /**
     * Set a new menu stack. This new stack will replace the old one and the user will be brought to the last indexed page inside the new stack
     *
     * @param newStack     the new menu stack
     * @param smoothScroll true to animate page change, false otherwise
     */
    public void setMenuStack(int[] newStack, boolean smoothScroll) {
        mMenuStack = newStack;
        mCurrentPage = mMenuStack.length;
        isFirstLayout = true;
        setCurrentPageInternal(mCurrentPage, smoothScroll);
        isFirstLayout = false;
    }

    void setCurrentPageInternal(int index, boolean smoothScroll) {

        if (mAdapter != null && ((index >= 0 && index != mCurrentPage) || isFirstLayout)) {
            if (!isFirstLayout && mOnMenuPageChangeListener != null) {
                mOnMenuPageChangeListener.onPageChange(mCurrentPage, index);
            }
            Fragment fragment = mAdapter.getPageInternal(index, mMenuStack);
            FragmentTransaction fTransaction = mAdapter.mFragmentManager.beginTransaction();
            if (smoothScroll) {
                int[] animations = new int[2];
                if (index < mCurrentPage) {
                    mAdapter.onBackwardAnimation(mCurrentPage, index, animations);
                } else {
                    mAdapter.onForwardAnimation(mCurrentPage, index, animations);
                }
                fTransaction.setCustomAnimations(animations[0], animations[1]);
            }
            fTransaction.replace(getId(), fragment);
            fTransaction.commit();

            if (mCurrentFragment != null)
                mAdapter.onDeletePage(mCurrentFragment);

            mCurrentPage = index;
            mCurrentFragment = fragment;

            if (!isFirstLayout && mOnMenuPageChangeListener != null) {
                mOnMenuPageChangeListener.onPageChanged(mCurrentPage);
            }
        }

    }

    /**
     * Moves the MenuPager one page back
     *
     * @return true if the MenuPager successfully moved back, false otherwise
     */
    public boolean moveBackward() {
        return moveBackward(true);
    }

    /**
     * Moves the MenuPager one page back
     *
     * @param smoothScroll true to animate page change, false otherwise
     * @return true if the MenuPager successfully moved back, false otherwise
     */
    public boolean moveBackward(boolean smoothScroll) {
        return moveBackward(mCurrentPage - 1, smoothScroll);
    }

    /**
     * Moves the MenuPager back to provided page index (the first page always represented with index 0)
     *
     * @param pageIndex    the index of the page the MenuPager should move back to
     * @param smoothScroll true to animate page change, false otherwise
     * @return true if the MenuPager successfully moved back, false otherwise
     */
    public boolean moveBackward(int pageIndex, boolean smoothScroll) {

        if (mMenuStack.length == 0 || pageIndex == mCurrentPage)
            return false;

        int index = mCurrentPage;
        while (index > pageIndex) {
            if (mMenuStack.length > 0) {
                int[] newStack = new int[mMenuStack.length - 1];
                System.arraycopy(mMenuStack, 0, newStack, 0, newStack.length);
                mMenuStack = newStack;
            }
            index--;
        }
        setCurrentPageInternal(pageIndex, smoothScroll);
        return true;

    }

    /**
     * Moves the MenuPager forward of one page registering the index of the selected item inside the menu stack
     *
     * @param selection the index of the item selected inside the current page
     */
    public void moveForward(int selection) {
        moveForward(selection, true);
    }

    /**
     * Moves the MenuPager forward of one page registering the index of the selected item inside the menu stack
     *
     * @param selection    the index of the item selected inside the current page
     * @param smoothScroll true to animate page change, false otherwise
     */
    public void moveForward(int selection, boolean smoothScroll) {
        int[] newStack = new int[mMenuStack.length + 1];
        System.arraycopy(mMenuStack, 0, newStack, 0, mMenuStack.length);
        newStack[newStack.length - 1] = selection;
        mMenuStack = newStack;
        setCurrentPageInternal(mMenuStack.length, smoothScroll);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        mMenuStack = ss.menuStack;
        mCurrentPage = ss.currentPage;

        setCurrentPageInternal(mCurrentPage, false);

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.menuStack = mMenuStack;
        ss.currentPage = mCurrentPage;
        return ss;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isFirstLayout = true;
    }

    @Override
    public void addView(View child) {
        checkHierarchy(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        checkHierarchy(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        checkHierarchy(child);
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        checkHierarchy(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        checkHierarchy(child);
        super.addView(child, width, height);
    }

    void checkHierarchy(View child) {

        int childLimit = 0;
        boolean isPreHoneycomb = false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            childLimit = 1;
            isPreHoneycomb = true;
        }

        if (child != null) {
            //Check child count
            if (getChildCount() > childLimit) {
                throw new IllegalStateException("MenuPager can only host one child");
            }

            //Check child type
            if (isPreHoneycomb) {
                if ((!(child instanceof RecyclerView) && getChildCount() == childLimit)
                        ||
                        (!(child instanceof FrameLayout) && getChildCount() == 0)) {
                    throw new IllegalStateException("MenuPager only child must be instance of " + RecyclerView.class.getName());
                }
            } else {
                if (!(child instanceof RecyclerView)) {
                    throw new IllegalStateException("MenuPager only child must be instance of " + RecyclerView.class.getName());
                }
            }

            if (child instanceof RecyclerView) {
                RecyclerView.Adapter adapter = ((RecyclerView) child).getAdapter();
                if (adapter != null && !(adapter instanceof Adapter)) {
                    throw new IllegalStateException("RecyclerView adapter must be instance of " + MenuPager.Adapter.class.getName());
                }
            }


        }

    }

    /**
     * Interface definition for a callback to be invoked when an item inside a {@link com.github.lquiroli.menupager.widget.MenuPager} is clicked
     */
    public interface OnMenuItemClickListener {
        public void onMenuItemClick(View itemView, Object item, int index);
    }

    /**
     * Interface definition for a callback to be invoked when the current page of a {@link com.github.lquiroli.menupager.widget.MenuPager} is changing
     */
    public interface OnMenuPageChangeListener {
        public void onPageChange(int oldPage, int newPage);

        public void onPageChanged(int newPage);
    }

    /**
     * Interface definition for a callback to be invoked when the adapter of a {@link com.github.lquiroli.menupager.widget.MenuPager} is changed
     */
    public interface OnMenuAdapterChangeListener {
        public void onAdapterChanged(BaseMenuFragmentAdapter oldAdapter);
    }

    public static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR
                = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        });
        ClassLoader loader;
        int[] menuStack;
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in, ClassLoader loader) {
            super(in);
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            menuStack = in.createIntArray();
            currentPage = in.readInt();
            this.loader = loader;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeIntArray(menuStack);
            out.writeInt(currentPage);
        }

    }


    public static abstract class Adapter<VH extends MenuPager.ViewHolder> extends RecyclerView.Adapter<VH> {

        private RecyclerView mRecyclerView;

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            mRecyclerView = recyclerView;
        }

        @Override
        public void onViewAttachedToWindow(VH holder) {
            super.onViewAttachedToWindow(holder);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    MenuPager menuPager = null;

                    //In pre-honeycomb a layer of NoSaveStateFrameLayout is added by support library. In case, go up with parent for 2 levels
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        menuPager = (MenuPager) mRecyclerView.getParent();
                    } else {
                        menuPager = (MenuPager) mRecyclerView.getParent().getParent();
                    }

                    final int index = mRecyclerView.getChildAdapterPosition(v);
                    Object obj = menuPager.mAdapter.getItem(index, menuPager.mCurrentPage, menuPager.mMenuStack);

                    if (menuPager.isAutoForward) {
                        boolean hasChildren = ReflectUtils.reflectCollection(obj).size() > 0;
                        if (hasChildren)
                            menuPager.moveForward(index);
                    }

                    if (menuPager.mOnMenuItemClickListener != null) {
                        menuPager.mOnMenuItemClickListener.onMenuItemClick(v, obj, index);
                    }
                }
            });
        }

    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
