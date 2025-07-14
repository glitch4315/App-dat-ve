package com.example.btl.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.btl.model.Movie;
import com.example.btl.model.giohangticket;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "CinemaDB";
    private static final int DB_VERSION = 3;

    // Tạo bảng users
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phone TEXT," +
                    "date_of_birth DATE," +
                    "password TEXT NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    // Tạo bảng movies
    private static final String CREATE_TABLE_MOVIES =
            "CREATE TABLE movies (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "author TEXT," +
                    "trailer TEXT," +
                    "genre TEXT," +
                    "duration INTEGER," +
                    "rating_heart INTEGER DEFAULT 0," +
                    "rating_share INTEGER DEFAULT 0," +
                    "rating TEXT DEFAULT '0'," +
                    "release_date TEXT," +
                    "poster TEXT," +
                    "in_cart INTEGER DEFAULT 0" +
                    ");";


    // Tạo bảng tickets
    private static final String CREATE_TABLE_TICKETS =
            "CREATE TABLE tickets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER," +
                    "event_id INTEGER," +
                    "movieName TEXT, " +
                    "price TEXT, " +
                    "purchase_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "seat_number TEXT," +
                    "room TEXT," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (event_id) REFERENCES events(id)" +
                    ");";


    // Tạo bảng events
    private static final String CREATE_TABLE_EVENTS =
            "CREATE TABLE events (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "movie_id INTEGER," +
                    "room_id INTEGER," +
                    "start_time TIMESTAMP," +
                    "end_time TIMESTAMP," +
                    "sale_id INTEGER," +
                    "FOREIGN KEY (movie_id) REFERENCES movies(id)," +
                    "FOREIGN KEY (room_id) REFERENCES rooms(id)," +
                    "FOREIGN KEY (sale_id) REFERENCES sale(id)" +
                    ");";

    // Tạo bảng rooms
    private static final String CREATE_TABLE_ROOMS =
            "CREATE TABLE rooms (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "capacity INTEGER," +
                    "status TEXT" +
                    ");";

    // Tạo bảng food
    private static final String CREATE_TABLE_FOOD =
            "CREATE TABLE food (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "price NUMERIC," +
                    "quantity INTEGER" +
                    ");";

    // Tạo bảng sale
    private static final String CREATE_TABLE_SALE =
            "CREATE TABLE sale (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ticket_id INTEGER," +
                    "food_id INTEGER," +
                    "sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "discount NUMERIC," +
                    "FOREIGN KEY (ticket_id) REFERENCES tickets(id)," +
                    "FOREIGN KEY (food_id) REFERENCES food(id)" +
                    ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MOVIES);
        db.execSQL(CREATE_TABLE_TICKETS);
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_ROOMS);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_SALE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS movies");
        db.execSQL("DROP TABLE IF EXISTS tickets");
        db.execSQL("DROP TABLE IF EXISTS events");
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS food");
        db.execSQL("DROP TABLE IF EXISTS sale");
        onCreate(db);
    }


    public List<Movie> getAllMovies() {
        List<Movie> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM movies", null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("author")),
                        cursor.getString(cursor.getColumnIndexOrThrow("trailer")),
                        cursor.getString(cursor.getColumnIndexOrThrow("genre")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("duration")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("rating_heart")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("rating_share")),
                        cursor.getString(cursor.getColumnIndexOrThrow("rating")),
                        cursor.getString(cursor.getColumnIndexOrThrow("release_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("poster"))
                );
                list.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void seedSampleTicketsAndMovies() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM tickets", null);
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            cursor.close();
            return;
        }
        cursor.close();

        // ✅ Chỉ thêm phim nếu chưa có
        if (!movieExists("Avengers")) {
            ContentValues mv1 = new ContentValues();
            mv1.put("title", "Avengers");
            mv1.put("poster", "avengers");
            db.insert("movies", null, mv1);
        }

        if (!movieExists("Inside Out 2")) {
            ContentValues mv2 = new ContentValues();
            mv2.put("title", "Inside Out 2");
            mv2.put("poster", "inside_out_2");
            db.insert("movies", null, mv2);
        }

        // ✅ Thêm 2 vé mẫu
        ContentValues t1 = new ContentValues();
        t1.put("movieName", "Avengers");
        t1.put("price", "100000");
        t1.put("purchase_time", "2025-06-30 15:00:00");
        t1.put("seat_number", "C5, C6");
        t1.put("room", "A1");
        db.insert("tickets", null, t1);

        ContentValues t2 = new ContentValues();
        t2.put("movieName", "Inside Out 2");
        t2.put("price", "95000");
        t2.put("purchase_time", "2025-06-30 18:00:00");
        t2.put("seat_number", "B3");
        t2.put("room", "B2");
        db.insert("tickets", null, t2);
    }
    private boolean movieExists(String title) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM movies WHERE title = ?", new String[]{title});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }



    public void deleteTicketById(int id) {
        getWritableDatabase().delete("tickets", "id = ?", new String[]{String.valueOf(id)});
    }


}