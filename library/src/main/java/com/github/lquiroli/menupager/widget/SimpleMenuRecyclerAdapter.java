package com.github.lquiroli.menupager.widget;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lquiroli.menupager.R;
import com.github.lquiroli.menupager.annotation.Label;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * MenuRecyclerAdapter
 * Created by lorenzo.quiroli on 30/04/2015.
 */
public class SimpleMenuRecyclerAdapter extends MenuPager.Adapter<SimpleMenuRecyclerAdapter.ViewHolder> {

    private List mData;
    private View.OnClickListener mListener;

    public SimpleMenuRecyclerAdapter(List data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int i) {

        LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_menu_item, parent, false);
        ViewHolder vh = new ViewHolder(tv);

        return vh;

    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (mListener != null)
            holder.itemView.setOnClickListener(mListener);
    }

    void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.label.setText(reflectLabel(mData.get(i)));
        viewHolder.arrow.setVisibility(View.VISIBLE);

    }

    private final String reflectLabel(Object obj) {
        Log.d(SimpleMenuRecyclerAdapter.class.getSimpleName(), "ReflectLabel");
        try {
            ArrayList<Field> totFields = new ArrayList<Field>();
            for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
                Log.d(SimpleMenuRecyclerAdapter.class.getSimpleName(), "Class : " + c);
                Field[] fields = c.getDeclaredFields();
                Log.d(SimpleMenuRecyclerAdapter.class.getSimpleName(), "Fields size : " + fields.length);
                for (Field field : fields) {
                    totFields.add(field);
                }
            }
            //TODO break cycle when found
            for (Field field : totFields) {
                Log.d(SimpleMenuRecyclerAdapter.class.getSimpleName(), "Fields : " + field.getName());
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Label) {
                        //Found label
                        field.setAccessible(true);
                        String label = (String) field.get(obj);
                        field.setAccessible(false);
                        return label;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";

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
