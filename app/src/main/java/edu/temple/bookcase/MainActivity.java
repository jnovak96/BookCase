package edu.temple.bookcase;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import edu.temple.bookcase.BookListFragment.BookSelectedListener;

public class MainActivity extends FragmentActivity implements BookSelectedListener {

    private static final int NUM_PAGES = 10;
    private ViewPager detailPager;
    private PagerAdapter pagerAdapter;
    private BookListFragment listFragment;
    private BookDetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        listFragment = new BookListFragment();
        detailsFragment = new BookDetailsFragment();
        if (findViewById(R.id.list_container) != null) {
            FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
            fragTran.replace(R.id.details_container, detailsFragment);
            fragTran.addToBackStack(null);
            fragTran.commit();
            FragmentTransaction fragTran2 = getSupportFragmentManager().beginTransaction();
            fragTran2.replace(R.id.list_container, listFragment);
            fragTran2.addToBackStack(null);
            fragTran2.commit();
        } else if (findViewById(R.id.list_container) == null){
            detailPager = findViewById(R.id.details_pager);
            pagerAdapter = new CustomFragmentStateAdapter(getSupportFragmentManager());
            detailPager.setAdapter(pagerAdapter);
        }
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


    @Override
    public void onBookTitleSelected(int input) {
        detailsFragment.setText(input);
    }
}

