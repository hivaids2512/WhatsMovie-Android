package vn.edu.hcmiu.whatsmovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import vn.edu.hcmiu.whatsmovie.LoginActivity;
import vn.edu.hcmiu.whatsmovie.MainActivity;
import vn.edu.hcmiu.whatsmovie.MovieDetailActivity;
import vn.edu.hcmiu.whatsmovie.R;
import vn.edu.hcmiu.whatsmovie.adapter.SingleMovieAdapter;
import vn.edu.hcmiu.whatsmovie.client.Client;
import vn.edu.hcmiu.whatsmovie.configuration.Configuration;
import vn.edu.hcmiu.whatsmovie.entities.movie;
import vn.edu.hcmiu.whatsmovie.security.securityManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link moviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link moviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class moviesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gridview;
    private SingleMovieAdapter adapter;
    private ProgressBar progressBar;
    Integer count = 1;
    private ArrayList<movie> movies;
    private String secureToken;

    private OnFragmentInteractionListener mListener;

    public moviesFragment() {
        movies = new ArrayList<movie>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment moviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static moviesFragment newInstance(String param1, String param2) {
        moviesFragment fragment = new moviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            secureToken = getArguments().getString("secureToken");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        gridview = (GridView) rootView.findViewById(R.id.movies);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(10);
        //secureToken = getArguments().getString("secureToken");
        //progressBar.setProgress(0);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, numbers);

        new HttpRequestTask().execute();


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                final Intent intent = new Intent(getActivity().getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("movie", movies.get(position));

                secureToken = new securityManager(getActivity()).readToken("secureToken");
                intent.putExtra("secureToken", secureToken);
                startActivity(intent);
            }

        });
        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class HttpRequestTask extends AsyncTask<Void, Integer, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... voids) {
            try {
                for(; count<=5;count++){
                    try {
                        Thread.sleep(1000);
                        publishProgress(count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //String url = "http://www.omdbapi.com/?t="+URLEncoder.encode("Life of pi", "UTF-8");
                String url = Configuration.GET_MOVIES_ADDRESS;

                String json = new Client(url).doGet();
                //Log.e("JSON", json);
                //json = Html.fromHtml(json).toString();
                JSONArray jsonArray = new JSONArray(json);
                //JSONObject jsonObj = new JSONObject(json);
                //Log.e("JSON", jsonArray.getString(1));
                for(; count<=10;count++){
                    try {
                        Thread.sleep(1000);
                        publishProgress(count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return jsonArray;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        private ArrayList<movie> map(JSONArray jsonArray){
            ArrayList movies = new ArrayList<movie>();

            try {
                for(int i=0; i< jsonArray.length(); i++){
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    movie movie = new movie();
                    movie.setId(jsonObj.getString("Id"));
                    movie.setTitle(jsonObj.getString("Title"));
                    movie.setPoster(jsonObj.getString("Poster"));
                    movie.setActor(jsonObj.getString("Actor"));
                    movie.setDirector(jsonObj.getString("Director"));
                    movie.setGenre(jsonObj.getString("Genre"));
                    movie.setIMDBRating(jsonObj.getString("IMDBRating"));
                    movie.setPlot(jsonObj.getString("Plot"));
                    movie.setYear(jsonObj.getString("Year"));
                    movies.add(movie);
                }

            } catch (JSONException e) {
                Log.e("JSON", e.getMessage());
            }
            return movies;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            movies = map(jsonArray);

            progressBar.setVisibility(View.GONE);
            adapter = new SingleMovieAdapter(getActivity(), movies);
            gridview.setAdapter(adapter);

        }

        @Override
        protected void onPreExecute() {
            progressBar.setMax(10);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }

}
