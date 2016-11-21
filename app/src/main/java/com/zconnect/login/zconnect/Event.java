package com.zconnect.login.zconnect;



public class Event {

    long Day, Month, Hour, Minute;
    long Year;
    String Description, Title, Image_Url;



    public Event() {

    }


    public Event(long xyz, long abc, long def, long hij, long klm, String nop, String qrs, String tuv) {
        Day = xyz    ;
        Month = abc;
        Hour = def;
        Minute = hij;
        Year = klm;
        Description = nop;
        Title = qrs;
        Image_Url=tuv;
    }

    long getDa() {
        return Day;
    }
    public long getHo() {
        return Hour;
    }
    public String getTitl() {
        return Title;
    }

public void setTitl(String title) {
        Title = title;
    }

   public long getMon() {
        return Month;
    }

    public long getMin() {
        return Minute;
    }

    public String getDesc() {
        return Description;
    }

    public long getYea() {
        return Year;
    }

        public String getImage() {
        return Image_Url;
    }}

