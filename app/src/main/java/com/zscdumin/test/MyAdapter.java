package com.zscdumin.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    private int[] images;
    private String[] titles;
    private Context context;
    private int resource;

    public MyAdapter(Context context, int resource, int[] images, String[] titles) {
        this.images = images;
        this.resource = resource;
        this.titles = titles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(resource, null);
            viewHolder.image = view.findViewById(R.id.imageView);
            viewHolder.title = view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.image.setImageResource(images[i]);
        viewHolder.title.setText(titles[i]);
        return view;
    }

    final class ViewHolder {
        ImageView image;
        TextView title;
    }
}
