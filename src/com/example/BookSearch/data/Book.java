package com.example.BookSearch.data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/3/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Book {
    public String author;
    public String title;
    public Date date;
    public String imageLink;
    public String link;
    public String description;

    public Book() {
    }

    public Book(String author, String title) {
        this.author=author;
        this.title=title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("*Book*\n");
        sb.append("title:" + this.title + "\n");
        sb.append("author:" + this.author + "\n");
        sb.append("link:" + this.link + "\n");
        sb.append("imageLink:" + this.imageLink + "\n");
        sb.append("description:" + this.description + "\n");
        return sb.toString();
    }

}
