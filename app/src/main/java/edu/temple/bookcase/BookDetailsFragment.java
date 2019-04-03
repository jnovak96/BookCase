package edu.temple.bookcase;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BookDetailsFragment extends Fragment {

    private String[] descList;
    private Context context;
    private TextView bookListView;
    private Resources resources;
    private static int position;

    public static BookDetailsFragment newInstance(int pos) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        position = pos;
        return fragment;
    }

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_book_details, container, false);
        context = getActivity();
        descList = context.getResources().getStringArray(R.array.descriptions);
        bookListView = rootView.findViewById(R.id.details_text);
        this.setText(position);
        return rootView;
    }

    public void setText(int position) {
        bookListView.setText(descList[position]);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
