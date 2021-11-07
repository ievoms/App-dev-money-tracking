package com.example.app_dev_money_tracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class IconAdapter extends BaseAdapter {
    Context context;
    int[] icons;
    LayoutInflater inflater;
    public IconAdapter(Context context, int[] pictures) {
        this.context = context;
        this.icons = pictures;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.icon_layout, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.categoryIcon);
        imageView.setImageResource(icons[i]);
        return view;
    }
}