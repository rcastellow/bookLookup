package com.example.BookSearch;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.BookSearch.data.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/2/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookAdapter extends BaseAdapter {
    private final Context context;
    private final List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book review = bookList.get(position);
        if (convertView == null || !(convertView instanceof BookListView)) {
            return new BookListView(context, review.author,
                    review.title);
        }
        BookListView view = (BookListView) convertView;
        view.setName(review.author);
        view.setRating(review.title);
        return view;
    }

    private final class BookListView extends LinearLayout {
        private TextView author;
        private TextView title;

        public BookListView(
                Context context, String itemName,
                String itemRating) {
            super(context);
            setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 3, 5, 0);
            author = new TextView(context);
            author.setText(itemName);
            author.setTextSize(16f);
            author.setTextColor(Color.WHITE);
            addView(author, params);
            title = new TextView(context);
            title.setText(itemRating);
            title.setTextSize(16f);
            title.setTextColor(Color.GRAY);
            addView(title, params);
        }

        public void setName(String itemName) {
            author.setText(itemName);
        }

        public void setRating(String itemRating) {
            title.setText(itemRating);
        }
    }
}
