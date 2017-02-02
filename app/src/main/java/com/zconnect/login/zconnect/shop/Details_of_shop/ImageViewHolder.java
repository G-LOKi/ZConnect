package com.zconnect.login.zconnect.shop.Details_of_shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zconnect.login.zconnect.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {


    View mView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setProductTitle(String productDesc) {

        TextView post_desc = (TextView) mView.findViewById(R.id.TitleOfImageWalaView);
        post_desc.setText(productDesc);

    }

    public void setProductDesc(String productDesc) {

        TextView post_desc = (TextView) mView.findViewById(R.id.TextOfImageWalaView);
        post_desc.setText(productDesc);

    }

    public void setImage(Context ctx, String image) {


        ImageView post_image = (ImageView) mView.findViewById(R.id.ImageOfImageWalaView);
        Picasso.with(ctx).load(image).into(post_image);


    }

}


