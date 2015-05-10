package com.github.lquiroli.menupager.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple implementation of a {@link com.github.lquiroli.menupager.widget.MenuPager.Adapter}. This adapter renders simple list items and fills them with
 * the appropriate label
 * <p/>
 * <p>This class assumes that you decorated your collection with the correct annotations to work properly. Please see {@link com.github.lquiroli.menupager.annotation.Collection}
 * and {@link com.github.lquiroli.menupager.annotation.Label} for further details</p>
 * <p>Created by lorenzo.quiroli</p>
 */
public final class SimpleMenuRecyclerAdapter extends MenuPager.Adapter<SimpleMenuRecyclerAdapter.ViewHolder> {

    private ArrayList mData;

    public SimpleMenuRecyclerAdapter(ArrayList data) {
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        ((TextView) viewHolder.textView).setText(ReflectUtils.reflectLabel(mData.get(i)));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends MenuPager.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

}
