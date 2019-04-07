package edu.temple.bookcase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class CustomFragmentStateAdapter extends FragmentStatePagerAdapter implements Filterable {

    private ArrayList<Book> bookTitles;
    private ArrayList<Book> unfilteredBookTitles;

    CustomFragmentStateAdapter(FragmentManager fm, ArrayList<Book> booklist) {
        super(fm);
        this.bookTitles = booklist;
        this.unfilteredBookTitles = booklist;
    }

    @Override
    public Fragment getItem(int i) {
        return BookDetailsFragment.newInstance(bookTitles.get(i));
    }

    @Override
    public int getCount() {
        return bookTitles.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                constraint = constraint.toString().toLowerCase();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<Book> filteredBookList = new ArrayList<>();
                    for (int i = 0; i < bookTitles.size(); i++) {
                        Book dataNames = bookTitles.get(i);
                        if (dataNames.getTitle().toLowerCase().contains(constraint.toString())) {
                            filteredBookList.add(dataNames);
                        }
                    }
                    results.count = filteredBookList.size();
                    results.values = filteredBookList;
                } else {
                    results.count = unfilteredBookTitles.size();
                    results.values = unfilteredBookTitles;
                }
                Log.e("VALUES", results.values.toString());
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                bookTitles = (ArrayList<Book>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}