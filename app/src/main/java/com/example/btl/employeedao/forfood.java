package com.example.btl.employeedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.model.food;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class forfood {
    private SQLiteDatabase db;

    public forfood(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertFood(String name, double price, int quantity) {
        ContentValues values = new ContentValues();
        values.put(food.COLUMN_NAME, name);
        values.put(food.COLUMN_PRICE, price);
        values.put(food.COLUMN_QUANTITY, quantity);
        return db.insert(food.TABLE_NAME, null, values);
    }

    public int updateFood(int id, String name, double price, int quantity) {
        ContentValues values = new ContentValues();
        values.put(food.COLUMN_NAME, name);
        values.put(food.COLUMN_PRICE, price);
        values.put(food.COLUMN_QUANTITY, quantity);
        return db.update(food.TABLE_NAME, values, food.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteFood(int id) {
        return db.delete(food.TABLE_NAME, food.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<String> getAllFood() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + food.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0) + " | " + cursor.getString(1) + " | $" + cursor.getDouble(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
