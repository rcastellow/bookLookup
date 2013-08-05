package com.example.BookSearch;

import android.app.Application;
import com.example.BookSearch.data.Book;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/3/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class BookSearchApplication extends Application {

    private Book currentBook;
    private String bookCriteriaAuthor;
    private String bookCriteriaTitle;

    public BookSearchApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getBookCriteriaAuthor() {
        return this.bookCriteriaAuthor;
    }

    public String getBookCriteriaTitle() {
        return this.bookCriteriaTitle;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setBookCriteriaAuthor(String bookCriteriaAuthor) {
        this.bookCriteriaAuthor = bookCriteriaAuthor;
    }

    public void setBookCriteriaTitle(String bookCriteriaTitle) {
        this.bookCriteriaTitle = bookCriteriaTitle;
    }

}
