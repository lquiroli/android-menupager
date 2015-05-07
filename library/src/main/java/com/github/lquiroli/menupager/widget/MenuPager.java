package com.github.lquiroli.menupager.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 * MenuPager
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class MenuPager extends FrameLayout {

    private BaseMenuFragmentAdapter mAdapter;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private boolean mIsFirstLayout = true;
    private MenuFragment mCurrentFragment;
    private int[] mPageSelections;
    private int mCurrentPage;//TODO save to parcel

    public MenuPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurrentPage = 0;
        mPageSelections = new int[0];
    }

    public MenuPager(Context context) {
        this(context, null);
    }

    public void setAdapter(BaseMenuFragmentAdapter adapter) {
        //TODO manage adapter change
        mAdapter = adapter;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mOnMenuItemClickListener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mIsFirstLayout) {
            if (mAdapter != null) {
                setCurrentPage(mCurrentPage);
            }
        }

        mIsFirstLayout = false;
    }

    public void setCurrentPage(int index) {

        if (mAdapter != null && ((index >= 0 && index != mCurrentPage) || mIsFirstLayout)) {
            //Valid request, check direction
            MenuFragment fragment = mAdapter.getFragmentItem(index, mPageSelections);
            fragment.mMenuPager = this;
            fragment.mPageIndex = index;
            mCurrentFragment = fragment;
            FragmentTransaction fTransaction = mAdapter.mFragmentManager.beginTransaction();
            int[] animations = new int[2];
            if (index < mCurrentPage) {
                mAdapter.onBackwardAnimation(index, mCurrentPage, animations);
            } else {
                mAdapter.onForwardAnimation(mPageSelections.length - 1, mPageSelections.length, animations);
            }
            fTransaction.setCustomAnimations(animations[0], animations[1]);
            fTransaction.replace(getId(), fragment);
            fTransaction.commit();
            mCurrentPage = index;
        }

    }

    public boolean moveBackward() {

        if (mPageSelections.length > 0) {
            int[] newSelections = new int[mPageSelections.length - 1];
            System.arraycopy(mPageSelections, 0, newSelections, 0, newSelections.length);
            mPageSelections = newSelections;
            setCurrentPage(mPageSelections.length);
            return true;
        }
        return false;

    }

    public void moveForward(int selection) {

        //Add new selection
        int[] newSelections = new int[mPageSelections.length + 1];
        System.arraycopy(mPageSelections, 0, newSelections, 0, mPageSelections.length);
        newSelections[newSelections.length - 1] = selection;
        mPageSelections = newSelections;
        setCurrentPage(mPageSelections.length);

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        Log.d(MenuPager.class.getSimpleName(), "onRestoreInstanceState. Isfirst : " + mIsFirstLayout);

        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        mPageSelections = ss.pageSelections;
        mCurrentPage = ss.currentPage;

        setCurrentPage(mCurrentPage);

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d(MenuPager.class.getSimpleName(), "onSaveInstance. Isfirst : " + mIsFirstLayout);
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.pageSelections = mPageSelections;
        ss.currentPage = mCurrentPage;
        return ss;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIsFirstLayout = true;

    }

    public interface OnMenuItemClickListener {
        public void onMenuItemClick(View itemView, Object item);
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
        int[] pageSelections;
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in, ClassLoader loader) {
            super(in);
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            pageSelections = in.createIntArray();
            currentPage = in.readInt();
            this.loader = loader;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeIntArray(pageSelections);
            out.writeInt(currentPage);
        }

    }

    public static abstract class Adapter<VH extends MenuPager.ViewHolder> extends RecyclerView.Adapter<VH> {

        MenuPager menuPagerInternal;
        BaseMenuFragmentAdapter adapterInternal;
        private RecyclerView mRecyclerView;

        public Adapter(List data) {
        }

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

                    int index = mRecyclerView.getChildAdapterPosition(v);
                    Object obj = adapterInternal.getItem(index, menuPagerInternal.mCurrentPage, menuPagerInternal.mPageSelections);
                    boolean hasChildren = adapterInternal.hasChildren(obj);
                    if (hasChildren)
                        menuPagerInternal.moveForward(index);

                    if (menuPagerInternal.mOnMenuItemClickListener != null) {
                        menuPagerInternal.mOnMenuItemClickListener.onMenuItemClick(v, obj);
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
