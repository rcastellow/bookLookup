package com.example.BookSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        super(context, android.R.layout.simple_list_item_1, bookList);
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        if (bookList != null)
            return bookList.size();
        return 0;
    }

    @Override
    public Book getItem(int position) {
        if (bookList != null)
            return bookList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (bookList != null)
            return bookList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflateView(convertView, parent);
        Book book = bookList.get(position);
        TextView title = (TextView) v.findViewById(R.id.book_title);
        title.setText(book.title);
        return v;
    }

    private View inflateView(View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.book_list_row, parent, false);
        }
        return v;
    }

    public List<Book> getItemList() {
        return bookList;
    }

    public void setItemList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
