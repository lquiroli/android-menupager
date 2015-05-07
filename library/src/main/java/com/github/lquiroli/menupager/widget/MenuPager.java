package com.github.lquiroli.menupager.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v7.app.AppCompatActivity;
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

    public MenuPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuPager(Context context) {
        super(context);
    }

    public void setAdapter(BaseMenuFragmentAdapter adapter) {
        //TODO manage adapter change
        Log.d(MenuPager.class.getSimpleName(), "setAdapter");
        Log.d(MenuPager.class.getSimpleName(), "mAdapter : " + mAdapter);
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
                mAdapter.onViewReady(this);
            }
        }

        mIsFirstLayout = false;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        Log.d(MenuPager.class.getSimpleName(), "onRestoreInstanceState");

        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Log.d("MenuPager", "Restoring : ");
        for (int n : ss.pageSelections) {
            Log.d("MenuPager", "PageSel : " + n);
        }

        if (mAdapter != null) {
            mAdapter.mPageSelections = ss.pageSelections;
        }

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().remove(mAdapter.mCurrentFragment).commit();
        ((AppCompatActivity) getContext()).getSupportFragmentManager().executePendingTransactions();
        if (mAdapter != null) {
            ss.pageSelections = mAdapter.mPageSelections;
        }
        return ss;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIsFirstLayout = true;
       /* if (mAdapter != null) {
            mAdapter.onViewReady(this);
        }*/

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

        public SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in, ClassLoader loader) {
            super(in);
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            pageSelections = in.createIntArray();
            this.loader = loader;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeIntArray(pageSelections);
        }

    }

    public static abstract class Adapter<VH extends MenuPager.ViewHolder> extends RecyclerView.Adapter<VH> {

        MenuPager menuPagerInternal;
        BaseMenuFragmentAdapter adapterInternal;
        private RecyclerView mRecyclerView;
        private List mData;

        public Adapter(List data) {
            mData = data;
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
                    Object obj = adapterInternal.getItem(index);
                    boolean hasChildren = adapterInternal.hasChildren(obj);
                    if (hasChildren)
                        adapterInternal.moveForward(index);

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
