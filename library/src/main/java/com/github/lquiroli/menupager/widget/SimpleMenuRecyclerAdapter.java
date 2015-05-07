package com.github.lquiroli.menupager.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lquiroli.menupager.R;

import java.util.List;

/**
 * MenuRecyclerAdapter
 * Created by lorenzo.quiroli on 30/04/2015.
 */
public final class SimpleMenuRecyclerAdapter extends MenuPager.Adapter<SimpleMenuRecyclerAdapter.ViewHolder> {

    private List mData;

    public SimpleMenuRecyclerAdapter(List data) {
        super(data);
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int i) {

        LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_menu_item, parent, false);
        ViewHolder vh = new ViewHolder(tv);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Object obj = mData.get(i);

        viewHolder.label.setText(ReflectUtils.reflectLabel(obj));
        viewHolder.arrow.setVisibility(adapterInternal.hasChildren(obj) ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends MenuPager.ViewHolder {

        private TextView label;
        private ImageView arrow;

        public ViewHolder(LinearLayout itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.menu_item_label);
            arrow = (ImageView) itemView.findViewById(R.id.menu_item_next);
        }
    }

}
