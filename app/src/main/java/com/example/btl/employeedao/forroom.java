package com.example.btl.employeedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import com.example.btl.model.room;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class forroom {
    private SQLiteDatabase db;

    public forroom(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertRoom(String name, int capacity, String status) {
        ContentValues values = new ContentValues();
        values.put(room.COLUMN_NAME, name);
        values.put(room.COLUMN_CAPACITY, capacity);
        values.put(room.COLUMN_STATUS, status);
        return db.insert(room.TABLE_NAME, null, values);
    }

    public int updateRoom(int id, String name, int capacity, String status) {
        ContentValues values = new ContentValues();
        values.put(room.COLUMN_NAME, name);
        values.put(room.COLUMN_CAPACITY, capacity);
        values.put(room.COLUMN_STATUS, status);
        return db.update(room.TABLE_NAME, values, room.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteRoom(int id) {
        return db.delete(room.TABLE_NAME, room.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<String> getAllRooms() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + room.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0) + " | " + cursor.getString(1) + " | " + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
