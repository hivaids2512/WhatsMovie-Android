package vn.edu.hcmiu.whatsmovie.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import vn.edu.hcmiu.whatsmovie.R;
import vn.edu.hcmiu.whatsmovie.client.Client;
import vn.edu.hcmiu.whatsmovie.configuration.Configuration;
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
    private TextView movieRelease;
    private TextView movieGenre;
    private TextView movieDirector;
    private TextView movieActor;
    private TextView movieIMDBRating;
    private String secureToken;
    private String movieId;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        final movie movie = (movie) getActivity().getIntent().getSerializableExtra("movie");
        secureToken = (String) getActivity().getIntent().getStringExtra("secureToken");

        movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
        movieThumb = (ImageView) rootView.findViewById(R.id.movie_thumb);
        movieRelease = (TextView) rootView.findViewById(R.id.movie_release);
        movieGenre = (TextView) rootView.findViewById(R.id.movie_genre);
        movieDirector = (TextView) rootView.findViewById(R.id.movie_director);
        movieActor = (TextView) rootView.findViewById(R.id.movie_actor);
        movieIMDBRating = (TextView) rootView.findViewById(R.id.movie_imdbRating);

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);

        getActivity().setTitle(StringProcess.processTitile(movie.getTitle()));
        movieTitle.setText(movie.getTitle());
        moviePlot.setText(movie.getPlot());
        movieRelease.setText(movie.getYear());
        movieGenre.setText(movie.getGenre());
        movieDirector.setText(movie.getDirector());
        movieActor.setText(movie.getActor());
        movieIMDBRating.setText(movie.getIMDBRating());
        if(!movie.getPoster().equals("N/A")) {
            Picasso.with(getActivity()).load(movie.getPoster()).into(movieThumb);
        }else {
            movieThumb.setImageResource(R.drawable.posterna);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                String params[] = new String[3];
                params[0] = secureToken;
                params[1] = String.valueOf(rating);
                params[2] = movie.getId();

                new HttpRequestTask().execute(params);
                Toast toast = Toast.makeText(getActivity(), R.string.rating_complete, Toast.LENGTH_SHORT);
                toast.show();
                ratingBar.setIsIndicator(true);
            }
        });

        return rootView;
    }

    public class HttpRequestTask extends AsyncTask<String[], Void, Void> {

        @Override
        protected Void doInBackground(String[]... params) {
            try {

                String paramsToPass[] = params[0];
                String url = Configuration.RATING_ADDRESS;
                JSONObject jsonObj = new JSONObject();

                jsonObj.put("secureToken", paramsToPass[0]);
                jsonObj.put("rating", paramsToPass[1]);
                jsonObj.put("movieId", paramsToPass[2] );
                String json = new Client(url).doPost(jsonObj);
                Log.e("JSON", String.valueOf(paramsToPass[0]));
                Log.e("JSON1", String.valueOf(paramsToPass[1]));
                Log.e("JSON12", String.valueOf(paramsToPass[2]));
                //json = Html.fromHtml(json).toString();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
