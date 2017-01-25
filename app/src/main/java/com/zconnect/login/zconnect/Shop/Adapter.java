package com.zconnect.login.zconnect.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zconnect.login.zconnect.R;

/**
 * Created by f390 on 21/1/17.
 */

public class Adapter extends BaseAdapter {
    private Context context;
    private String[] names, Url;

    public Adapter(Context context, String[] Url, String[] names) {
        this.context = context;
        this.Url = Url;
        this.names = names;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.gv_item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.GV_imageView);
            // Picasso.with(context).load(Url[position]).into(imageView);
            TextView textView = (TextView) gridView
                    .findViewById(R.id.GV_name);
            textView.setText(names[position]);


        } else {
            gridView = convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}

