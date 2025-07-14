// File: com.example.btl.model.Movie.java
package com.example.btl.model;

public class Movie {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_TRAILER = "trailer";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_RATING_HEART = "rating_heart";
    public static final String COLUMN_RATING_SHARE = "rating_share";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_POSTER = "poster";

    private int id;
    private String title;
    private String author;
    private String trailer;
    private String genre;
    private int duration;
    private int ratingHeart;
    private int ratingShare;
    private String rating;
    private String releaseDate;
    private String poster;

    public Movie(int id, String title, String author, String trailer, String genre,
                 int duration, int ratingHeart, int ratingShare, String rating,
                 String releaseDate, String poster) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.trailer = trailer;
        this.genre = genre;
        this.duration = duration;
        this.ratingHeart = ratingHeart;
        this.ratingShare = ratingShare;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getTrailer() { return trailer; }
    public String getGenre() { return genre; }
    public int getDuration() { return duration; }
    public int getRatingHeart() { return ratingHeart; }
    public int getRatingShare() { return ratingShare; }
    public String getRating() { return rating; }
    public String getReleaseDate() { return releaseDate; }
    public String getPoster() { return poster; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setTrailer(String trailer) { this.trailer = trailer; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setRatingHeart(int ratingHeart) { this.ratingHeart = ratingHeart; }
    public void setRatingShare(int ratingShare) { this.ratingShare = ratingShare; }
    public void setRating(String rating) { this.rating = rating; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setPoster(String poster) { this.poster = poster; }
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
