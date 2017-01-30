package com.zconnect.login.zconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.zconnect.login.zconnect.Phonebook_File.PhonebookDetails;
import com.zconnect.login.zconnect.Phonebook_File.PhonebookDisplayItem;

import java.util.Calendar;

class everythingViewHolder extends RecyclerView.ViewHolder

{
    View mView;

    public everythingViewHolder(View itemView) {

        super(itemView);
        mView = itemView;


    }

    public void removeView() {
        RelativeLayout mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.ContactCardEverything);
        mRelativeLayout.setVisibility(View.GONE);
    }

    public void setTitle(String title) {

        TextView postTitle = (TextView) mView.findViewById(R.id.title_everything);
        postTitle.setText(title);

    }

    public void setDescription(String description) {


        TextView postDesc = (TextView) mView.findViewById(R.id.description_or_price);
        postDesc.setText(description);
    }

    public void setDate(final String title, boolean isNumber, final Context context) {
        TextView setDate = (TextView) mView.findViewById(R.id.date_event);
        setDate.setText(title);
        if (isNumber)

        {
            setDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(String.valueOf(Uri.parse("tel:" + title.trim()))).setType(Intent.ACTION_DIAL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }
        Button makeCall = (Button) mView.findViewById(R.id.reminder_or_call);
        makeCall.setText("CALL");
        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(String.valueOf(Uri.parse("tel:" + title.trim()))).setType(Intent.ACTION_DIAL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });
    }

    public void setTime(String title) {


        TextView setTime = (TextView) mView.findViewById(R.id.time);
        setTime.setText(title);

    }

    public void setImage(Context context, String url) {


        ImageView imageView = (ImageView) mView.findViewById(R.id.Image_everything);
        Picasso.with(context).load(url).into(imageView);

    }

    public void makeButton(final String title, final String desc, final long time) {
        Button set_reminder = (Button) mView.findViewById(R.id.reminder_or_call);
        set_reminder.setText("SET REMINDER");
        set_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addReminderInCalendar(title, desc, time, mView.getContext());

            }
        });
    }

    private void addReminderInCalendar(String title, String desc, long time_gaph, Context context) {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis() + time_gaph);
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=DAILY");
        intent.putExtra("endTime", cal.getTimeInMillis() + time_gaph + 60 * 60 * 1000);
        intent.putExtra("title", title);
        intent.putExtra("description", desc);
        context.startActivity(intent);

        // Display event id.
        //Toast.makeText(getAppli
        //cationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

        /** Adding reminder for event added. *
         }

         /** Returns Calendar Base URI, supports both new and old OS. */


    }

    public void makeContactView(final Context context, final PhonebookDisplayItem phonebookDisplayItem) {

        LinearLayout mlayout = (LinearLayout) mView.findViewById(R.id.EventsAndDescriptionEverything);
        mlayout.setVisibility(View.GONE);
        RelativeLayout mRel = (RelativeLayout) mView.findViewById(R.id.ContactCardEverything);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) mView.findViewById(R.id.everything_item_format_image);
        simpleDraweeView.setImageURI(Uri.parse(phonebookDisplayItem.getImageurl()));
        TextView name = (TextView) mView.findViewById(R.id.everything_name1);
        name.setText(phonebookDisplayItem.getName());
        Toast.makeText(context, phonebookDisplayItem.getName(), Toast.LENGTH_LONG).show();
        TextView number = (TextView) mView.findViewById(R.id.everything_number1);
        number.setText(phonebookDisplayItem.getNumber());
        mRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhonebookDetails.class);
                //intent.putExtra("Workshop", workshopItemformat1);
                intent.putExtra("desc", phonebookDisplayItem.getDesc());
                intent.putExtra("name", phonebookDisplayItem.getName());
                intent.putExtra("number", phonebookDisplayItem.getNumber());
                intent.putExtra("image", phonebookDisplayItem.getImageurl());
                intent.putExtra("email", phonebookDisplayItem.getEmail());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                if (context instanceof PhonebookDetails) {
                    ((PhonebookDetails) context).finish();
                }
            }
        });


    }
}


