package com.github.lquiroli.menupager.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.lquiroli.menupager.widget.MenuPager;

import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 05/05/2015.
 */
public class ShopAdapter extends MenuPager.Adapter<ShopAdapter.ViewHolder> {

    private ArrayList<ShopEntry> mData;

    public ShopAdapter(ArrayList<ShopEntry> data) {
        super(data);
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.text.setText(mData.get(i).getLabel());
        if (mData.get(i).getEntries().size() == 0) {
            viewHolder.check.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends MenuPager.ViewHolder {

        private TextView text;
        private CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.menu_label);
            check = (CheckBox) itemView.findViewById(R.id.menu_check);
        }
    }

}
