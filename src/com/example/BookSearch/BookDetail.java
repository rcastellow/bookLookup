package com.example.BookSearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.BookSearch.data.Book;
import com.example.BookSearch.data.QueryURLFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
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

    private String imageLink;
    private TextView name_detail;
    private ImageView reviewImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        BookSearchApplication application = (BookSearchApplication) getApplication();
        Book currentBook = application.getCurrentBook();
        setupText(currentBook);
        setupImage(currentBook);
    }

    private void setupImage(Book currentBook) {
        reviewImage = (ImageView) findViewById(R.id.review_image);
        final QueryURLFactory factory = new QueryURLFactory(currentBook);
        this.imageLink = factory.getImageURL();
        (new BitmapWorker(reviewImage)).execute();
    }

    private void setupText(Book currentBook) {
        name_detail = (TextView) findViewById(R.id.name_detail);
        if (name_detail != null) {
            name_detail.setText(currentBook.author + "\n" + currentBook.title);
        }
    }

    private class BitmapWorker extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorker(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            if ((imageLink != null) && !imageLink.equals("")) {
                return getImageLink();
            } else {
                reviewImage.setImageResource(R.drawable.book_icon);
                return null;
            }
        }

        private Bitmap getImageLink() {
            Bitmap bm = getBitmapFromURL();
            if (bm != null) {
                return bm;
            } else {
                reviewImage.setImageResource(R.drawable.book_icon);
                return null;
            }
        }

        private Bitmap getBitmapFromURL() {
            BufferedInputStream bis = null;
            try {
                URLConnection conn = (new URL(imageLink)).openConnection();
                conn.connect();
                bis = new BufferedInputStream(conn.getInputStream());
                return BitmapFactory.decodeStream(bis);
            } catch (IOException e) {
                return null;
            } finally {
                closeStream(bis);
            }
        }

        private void closeStream(BufferedInputStream bis) {
            try {
                if (bis != null)
                    bis.close();
            } catch (IOException e) {}
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final Bitmap returnedImage = bitmap;
                if (imageView != null)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(returnedImage);
                        }
                    });
            }
        }
    }
}
