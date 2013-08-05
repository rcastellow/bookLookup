package com.example.BookSearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/1/13
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class BookCriteria extends Activity {
    private static final int MENU_GET_BOOKS = Menu.FIRST;
    private EditText author;
    private EditText title;
    private Button grabTitles;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Override onCreate()
        super.onCreate(savedInstanceState);

        // Define layout with setContentView
        setContentView(R.layout.book_criteria);

        title = (EditText) findViewById(R.id.title);
        author = (EditText) findViewById(R.id.author);
        grabTitles = (Button) findViewById(R.id.get_books_button);

        grabTitles.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        handleGetBooks();
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, BookCriteria.MENU_GET_BOOKS, 0,
                R.string.menu_get_books).setIcon(
                android.R.drawable.ic_menu_more);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case MENU_GET_BOOKS:
                handleGetBooks();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void handleGetBooks() {
        if (((title.getText() == null) ||
                title.getText().toString().equals("")) &&
                (author.getText() == null || author.getText().toString().equals(""))) {
            new AlertDialog.Builder(this).setTitle(R.string.alert_label).
                    setMessage(R.string.title_not_supplied_message).
                    setPositiveButton("Continue",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    // Just close alert.
                                }
                            }).show();
            return;
        }

        BookSearchApplication application = (BookSearchApplication) getApplication();
        application.setBookCriteriaTitle(title.getText().toString());
        application.setBookCriteriaAuthor(author.getText().toString());
        Intent intent =
                new Intent(Constants.INTENT_ACTION_VIEW_LIST);
        startActivity(intent);
    }
}
