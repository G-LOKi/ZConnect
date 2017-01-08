package com.zconnect.login.zconnect;

/**
 * Created by Lokesh Garg on 14-11-2016.
 */
public class Event {
    private String EventName, EventDescription, EventImage, EventDate;
    private int DateInt;

    public Event(){

    }

    public Event(String eventName, String eventDescription, String eventImage, String eventDate, int dateInt) {
        EventName = eventName;
        EventDescription = eventDescription;
        EventImage = eventImage;
        EventDate = eventDate;
        DateInt = dateInt;
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
