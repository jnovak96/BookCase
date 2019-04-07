package edu.temple.bookcase;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomArrayAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Book> bookTitles;
    private ArrayList<Book> unfilteredBookTitles;
    private Context context;

    public CustomArrayAdapter(Context context, ArrayList<Book> bookTitles){
        this.context = context;
        this.bookTitles = bookTitles;
        this.unfilteredBookTitles = bookTitles;
    }

    @Override
    public int getCount() {
        return bookTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return bookTitles.get(position);
    }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView t = new TextView(context);
        t.setText("\n" + bookTitles.get(position).getTitle() + "\n");
        return t;
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
