package com.zconnect.login.zconnect.Shop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zconnect.login.zconnect.R;

public class shopListViewHolder extends RecyclerView.ViewHolder

{
    View mView;

    public shopListViewHolder(View itemView) {

        super(itemView);
        mView = itemView;


    }


    public void setTitle(String title) {
        TextView postTitle = (TextView) mView.findViewById(R.id.Title_store);
        postTitle.setText(title);

    }

    public void setAddress(String description) {
        TextView postDesc = (TextView) mView.findViewById(R.id.Address_store);
        postDesc.setText(description);

    }

    public void setPhn(final String description, final Context context) {
        TextView postDesc = (TextView) mView.findViewById(R.id.Phone_No);
        postDesc.setText(description);
        postDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + description)));
            }
        });

    }

    public void setImage(Context context, String url) {
        ImageView imageView = (ImageView) mView.findViewById(R.id.StoreImage);
        Picasso.with(context).load(url).into(imageView);


    }

    public void makeButton(final double lat, final double lon, final Context context) {
        Button getDirection = (Button) mView.findViewById(R.id.Direction);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lon));
                context.startActivity(intent);
            }
        });


    }
}