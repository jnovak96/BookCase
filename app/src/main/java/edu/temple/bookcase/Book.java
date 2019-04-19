package edu.temple.bookcase;

import android.graphics.drawable.Drawable;

public class Book {

    private int book_id;
    private String title;
    private String author;
    private int duration;
    private int published;
    private String cover_url;

    public Book() {
        //default constructor
    }

    public Book (int book_id, String title, String author, int duration, int published, String cover_url) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.published = published;
        this.cover_url = cover_url;
    }

    public int getBook_id() {return book_id; }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCover_url() {return cover_url; }

    public int getDuration() {return duration; }

    public int getPublished() {return published; }

    public String toString() {
        return "Book ID: " + book_id + "\n" +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Duration: " + duration + "\n" +
                "Published: " + published + "\n" +
                "Cover URL: " + cover_url + "\n";
    }
}
