package edu.temple.bookcase;

import android.content.Context;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    private BookSelectedListener listener;
    private ListView bookListView;
    private CustomArrayAdapter arrayAdapter;
    private ArrayList<Book> bookList;

    public BookListFragment() {
        // Required empty public constructor
    }

    public interface BookSelectedListener{
        void onBookTitleSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);
        MainActivity activity = (MainActivity) getActivity();
        bookList = activity.getBookList();
        Context context = getActivity();
        arrayAdapter = new CustomArrayAdapter(context, bookList);
        bookListView = v.findViewById(R.id.book_list);
        bookListView.setAdapter(arrayAdapter);

        bookListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onBookTitleSelected(position);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookSelectedListener) {
            listener = (BookSelectedListener)context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BookSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public CustomArrayAdapter getAdap() {
        return arrayAdapter;
    }

}
