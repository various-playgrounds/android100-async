package com.example.android100async;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static final String CAT_IMAGE = "https://www.petmd.com/sites/default/files/petmd-cat-happy-10.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.image_view);

        // 3) async task
        DownloadImageAsyncTask downloadImageAsyncTask = new DownloadImageAsyncTask();
        downloadImageAsyncTask.execute(CAT_IMAGE);

        // 2) thread solution
//        new Thread() {
//            public void run() {
//                final Bitmap bmp;
//                try {
//                    bmp = downloadImage(CAT_IMAGE);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return;
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(bmp);
//                    }
//                });
//            }
//        }.start();

        // 1) network request in main thread:
        // java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.android100async/com.example.android100async.MainActivity}: android.os.NetworkOnMainThreadException
//        try {
//            bmp = downloadImage(CAT_IMAGE);
//        } catch ( IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        imageView.setImageBitmap(bmp);
    }

    private static Bitmap downloadImage(String s) throws IOException {
        URL url = new URL(s);
        InputStream is = url.openStream();
        return BitmapFactory.decodeStream(is);
    }

    class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return downloadImage(strings[0]);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = findViewById(R.id.image_view);
            imageView.setImageBitmap(bitmap);
        }
    }
}
