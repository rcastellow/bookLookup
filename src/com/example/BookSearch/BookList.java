package com.example.BookSearch;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.BookSearch.data.Book;
import com.example.BookSearch.data.BookFetcher;
import com.example.BookSearch.data.QueryURLFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/2/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookList extends ListActivity {
    private static final int MENU_CHANGE_CRITERIA = Menu.FIRST + 1;
    private static final int MENU_GET_NEXT_PAGE = Menu.FIRST;
    private static final int NUM_RESULTS_PER_PAGE = 8;

    private List<Book> bookList;
    private BookAdapter adapter;
    private TextView empty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        this.bookList = new ArrayList<Book>();
        initializeListView();
        loadBooks();
    }

    private void initializeListView() {
        empty = (TextView) findViewById(R.id.empty);
        ListView listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setEmptyView(empty);
    }

    private void updateAdapter(BookAdapter adapter) {
        ListView lView = (ListView) findViewById(android.R.id.list);
        lView.setAdapter(adapter);
        setListAdapter(adapter);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case MENU_GET_NEXT_PAGE:
                getNextPage();
                return true;
            case MENU_CHANGE_CRITERIA:
                Intent intent = new Intent(this, BookCriteria.class);
                startActivity(intent);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void getNextPage() {
        Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_LIST);
        intent.putExtra(Constants.STARTFROM_EXTRA,
                getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1)
                        + BookList.NUM_RESULTS_PER_PAGE);
        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        BookSearchApplication application = (BookSearchApplication) getApplication();
        application.setCurrentBook(bookList.get(position));
        Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_DETAIL);
        intent.putExtra(Constants.STARTFROM_EXTRA,
                getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1));
        startActivity(intent);
    }

    private void loadBooks() {
        final QueryURLFactory factory = new QueryURLFactory(getBookToFind());
        (new BookLoader()).execute(factory.getQueryURL());
    }

    private Book getBookToFind() {
        BookSearchApplication application = (BookSearchApplication) getApplication();
        String criteriaTitle = application.getBookCriteriaTitle();
        String criteriaAuthor = application.getBookCriteriaAuthor();
        return new Book(criteriaAuthor, criteriaTitle);
    }

    private class BookLoader extends AsyncTask<String, Void, List<Book>> {
        private final ProgressDialog dialog = new ProgressDialog(BookList.this);

        @Override
        protected void onPostExecute(List<Book> books) {
            if (this.dialog.isShowing())
                this.dialog.dismiss();
            bookList = books;
            adapter = new BookAdapter(BookList.this, books);
            updateAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Getting books. . .");
            dialog.show();
        }

        @Override
        protected List<Book> doInBackground(String... params) {
            return BookFetcher.getBookList(params[0]);
        }
    }
}
