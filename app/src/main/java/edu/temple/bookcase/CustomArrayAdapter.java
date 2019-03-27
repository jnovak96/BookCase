package edu.temple.bookcase;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;

public class CustomArrayAdapter extends BaseAdapter {

    private String[] bookTitles;
    private Context context;

    public CustomArrayAdapter(Context context, String[] bookTitles){
        this.context = context;
        this.bookTitles = bookTitles;
    }

    @Override
    public int getCount() {
        return bookTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return bookTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView t = new TextView(context);
        t.setText("\n" + bookTitles[position] + "\n");
        return t;
    }
}
