package edu.temple.bookcase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BookDetailsFragment extends Fragment {

    private String[] descList;

    public static BookDetailsFragment newInstance(int position) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        //fragment.setText(position);
        return fragment;
    }

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_book_details, container, false);
        return rootView;
    }

    public void setText(int position) {
        Context context = getActivity();
        descList = context.getResources().getStringArray(R.array.descriptions);
        TextView bookListView = getView().findViewById(R.id.details_text);
        bookListView.setText(descList[position]);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

}
