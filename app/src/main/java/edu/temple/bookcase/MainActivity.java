package edu.temple.bookcase;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.ArrayList;

import edu.temple.bookcase.BookListFragment.BookSelectedListener;

public class MainActivity extends FragmentActivity implements BookSelectedListener {

    private ViewPager detailPager;
    private BookDetailsFragment detailsFragment;
    android.content.res.Resources res;
    ArrayList<Book> booklist;
    EditText searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getResources();
        final BookListFragment listFragment = new BookListFragment();
        detailsFragment = new BookDetailsFragment();
        setContentView(R.layout.activity_main);
        buildLibrary();
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

            //Portrait: list container not available.
            } else {
            searchBar = findViewById(R.id.edit_text);
            detailPager = findViewById(R.id.details_pager);
            final CustomFragmentStateAdapter pagerAdapter = new CustomFragmentStateAdapter(getSupportFragmentManager(), booklist);
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
        return booklist;
    }

    @Override
    public void onBackPressed() {
        if (detailPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            detailPager.setCurrentItem(detailPager.getCurrentItem() - 1);
        }
    }



    public void buildLibrary(){
        booklist = new ArrayList<>();
        booklist.add(new Book("War and Peace", "Leo Tolstoy", ResourcesCompat.getDrawable(res, R.drawable.img00, null)));
        booklist.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", ResourcesCompat.getDrawable(res, R.drawable.img01, null)));
        booklist.add(new Book("Moby Dick", "Herman Melville",ResourcesCompat.getDrawable(res, R.drawable.img02, null)));
        booklist.add(new Book("Don Quixote", "Miguel de Cervantes",ResourcesCompat.getDrawable(res, R.drawable.img03, null)));
        booklist.add(new Book("Ulysses", "James Joyce", ResourcesCompat.getDrawable(res, R.drawable.img04, null)));
        booklist.add(new Book("Crime and Punishment", "Fyodor Dostoevksy", ResourcesCompat.getDrawable(res, R.drawable.img05, null)));
        booklist.add(new Book("The Odyssey", "Homer", ResourcesCompat.getDrawable(res, R.drawable.img06, null)));
        booklist.add(new Book("Pride and Prejudice", "Jane Austen", ResourcesCompat.getDrawable(res, R.drawable.img07, null)));
        booklist.add(new Book("Jane Eyre", "Charlotte Bronte", ResourcesCompat.getDrawable(res, R.drawable.img08, null)));
        booklist.add(new Book("To Kill a Mockingbird", "Harper Lee", ResourcesCompat.getDrawable(res, R.drawable.img09, null)));
    }

    @Override
    public void onBookTitleSelected(int input) {
        detailsFragment.setText(booklist.get(input));
    }


}

