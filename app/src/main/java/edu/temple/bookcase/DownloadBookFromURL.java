package edu.temple.bookcase;

import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadBookFromURL extends AsyncTask<String, String, String> {

    File storageDirectory;
    String directoryName = "bookCaseApp";

    @Override
    protected String doInBackground(String... url) {
        try {
            Log.d("URL: ", url[0]);
            URL bookURL = new URL(url[0]);
            storageDirectory = new File(Environment.getExternalStorageDirectory(), directoryName);
            storageDirectory.mkdirs();
            Log.d("Storage Directoty: ", storageDirectory.getAbsolutePath());
            URLConnection connection = bookURL.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();
            InputStream input = new BufferedInputStream(bookURL.openStream(), 8192);
            File bookFile = new File(storageDirectory, "audiobook");
            FileOutputStream output = new FileOutputStream(bookFile);
            byte data[] = new byte[1024];
            int len1 = 0;
            while ((len1 = input.read(data)) != -1) {
                output.write(data, 0, len1);
                Log.d("Download Progress: ", (Integer.toString((len1 * 100) / fileLength)));
            }
            output.flush();
            output.close();
            input.close();
        } catch (MalformedURLException e) {
            Log.e("Error Test: ", "MalformedURL");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error Test: ", "IOException");
            e.printStackTrace();
        }
        return null;
    }
}
