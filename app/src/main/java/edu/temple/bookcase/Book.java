package edu.temple.bookcase;

import android.graphics.drawable.Drawable;

public class Book {

    private String title;
    private String author;
    private Drawable image;

    public Book (String title, String author, Drawable image) {
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Drawable getImage() {
        return image;
    }

    public Book() {
        //default constructor
    }
}
