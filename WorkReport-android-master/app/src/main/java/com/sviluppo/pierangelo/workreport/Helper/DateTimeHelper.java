package com.sviluppo.pierangelo.workreport.Helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pierangelo on 08/11/16.
 */

public class DateTimeHelper {


    // formatta data e time
    public String myFormatDateTime(String data) {

        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy'T'HH:mm:ss");
        String mydata = null;
        try {
            mydata = String.valueOf(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mydata;
    };


    //ottieni solo time da datetime
    public String myFormatDate(String data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dt = null;
        try {
            dt = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(dt);
    }


    //ottieni solo time da datetime
    public String myFormatTime(String data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy'T'HH:mm:ss");
        Date dt = null;
        try {
            dt = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        return dateFormat.format(dt);
    }

}
