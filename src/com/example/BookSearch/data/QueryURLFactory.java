package com.example.BookSearch.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/3/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryURLFactory {

    private static final String CLASSTAG = QueryURLFactory.class.getSimpleName();
    private static final String QBASE = "http://openlibrary.org/search.json";
    private static final String QT_PREFIX = "title=";
    private static final String QA_PREFIX = "author=";
    private static final String ENCODING = "UTF-8";
    private static final String SEPARATOR_START = "?";
    private static final String SEPARATOR_INCLUDE = "&";
    private static final String ALL_AUTHORS = "ANY";

    private static final String IMAGE_BASE = "http://covers.openlibrary.org/b/id/";
    private static final String IMAGE_EXT = ".jpg";
    private static final String IMAGE_SIZE = "M";

    private String query;
    private String imageLinkQuery;


    public QueryURLFactory(Book book) {
        book.title = encodeParameter(book.title);
        book.author = encodeParameter(book.author);
        buildQuery(book);
        buildImageLink(book);
    }

    private String encodeParameter(String input) {
        try {
            if (input != null)
                return URLEncoder.encode(input, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return input;
    }

    private void buildQuery(Book book) {
        this.query = QueryURLFactory.QBASE;
        String paramSeparator = SEPARATOR_START;
        if (haveTitle(book.title)) {
            this.query += (paramSeparator + QueryURLFactory.QT_PREFIX + book.title.replace(' ', '+'));
            paramSeparator = SEPARATOR_INCLUDE;
        }
        if (haveAuthor(book.author))
            this.query += (paramSeparator + QueryURLFactory.QA_PREFIX + book.author.replace(' ', '+'));
    }

    private boolean haveTitle(String title) {
        if (title != null) {
            if (title.equals("") == false)
                return true;
        }
        return false;
    }

    private boolean haveAuthor(String author) {
        if (author != null) {
            if (author.equals(ALL_AUTHORS) == false)
                return true;
        }
        return false;
    }

    private void buildImageLink(Book book) {
        if (book.coverID > 0) {
            this.imageLinkQuery = QueryURLFactory.IMAGE_BASE +
                    book.coverID + "-" + IMAGE_SIZE + IMAGE_EXT;
            book.imageLink = this.imageLinkQuery;
        }
    }

    public String getQueryURL() {
        return query;
    }

    public String getImageURL() {
        return imageLinkQuery;
    }

}
