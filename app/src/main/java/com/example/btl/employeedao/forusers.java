package com.example.btl.employeedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.model.User;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class forusers {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public forusers(Context context) {
        dbHelper = new DBHelper(context); // sửa lỗi dbHelper viết thường
        db = dbHelper.getWritableDatabase();
    }

    public long insertUser(String name, String email, String phone, String dob, String password) {
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME, name);
        values.put(User.COLUMN_EMAIL, email);
        values.put(User.COLUMN_PHONE, phone);
        values.put(User.COLUMN_DOB, dob);
        values.put(User.COLUMN_PASSWORD, password);
        return db.insert(User.TABLE_NAME, null, values);
    }

    public boolean checkEmailExists(String email) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean validateLogin(String email, String password) {
        Cursor cursor = db.query(User.TABLE_NAME,
                null,
                User.COLUMN_EMAIL + "=? AND " + User.COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;
    }

    public boolean updateUser(int id, String name, String email, String phone, String dob, String password) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("date_of_birth", dob);
        values.put("password", password);
        int result = db.update("users", values, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean isEmailExists(String email) {
        Cursor cursor = db.query(User.TABLE_NAME, null, User.COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public int deleteUser(int id) {
        return db.delete(User.TABLE_NAME, User.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<User> getAllUserObjects() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + User.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(User.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_PHONE));
                String dob = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_DOB));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_PASSWORD));

                User user = new User(id, name, email, phone, dob, password);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    public User getUserById(int id) {
        Cursor c = db.rawQuery("SELECT * FROM users WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            User user = new User(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("email")),
                    c.getString(c.getColumnIndexOrThrow("phone")),
                    c.getString(c.getColumnIndexOrThrow("date_of_birth")),
                    c.getString(c.getColumnIndexOrThrow("password"))
            );
            c.close();
            return user;
        }
        c.close();
        return null;
    }
    public User getUserByEmail(String email) {
        Cursor cursor = db.query(User.TABLE_NAME, null, "email=?", new String[]{email}, null, null, null);
        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                    cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password"))
            );
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

}
