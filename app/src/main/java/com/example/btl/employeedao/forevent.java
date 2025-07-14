package com.example.btl.employeedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.model.event;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class forevent {
    private SQLiteDatabase db;

    public forevent(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertEvent(int movieId, int roomId, String startTime, String endTime, int saleId) {
        ContentValues values = new ContentValues();
        values.put(event.COLUMN_MOVIE_ID, movieId);
        values.put(event.COLUMN_ROOM_ID, roomId);
        values.put(event.COLUMN_START_TIME, startTime);
        values.put(event.COLUMN_END_TIME, endTime);
        values.put(event.COLUMN_SALE_ID, saleId);
        return db.insert(event.TABLE_NAME, null, values);
    }

    public int updateEvent(int id, int movieId, int roomId, String startTime, String endTime, int saleId) {
        ContentValues values = new ContentValues();
        values.put(event.COLUMN_MOVIE_ID, movieId);
        values.put(event.COLUMN_ROOM_ID, roomId);
        values.put(event.COLUMN_START_TIME, startTime);
        values.put(event.COLUMN_END_TIME, endTime);
        values.put(event.COLUMN_SALE_ID, saleId);
        return db.update(event.TABLE_NAME, values, event.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteEvent(int id) {
        return db.delete(event.TABLE_NAME, event.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<String> getAllEvents() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + event.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0) + " | Movie: " + cursor.getInt(1) + " | Room: " + cursor.getInt(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
