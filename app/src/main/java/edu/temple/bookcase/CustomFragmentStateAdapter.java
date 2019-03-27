package edu.temple.bookcase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CustomFragmentStateAdapter extends FragmentStatePagerAdapter {

    public CustomFragmentStateAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return BookDetailsFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return 10;
    }
}
