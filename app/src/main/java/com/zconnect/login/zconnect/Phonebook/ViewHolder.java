package com.zconnect.login.zconnect.Phonebook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zconnect.login.zconnect.R;

class ViewHolder extends RecyclerView.ViewHolder

{
    View mView;

    public ViewHolder(View itemView) {

        super(itemView);
        mView = itemView;


    }

    public void destroy() {
        View v1 = mView.findViewById(R.id.textNumber);
        ((ViewGroup) v1.getParent()).removeView(v1);
        v1.layout(0, 0, 0, 0);

        View v2 = mView.findViewById(R.id.textName);
        ((ViewGroup) v2.getParent()).removeView(v2);
    }

    public void setTitle(String title) {
        TextView postTitle = (TextView) mView.findViewById(R.id.textName);
        postTitle.setText(title);

    }

    public void setAddress(String description) {
        TextView postDesc = (TextView) mView.findViewById(R.id.textNumber);
        postDesc.setText(description);

    }
}

