package com.example.BookSearch.data;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark.mixson on 8/8/13.
 */
public class BookFetcher {

    private static final String ENCODING_FORMAT = "UTF-8";

    public static List<Book> getBookList(String serviceURL) {
        JsonReader reader = null;
        try {
            reader = getReader(serviceURL);
            return (new BookBinder()).getData(reader);
        } catch (IOException e) {
            return new ArrayList<Book>();
        } finally {
            close(reader);
        }
    }

    private static JsonReader getReader(String serviceURL) throws IOException  {
        URL u = new URL(serviceURL);
        InputStream stream = u.openStream();
        InputStreamReader streamReader = new InputStreamReader(stream, ENCODING_FORMAT);
        return new JsonReader(streamReader);
    }

    private static void close(JsonReader reader) {
        try {
            if (reader != null)
                reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
