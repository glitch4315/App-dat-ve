package com.example.btl.employeedao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.model.giohangticket;
import com.example.btl.model.ticket;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class forticket {
    private SQLiteDatabase db;

    public forticket(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // ✅ Thêm vé vào bảng tickets (chính xác)
    public void insertTicket(int userId, int eventId, String movieName, String price, String room, String seatNumber, String poster) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("event_id", eventId);
        values.put("movieName", movieName);
        values.put("price", price);
        values.put("room", room);
        values.put("seat_number", seatNumber);
        // purchase_time sẽ tự động là CURRENT_TIMESTAMP nhờ định nghĩa trong SQLite
        db.insert("tickets", null, values);
    }

    public int updateTicket(int id, int userId, int eventId, String seatNumber) {
        ContentValues values = new ContentValues();
        values.put(ticket.COLUMN_USER_ID, userId);
        values.put(ticket.COLUMN_EVENT_ID, eventId);
        values.put(ticket.COLUMN_SEAT_NUMBER, seatNumber);
        return db.update(ticket.TABLE_NAME, values, ticket.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteTicket(int id) {
        return db.delete(ticket.TABLE_NAME, ticket.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // ✅ Lấy tất cả vé, join với poster của movie theo movieName
    public List<giohangticket> getAllTickets() {
        List<giohangticket> list = new ArrayList<>();

        String query = "SELECT t.id, t.movieName, t.seat_number, t.purchase_time, t.price, m.poster, t.room " +
                "FROM tickets t " +
                "LEFT JOIN movies m ON t.movieName = m.title";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                giohangticket ticket = new giohangticket(
                        cursor.getInt(0),      // id
                        cursor.getString(1),   // movieName
                        cursor.getString(2),   // seat
                        cursor.getString(3),   // bookingDate (purchase_time)
                        cursor.getString(4),   // price
                        cursor.getString(5),   // poster
                        cursor.getString(6)    // room
                );
                list.add(ticket);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public String getTicketPriceByMovieId(int movieId) {
        String price = "95000"; // fallback nếu không có giá

        // Lấy tên phim từ movieId
        Cursor nameCursor = db.rawQuery("SELECT title FROM movies WHERE id = ?", new String[]{String.valueOf(movieId)});
        if (!nameCursor.moveToFirst()) {
            nameCursor.close();
            return price; // không có movie → return fallback
        }
        String movieName = nameCursor.getString(0);
        nameCursor.close();

        // Truy vấn giá vé theo tên phim
        Cursor cursor = db.rawQuery("SELECT price FROM tickets WHERE movieName = ? LIMIT 1", new String[]{movieName});
        if (cursor.moveToFirst()) {
            price = cursor.getString(0);
        }
        cursor.close();

        return price;
    }


}

