package com.example.btl.model;
import java.io.Serializable;
public class giohangticket implements Serializable{
    private int id;
    private String movieName;
    private String seat;
    private String bookingDate;
    private String price;
    private String poster;
    private String room;



    public void setRoom(String room) {
        this.room = room;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPurchaseTime(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setSeatNumber(String seat) {
        this.seat = seat;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    private String imageUrl;

    public giohangticket(int id, String movieName, String seat, String bookingDate,
                         String price, String poster, String room) {
        this.id = id;
        this.movieName = movieName;
        this.seat = seat;
        this.bookingDate = bookingDate;
        this.price = price;
        this.poster = poster;
        this.room = room;
    }

    public int getId() { return id;}
    public String getMovieName() { return movieName; }
    public String getSeat() { return seat; }
    public String getBookingDate() { return bookingDate; }
    public String getPrice() { return price; }
    public String getPoster() { return poster; }
    public String getRoom() {
        return room;
    }

    private boolean isSelected = false;

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) { isSelected = selected; }

    public void setId(int id) { this.id = id; }
}

