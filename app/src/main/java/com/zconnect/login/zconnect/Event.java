package com.zconnect.login.zconnect;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Lokesh Garg on 14-11-2016.
 */
public class Event {
    private String EventName, EventDescription, EventImage, EventDate, FormatDate;

    public Event(){

    }

    public Event(String eventName, String eventDescription, String eventImage, String eventDate, String formatDate) {
        EventName = eventName;
        EventDescription = eventDescription;
        EventImage = eventImage;
        EventDate = eventDate;
        FormatDate = formatDate;

    }

    public String getEventDate() {
        String[] tokenizedDate = EventDate.split("\\s");
        int i=0;
        String date = "";
        while(i<3)
        {
            date = date + " " + tokenizedDate[i];
            i++;
        }
        return  date;
    }

    public String getSimpleDate() {
        String[] tokenizedDate = EventDate.split("\\s");
        int i=0;
        String date = "";
        while(i<4)
        {
            date = date + " " + tokenizedDate[i];
            i++;
        }
        return  date;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getEventImage() {
        return EventImage;
    }

    public void setEventImage(String eventImage) {
        EventImage = eventImage;
    }
}
