package com.example.BookSearch.data;

import android.util.JsonReader;
import android.util.Log;
import com.example.BookSearch.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/3/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class BookFetcher {

    private static final String CLASSTAG = BookFetcher.class.getSimpleName();
    private static final String QBASE = "http://openlibrary.org/search.json";
    private static final String QT_PREFIX = "title=";
    private static final String QA_PREFIX = "author=";
    private String query;


    public BookFetcher(String title, String author, int start, int numResults) {

        Log.v(Constants.LOGTAG, " " + BookFetcher.CLASSTAG + " title = " + title + " author = " + author + " start = "
                + start + " numResults = " + numResults);

        // urlencode params
        try {
            if (title != null) {
                title = URLEncoder.encode(title, "UTF-8");
            }
            if (author != null) {
                author = URLEncoder.encode(author, "UTF-8");
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        // build query
        this.query = BookFetcher.QBASE;
        String paramSeparator = "?";
        if ((title != null) && !title.equals("")) {
            this.query += (paramSeparator + BookFetcher.QT_PREFIX + title);
            paramSeparator = "&";
        }
        if ((author != null) && !author.equals("ANY")) {
            this.query += (paramSeparator + BookFetcher.QA_PREFIX + author);
        }

        Log.v(Constants.LOGTAG, " " + BookFetcher.CLASSTAG + " query - " + this.query);
    }

    /**
     * @return
     */
    public List<Book> getBooks() {
        long startTime = System.currentTimeMillis();
        List<Book> results = null;
        URL url;
        InputStream inputStream;
        JsonReader reader = null;
        try {
            url = new URL(this.query);
            try {
                inputStream = url.openStream();
                reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                results = readMessage(reader);
            } catch (IOException e) {
                Log.e(Constants.LOGTAG, " " + BookFetcher.CLASSTAG, e);
            }  finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            Log.e(Constants.LOGTAG, " " + BookFetcher.CLASSTAG, e);
        }

        long duration = System.currentTimeMillis() - startTime;
        Log.v(Constants.LOGTAG, " " + BookFetcher.CLASSTAG + " call and parse duration - " + duration);
        return results;
    }

    public List<Book> readMessage(JsonReader reader) throws IOException {
        long id = -1;
        String text = null;
        List<Book> bookList = new ArrayList();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("docs")) {
                bookList = readMessagesArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return bookList;
    }


    private List<Book> readMessagesArray(JsonReader reader) throws IOException {
        List<Book> bookList = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            Book book = new Book();
            while (reader.hasNext()) {
                String text = reader.nextName();
                if (text.equals("title")) {
                    text = reader.nextString();
                    book.setTitle(text);
                } else if (text.equals("author")) {
                    text = reader.nextString();
                    book.setAuthor(text);
                } else {
                    reader.skipValue();
                }
            }
            bookList.add(book);
            reader.endObject();
        }
        reader.endArray();
        return bookList;
    }

}
