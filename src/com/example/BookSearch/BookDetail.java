package com.example.BookSearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.BookSearch.data.Book;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/2/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookDetail extends Activity {

    private static final int MENU_CALL_REVIEW = Menu.FIRST + 2;
    private static final int MENU_MAP_REVIEW = Menu.FIRST + 1;
    private static final int MENU_WEB_REVIEW = Menu.FIRST;
    private String imageLink;
    private String link;
    private TextView author;
    private TextView title;
    private ImageView reviewImage;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if ((imageLink != null) && !imageLink.equals("")) {
                try {
                    URL url = new URL(imageLink);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new
                            BufferedInputStream(conn.getInputStream());
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    reviewImage.setImageBitmap(bm);
                } catch (IOException e) {
                    // log and or handle here
                }
            } else {
                reviewImage.setImageResource(R.drawable.no_review_image);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        author = (TextView) findViewById(R.id.author);
        title = (TextView) findViewById(R.id.title);

        BookSearchApplication application =
                (BookSearchApplication) getApplication();
        Book currentBook = application.getCurrentBook();

        link = currentBook.link;
        imageLink = currentBook.imageLink;

        if (title != null) {
            title.setText(currentBook.title);
        }

        if (author != null) {
            author.setText(currentBook.author);
        }

//        if ((currentBook.author != null) &&
//                !currentBook.author.equals("")) {
//            author.setText(currentBook.author);
//        } else {
//            author.setText("NA");
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        menu.add(0, BookDetail.MENU_WEB_REVIEW, 0,
//                R.string.menu_web_).setIcon(
//                android.R.drawable.ic_menu_info_details);
//        menu.add(0, BookDetail.MENU_MAP_REVIEW, 1,
//                R.string.menu_map_review).setIcon(
//                android.R.drawable.ic_menu_mapmode);
        return true;
    }
}
