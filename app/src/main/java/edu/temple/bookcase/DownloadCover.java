package edu.temple.bookcase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DownloadCover extends AsyncTask<String, Void, Bitmap> {
    ImageView cover;

    public DownloadCover(ImageView cover) {
        this.cover = cover;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        cover.setImageBitmap(result);
    }
}
