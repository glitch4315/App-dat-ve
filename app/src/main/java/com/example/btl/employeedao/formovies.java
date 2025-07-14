package com.example.btl.employeedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.model.Movie;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class formovies {
    private SQLiteDatabase db;

    public formovies(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertMovie(String title, String author, String trailer, String genre, int duration,
                            int ratingHeart, int ratingShare, String rating, String releaseDate, String poster) {
        ContentValues values = new ContentValues();
        values.put(Movie.COLUMN_TITLE, title);
        values.put(Movie.COLUMN_AUTHOR, author);
        values.put(Movie.COLUMN_TRAILER, trailer);
        values.put(Movie.COLUMN_GENRE, genre);
        values.put(Movie.COLUMN_DURATION, duration);
        values.put(Movie.COLUMN_RATING_HEART, ratingHeart);
        values.put(Movie.COLUMN_RATING_SHARE, ratingShare);
        values.put(Movie.COLUMN_RATING, rating);
        values.put(Movie.COLUMN_RELEASE_DATE, releaseDate);
        values.put(Movie.COLUMN_POSTER, poster);
        return db.insert(Movie.TABLE_NAME, null, values);
    }

    public int updateMovie(int id, String title, String author, String trailer, String genre, int duration,
                           int ratingHeart, int ratingShare, String rating, String releaseDate, String poster) {
        ContentValues values = new ContentValues();
        values.put(Movie.COLUMN_TITLE, title);
        values.put(Movie.COLUMN_AUTHOR, author);
        values.put(Movie.COLUMN_TRAILER, trailer);
        values.put(Movie.COLUMN_GENRE, genre);
        values.put(Movie.COLUMN_DURATION, duration);
        values.put(Movie.COLUMN_RATING_HEART, ratingHeart);
        values.put(Movie.COLUMN_RATING_SHARE, ratingShare);
        values.put(Movie.COLUMN_RATING, rating);
        values.put(Movie.COLUMN_RELEASE_DATE, releaseDate);
        values.put(Movie.COLUMN_POSTER, poster);
        return db.update(Movie.TABLE_NAME, values, Movie.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteMovie(int id) {
        return db.delete(Movie.TABLE_NAME, Movie.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<Movie> getAllMovieObjects() {
        List<Movie> moviesList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Movie.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                   Movie movie = new Movie(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_AUTHOR)),
                           cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_TRAILER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_GENRE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_DURATION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_RATING_HEART)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_RATING_SHARE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_RELEASE_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_POSTER))
                );
                moviesList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return moviesList;
    }
    public void addToCart(int movieId) {
        ContentValues values = new ContentValues();
        values.put("in_cart", 1);
        db.update(Movie.TABLE_NAME, values, "id = ?", new String[]{String.valueOf(movieId)});
    }
    public List<Movie> getMoviesInCart() {
        List<Movie> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM movies WHERE in_cart = 1", null);

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

    public Movie getMovieById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Movie.TABLE_NAME + " WHERE " + Movie.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Movie movie = new Movie(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_AUTHOR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_TRAILER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_GENRE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_DURATION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_RATING_HEART)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_RATING_SHARE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_RATING)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_POSTER))
            );
            cursor.close();
            return movie;
        }
        cursor.close();
        return null;
    }
}
