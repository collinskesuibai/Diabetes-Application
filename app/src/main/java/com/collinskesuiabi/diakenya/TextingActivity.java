package com.collinskesuiabi.diakenya;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;


import com.collinskesuiabi.diakenya.Common.Common;
import com.collinskesuiabi.diakenya.Model.DiaUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class TextingActivity extends AppCompatActivity {


    public static String msgData,number,information,todaysDay;
    public static TextView mtextView,numberText;
    public static String bodies ="";
    public static float sugarValue;
    public static String strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texting);
        mtextView =findViewById(R.id.textViewMessage);
        numberText = findViewById(R.id.textViewNumber);


        ReadSms();
    }
    public void ReadSms (){
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception

                msgData = "";
                number ="";
                for(int idx=0;idx<cursor.getColumnCount();idx++)
                {
                    msgData += " " + cursor.getColumnName(idx) + "@@" + cursor.getString(idx);
                    number =cursor.getString(cursor.getColumnIndex("address"));

                }
                numberText.setText(number.toString());
                // use msgData
                mtextView.setText(msgData);

        } else {
            Toast.makeText(this, "there are no messages", Toast.LENGTH_SHORT).show();
            // empty box,

        }
}
    public static class SmsListener extends BroadcastReceiver {

        private SharedPreferences preferences;

        @Override
        public void onReceive(final Context context, Intent intent) {
            // TODO Auto-generated method stub

            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs = null;
                String msg_from;
                String body;
                if (bundle != null){
                    //---retrieve the SMS message received---
                    try{
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0; i<msgs.length; i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            number = msgs[i].getOriginatingAddress();
                            msgData = msgs[i].getMessageBody();

                           numberText.setText(number);
                           mtextView.setText(msgData);
                           if (msgData.equals("Enroll") || msgData.equals("enroll")){
                                PendingIntent pi = PendingIntent.getActivity(context, 0,
                                        new Intent(context, TextingActivity.class), 0);
                                SmsManager sms = SmsManager.getDefault();

                                sms.sendTextMessage(number, null, "Welcome to Dia health care,a diabetes health care program that is here for you ?(Send you blood sugar levels in mg/dl to analyse your state.)", pi, null);

                            }else if (msgData.equals("1") || msgData.equals("1")) {

                               SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                               Date d = new Date();
                               todaysDay = sdf.format(d);

                               FirebaseDatabase database = FirebaseDatabase.getInstance();
                               final DatabaseReference table_user = database.getReference("User");
                               final DatabaseReference table_departments = database.getReference("TimeTable");







                        }else if (msgData.equals("Info") ||msgData.equals("info") ) {


                           }else{


                               try {
                                   strings = msgData;

                                   sugarValue =Float.parseFloat(strings);
                                   if (sugarValue < 70){
                                       PendingIntent pi = PendingIntent.getActivity(context, 0,
                                               new Intent(context, TextingActivity.class), 0);
                                       SmsManager sms = SmsManager.getDefault();

                                       sms.sendTextMessage(number, null, "Your blood sugar level is low,please seek medication or contact us:@ +254710285805 ", pi, null);

                                   }else if (sugarValue>70 && sugarValue<100){
                                       PendingIntent pi = PendingIntent.getActivity(context, 0,
                                               new Intent(context, TextingActivity.class), 0);
                                       SmsManager sms = SmsManager.getDefault();

                                       sms.sendTextMessage(number, null, "Your blood sugar level is  Normal ,keep up the good job.for queries contact us:@ +254710285805", pi, null);


                                   }else if (sugarValue>100 && sugarValue<125){
                                       PendingIntent pi = PendingIntent.getActivity(context, 0,
                                               new Intent(context, TextingActivity.class), 0);
                                       SmsManager sms = SmsManager.getDefault();

                                       sms.sendTextMessage(number, null, "Your blood sugar level is  Pre-Diabetic ,please seek medication or contact us:@ +254710285805", pi, null);


                                   }else if (sugarValue>126){
                                       PendingIntent pi = PendingIntent.getActivity(context, 0,
                                               new Intent(context, TextingActivity.class), 0);
                                       SmsManager sms = SmsManager.getDefault();

                                       sms.sendTextMessage(number, null, "Your blood sugar level is  Diabetic,please seek medication or contact us:@ +254710285805", pi, null);


                                   }
                               } catch (NumberFormatException e) {
                                   PendingIntent pi = PendingIntent.getActivity(context, 0,
                                           new Intent(context, TextingActivity.class), 0);
                                   SmsManager sms = SmsManager.getDefault();

                                   sms.sendTextMessage(number, null, "Please input only numbers eg. 78.9 and try again.", pi, null);
                                   e.printStackTrace();
                               }

                           }

                        }
                    }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }

    }



}
