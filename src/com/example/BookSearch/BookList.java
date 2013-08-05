package com.example.BookSearch;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.example.BookSearch.data.Book;
import com.example.BookSearch.data.BookFetcher;

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
    private TextView empty;
    private ProgressDialog progressDialog;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private final Handler handler = new Handler() {
        public void handleMessage(final Message msg) {
            progressDialog.dismiss();
            if ((bookList == null) || (bookList.size() == 0)) {
                empty.setText("No Data");
            } else {
                bookAdapter = new BookAdapter(BookList.this, bookList);
                setListAdapter(bookAdapter);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        empty = (TextView) findViewById(R.id.empty);
        ListView listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setEmptyView(empty);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BookSearchApplication application = (BookSearchApplication) getApplication();
        String criteriaTitle = application.getBookCriteriaTitle();
        String criteriaAuthor = application.getBookCriteriaAuthor();
        int startFrom = getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1);
        loadBooks(criteriaTitle, criteriaAuthor, startFrom);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case MENU_GET_NEXT_PAGE:
                intent = new Intent(Constants.INTENT_ACTION_VIEW_LIST);
                intent.putExtra(Constants.STARTFROM_EXTRA,
                        getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1)
                                + BookList.NUM_RESULTS_PER_PAGE);
                startActivity(intent);
                return true;
            case MENU_CHANGE_CRITERIA:
                intent = new Intent(this, BookCriteria.class);
                startActivity(intent);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        BookSearchApplication application =
                (BookSearchApplication) getApplication();
        application.setCurrentBook(bookList.get(position));
        Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_DETAIL);
        intent.putExtra(Constants.STARTFROM_EXTRA, getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1));
        startActivity(intent);
    }

    private void loadBooks(String title, String author,
                           int startFrom) {
        final BookFetcher rf = new BookFetcher(title, author, startFrom,
                BookList.NUM_RESULTS_PER_PAGE);
        progressDialog =
                ProgressDialog.show(this, " Working...",
                        " Retrieving bookList", true, false);
        new Thread() {
            public void run() {
                bookList = rf.getBooks();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}
