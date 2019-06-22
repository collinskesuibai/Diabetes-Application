package com.collinskesuiabi.diakenya.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;


import com.collinskesuiabi.diakenya.Model.DataChatMessages;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDepart extends SQLiteAssetHelper {
    private static final String DB_NAME = "Event";
    private static final int DB_VER = 1;
    Context contexts;
    Thread thread1;

    String sender ,receiver ,date ,message ,time,messageId,userPhone;


    public DatabaseDepart(Context context) {
        super(context, DB_NAME, null, DB_VER);
        contexts =context;


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);


    }

    //user chat methods


    public boolean isChatUserDia(String messageId, String userPhone) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("SELECT * FROM UserChart WHERE messageId='%s' and UserPhone='%s';", messageId, userPhone);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() <= 0) {

                cursor.close();
                return false;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;


    }
    public List<DataChatMessages> getAllChatUserDia(String userPhone) {


        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String[] sqlSelect = {"messageId","UserPhone","message","sender","receiver","date","time"};

            String sqlTable = "UserChart";

            qb.setTables(sqlTable);
            c = qb.query(db, sqlSelect, "UserPhone=?", new String[]{userPhone}, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<DataChatMessages> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new DataChatMessages(
                        c.getString(c.getColumnIndex("messageId")),
                        c.getString(c.getColumnIndex("UserPhone")),
                        c.getString(c.getColumnIndex("message")),
                        c.getString(c.getColumnIndex("sender")),
                        c.getString(c.getColumnIndex("receiver")),
                        c.getString(c.getColumnIndex("date")),
                        c.getString(c.getColumnIndex("time"))

                ));
            } while (c.moveToNext());
        }
        return result;
    }
    public void addToChatUserDia(DataChatMessages posting) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO UserChart(messageId,UserPhone,message,sender,receiver,date,time) VALUES('%s','%s','%s','%s','%s','%s','%s');",
                posting.getMessageId(),
                posting.getUserPhone(),
                posting.getMessage(),
                posting.getSender(),
                posting.getReceiver(),
                posting.getDate(),
                posting.getTime()


        );

        db.execSQL(query);
    }
    public void removeFromUserChat2(String messageId, String userPhone) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM UserChart WHERE messageId='%s' and UserPhone='%s';", messageId, userPhone);

        db.execSQL(query);
    }




}
