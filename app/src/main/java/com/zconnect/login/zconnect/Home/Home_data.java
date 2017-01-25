package com.zconnect.login.zconnect.Home;


public class Home_data {

    private long Day, Month, Hour, Minute;
   private long Year;
    private  byte type;
    private String Description, Title, Image_Url;


    private Home_data() {

    }


     Home_data(long xyz, long abc, long def, long hij, long klm, String nop, String qrs, String tuv, byte typ) {
        Day = xyz;
        Month = abc;
        Hour = def;
        Minute = hij;
        Year = klm;
        Description = nop;
        Title = qrs;
        type = typ;
        Image_Url = tuv;
    }

     long getDa() {
        return Day;
    }

    long getHo() {
        return Hour;
    }

     String getTitl() {
        return Title;
    }

 void setTitl(String title) {
        Title = title;
    }

 long getMon() {
        return Month;
    }

 long getMin() {
        return Minute;
    }
 String getDesc() {
        return Description;
    }

 long getYea() {
        return Year;
    }

    String getImage() {
        return Image_Url;
    }

    byte getTypeOfevent() {
        return type;
    }
}

