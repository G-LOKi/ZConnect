package com.zconnect.login.zconnect.Phonebook_File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zconnect.login.zconnect.R;

import java.util.Vector;

/**
 * Created by shubhamk on 2/1/17.
 */

public class PhonebookAdapter extends RecyclerView.Adapter<PhonebookAdapter.ViewHolder> {
    Context context;
    Vector<PhonebookItem> phonebookItem;

    public PhonebookAdapter(Vector<PhonebookItem> phonebookItem, Context context) {
        this.phonebookItem = phonebookItem;
        this.context = context;
    }

    @Override
    public PhonebookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.phonebook_item_format, parent, false);

        // Return a new holder instance
        return new PhonebookAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(PhonebookAdapter.ViewHolder holder, int position) {
        holder.simpleDraweeView.setImageURI(Uri.parse(phonebookItem.get(position).getImgurl()));
        holder.name.setText(phonebookItem.get(position).getName());
        holder.number.setText(phonebookItem.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return phonebookItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView name, number;

        public ViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.phonebook_item_format_image);
            name = (TextView) itemView.findViewById(R.id.phonebook_name1);
            number = (TextView) itemView.findViewById(R.id.phonebook_number1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PhonebookDetails.class);

                    PhonebookDisplayItem phonebookDisplayItem;
                    phonebookDisplayItem = phonebookItem.get(getAdapterPosition()).getPhonebookDisplayItem();
                    intent.putExtra("desc", phonebookDisplayItem.getDesc());
                    intent.putExtra("name", phonebookDisplayItem.getName());
                    intent.putExtra("number", phonebookDisplayItem.getNumber());
                    intent.putExtra("image", phonebookDisplayItem.getImageurl());
                    intent.putExtra("email", phonebookDisplayItem.getEmail());
                    context.startActivity(intent);
                    if (context instanceof PhonebookDetails) {
                        ((PhonebookDetails) context).finish();
                    }
                }
            });


        }
    }
}
