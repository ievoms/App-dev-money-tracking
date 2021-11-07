package com.example.app_dev_money_tracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {
    Context context;
    int[] icons;
    LayoutInflater inflater;
    public MyAdapter(CreateCategory createCategory, int[] pictures) {
        this.context = createCategory;
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
            view = inflater.inflate(R.layout.iconssampleview, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.single_icon_id);
        imageView.setImageResource(icons[i]);
        return view;
    }
}
