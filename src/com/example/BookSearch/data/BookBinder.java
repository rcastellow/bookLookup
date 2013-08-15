package com.example.BookSearch.data;

import android.util.JsonReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mark.mixson on 8/9/13.
 */
public class BookBinder extends JSONBinder<Book> {
    private static final String AUTHOR = "author_name";
    private static final String TITLE = "title";
    private static final String TOP_LEVEL_NODE = "docs";
    private static final String COVER_ID = "cover_i";

    public BookBinder() {
        super(TOP_LEVEL_NODE, getFields());
    }

    private static List<String> getFields() {
        return Arrays.asList(AUTHOR, TITLE, COVER_ID);
    }

    @Override
    protected Book getNewItem() {
        return new Book();
    }

    @Override
    protected Book updateItemField(String fieldName,
                                   Book book,
                                   JsonReader reader) throws IOException {
        if (fieldName.equals(AUTHOR)) {
            handleAuthors(book, reader);
        } else if (fieldName.equals(TITLE)) {
            book.setTitle(reader.nextString());
        } else if (fieldName.equals(COVER_ID)) {
            book.setCoverID(reader.nextInt());
        }
        return book;
    }

    private void handleAuthors(Book book, JsonReader reader) throws IOException {
        reader.beginArray();
        book.author = new String();
        while(reader.hasNext()) {
            book.author += reader.nextString() + " / ";
        }
        if (book.author.isEmpty() == false)
            book.author = book.author.substring(0, book.author.length() - 3);
        reader.endArray();
    }
}
