package edu.temple.bookcase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import edu.temple.audiobookplayer.*;
import edu.temple.bookcase.BookListFragment.BookSelectedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements BookSelectedListener {

    private String jsonData;
    private ViewPager detailPager;
    private BookDetailsFragment detailsFragment;
    private BookListFragment listFragment;
    android.content.res.Resources res;
    ArrayList<Book> bookList;
    EditText searchBar;
    private AudiobookService.MediaControlBinder mBinder;
    boolean abServiceConnected;
    private Handler progressHandler = new Handler();


    ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (AudiobookService.MediaControlBinder) service;
            Log.i("Binder: ", "Service Bound");
            abServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("Binder: ", "Service Unbound");
            abServiceConnected = false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, AudiobookService.class);
        bindService(serviceIntent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        unbindService(myConnection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        res = getResources();
        listFragment = new BookListFragment();
        detailsFragment = new BookDetailsFragment();
        setContentView(R.layout.activity_main);
        fetchJson process = new fetchJson();

        //Fetch JSON Data
        try {
            jsonData = process.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Build Library
        try {
            buildLibrary();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Landscape
        if (findViewById(R.id.list_container) != null) {
            FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
            fragTran.replace(R.id.details_container, detailsFragment);
            fragTran.addToBackStack(null);
            fragTran.commit();
            FragmentTransaction fragTran2 = getSupportFragmentManager().beginTransaction();
            fragTran2.replace(R.id.list_container, listFragment);
            fragTran2.addToBackStack(null);
            fragTran2.commit();
            searchBar = findViewById(R.id.edit_text);
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listFragment.getAdap().getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            //Portrait: list container unavailable.
        } else {
            searchBar = findViewById(R.id.edit_text);
            detailPager = findViewById(R.id.details_pager);
            final CustomFragmentStateAdapter pagerAdapter = new CustomFragmentStateAdapter(getSupportFragmentManager(), bookList);
            detailPager.setAdapter(pagerAdapter);
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    pagerAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    @Override
    public void onBackPressed() {
        if (detailPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            detailPager.setCurrentItem(detailPager.getCurrentItem() - 1);
        }
    }

    public void buildLibrary() throws JSONException {
        bookList = new ArrayList<>();
        Log.i("jsonData: ", jsonData);
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currentBook = (JSONObject) jsonArray.get(i);
            bookList.add(new Book(currentBook.getInt("book_id"),
                    currentBook.getString("title"),
                    currentBook.getString("author"),
                    currentBook.getInt("duration"),
                    currentBook.getInt("published"),
                    currentBook.getString("cover_url")));
        }
        for (int i = 0; i < bookList.size(); i++) {
            Log.i("BookList", bookList.get(i).toString());
        }
    }

    public void playBook(int id, int progress) {
        if (progress > 0)
            mBinder.play(id, progress);
        else
            mBinder.play(id);

        setupHandler();
    }


    public void pauseBook() {
        mBinder.pause();
    }

    public void stopBook() {
        mBinder.stop();
    }

    public void seekBook(int position) {
        mBinder.seekTo(position);
    }

    public void setupHandler() {
        progressHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                detailsFragment.setSeekBarProgress(msg.what);
                return false;
            }
        });
        mBinder.setProgressHandler(progressHandler);
        Log.i("HandlerSetup: ", "TRUE");
    }

    @Override
    public void onBookTitleSelected(int input) {
        detailsFragment.setText(bookList.get(input));
    }
}

