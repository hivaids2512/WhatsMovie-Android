package vn.edu.hcmiu.whatsmovie.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import vn.edu.hcmiu.whatsmovie.R;
import vn.edu.hcmiu.whatsmovie.entities.movie;
import vn.edu.hcmiu.whatsmovie.utilities.StringProcess;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private RatingBar ratingBar;
    private TextView movieTitle;
    private TextView moviePlot;
    private ImageView movieThumb;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        movie movie = (movie) getActivity().getIntent().getSerializableExtra("movie");

        movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
        movieThumb = (ImageView) rootView.findViewById(R.id.movie_thumb);

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);

        getActivity().setTitle(StringProcess.processTitile(movie.getTitle()));
        movieTitle.setText(movie.getTitle());
        moviePlot.setText(movie.getPlot());
        if(!movie.getPoster().equals("N/A")) {
            Picasso.with(getActivity()).load(movie.getPoster()).into(movieThumb);
        }else {
            movieThumb.setImageResource(R.drawable.posterna);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast toast = Toast.makeText(getActivity(), R.string.rating_complete, Toast.LENGTH_SHORT);
                toast.show();
                ratingBar.setIsIndicator(true);
            }
        });

        return rootView;
    }
}
