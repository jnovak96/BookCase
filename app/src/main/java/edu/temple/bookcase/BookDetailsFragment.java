package edu.temple.bookcase;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class BookDetailsFragment extends Fragment {

    private String[] descList;
    private Context context;
    private TextView bookTitleView;
    private TextView bookAuthorView;
    private ImageView bookImageView;
    private Resources resources;
    private static Book selectedBook;

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        selectedBook = book;
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
        //Remove and replace with Book Object Functionality
        descList = context.getResources().getStringArray(R.array.descriptions);
        bookTitleView = rootView.findViewById(R.id.title_text);
        bookAuthorView = rootView.findViewById(R.id.author_text);
        bookImageView = rootView.findViewById(R.id.book_image);
        this.setText(selectedBook);
        return rootView;
    }

    public void setText(Book book) {
        //Remove and replace with Book Object Functionality
        bookTitleView.setText(book.getTitle());
        bookAuthorView.setText(book.getAuthor());
        bookImageView.setImageDrawable(book.getImage());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
