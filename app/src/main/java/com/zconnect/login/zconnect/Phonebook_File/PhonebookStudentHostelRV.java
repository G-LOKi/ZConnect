package com.zconnect.login.zconnect.Phonebook_File;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zconnect.login.zconnect.R;

import java.util.Vector;

/**
 * Created by shubhamk on 6/2/17.
 */

public class PhonebookStudentHostelRV extends RecyclerView.Adapter<PhonebookStudentHostelRV.ViewHolder> {
    Context context;
    Vector<PhonebookStudentHostelItem> phonebookStudentHostelItems;

    public PhonebookStudentHostelRV(Vector<PhonebookStudentHostelItem> phonebookStudentHostelItems, Context context) {
        this.phonebookStudentHostelItems = phonebookStudentHostelItems;
        this.context = context;
    }

    @Override
    public PhonebookStudentHostelRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.student_hostel_list_item_format, parent, false);

        // Return a new holder instance
        return new PhonebookStudentHostelRV.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(PhonebookStudentHostelRV.ViewHolder holder, int position) {
        holder.hostel.setText(phonebookStudentHostelItems.get(position).getHostel());
    }

    @Override
    public int getItemCount() {
        return phonebookStudentHostelItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView hostel;

        public ViewHolder(View itemView) {
            super(itemView);
            hostel = (TextView) itemView.findViewById(R.id.student_hostel_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PhonebookHostelWise.class);
                    intent.putExtra("Hostel", phonebookStudentHostelItems.get(getAdapterPosition()).getHostel());
                    context.startActivity(intent);
                    if (context instanceof PhonebookHostelWise) {
                        ((PhonebookHostelWise) context).finish();
                    }
                }
            });

        }
    }
}