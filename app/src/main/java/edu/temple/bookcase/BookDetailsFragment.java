package edu.temple.bookcase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class BookDetailsFragment extends Fragment {

    private String[] descList;
    private Context context;
    private TextView bookTitleView;
    private TextView bookAuthorView;
    private Button stopButton;
    private Button playPauseButton;
    private SeekBar seekBar;
    private ImageView bookImageView;
    private boolean isPlaying;
    private static Book selectedBook;
    private TextView seekBarTextView;

    int progressValue;

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
        descList = context.getResources().getStringArray(R.array.descriptions);
        bookTitleView = rootView.findViewById(R.id.title_text);
        bookAuthorView = rootView.findViewById(R.id.author_text);
        bookImageView = rootView.findViewById(R.id.book_image);
        seekBarTextView = rootView.findViewById(R.id.seekbar_time);
        //Set the Parameters of the Book to show up in the views
        this.setText(selectedBook);
        //Stop Button Initialization

        stopButton = rootView.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Stop play of book completely
                Log.i("BookPlay: ", "Book Stopped...\n");
                ((MainActivity)getActivity()).stopBook();
            }
        });

        progressValue = 0;

        //Play/Pause Button Initialization
        isPlaying = false;
        playPauseButton = rootView.findViewById(R.id.pausePlayButton);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    //Pause the Audiobook
                    Log.i("BookPlay: ", "Book Paused...\n");
                    playPauseButton.setText("Play");
                    ((MainActivity)getActivity()).pauseBook();
                } else {
                    //Play the Audiobook
                    Log.i("BookPlay: ", "Book Played...\n");
                    playPauseButton.setText("Pause");
                    ((MainActivity)getActivity()).playBook(selectedBook.getBook_id(), progressValue);
                }
                isPlaying = !isPlaying;
            }
        });

        seekBar = rootView.findViewById(R.id.seekBar);
        seekBar.setMax(selectedBook.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
                Log.i("Progress: ", Integer.toString(progress));
                int progPercent = (progressValue * 100) / selectedBook.getDuration();
                Log.i("ProgressPercent:", Integer.toString(progPercent));
                ((MainActivity)getActivity()).seekBook(progressValue);
                seekBarTextView.setText(progPercent + "% / 100%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return rootView;
    }

    public void setText(Book book) {
        bookTitleView.setText(book.getTitle() + " (" + book.getPublished() + ")");
        bookAuthorView.setText(book.getAuthor());
        new DownloadCover(bookImageView).execute(book.getCover_url());
    }

    public void setSeekBarProgress(int progress){
        if (seekBar != null)
            seekBar.setProgress(progress);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
