package com.collinskesuiabi.diakenya.Common;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.collinskesuiabi.diakenya.Model.DiaUser;


import java.util.Calendar;
import java.util.Locale;


public class Common {

    public static DiaUser currentUser;
    public static DiaUser currentCustomer;
    public static int sizeOfMessageList ;



    public static String topicName ="News";
    public static String newMessagesUpdate;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public static final String fcmUrl ="https://fcm.googleapis.com/";
    public static String Phone_Text  ,messageid,senttoPhone,receiver ,sender ,dates ,times ,messages;

    public static String University ;
    public static String Department;


    public static  final String USER_DEPART = "Depart";
    public static  final String USER_KEY ="User";
    public static  final String UNI_KEY ="UNI";
    public static boolean isConnectedInternet(Context context){
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (connectivityManager != null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null){

                for(int  i=0 ; 1<info.length; i++)

                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;

                }
            }
        }
        return false;
    }


    public static String convertCodeTostring(String code)
    {
        if (code.equals("0"))
            return "placed";

        else if(code.equals("1"))
            return "On My Way";
        else
            return "Shipped";


    }

    public static String getDate(long time){

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        StringBuilder date = new StringBuilder(
                android.text.format.DateFormat.format("dd-MM-yyyy HH:mm",calendar).toString());

        return date.toString();


    }
    public static String getDateOnly(long time){

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        StringBuilder date = new StringBuilder(
                android.text.format.DateFormat.format("dd-MM-yyyy",calendar).toString());

        return date.toString();


    }


}
