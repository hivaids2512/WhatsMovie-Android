package vn.edu.hcmiu.whatsmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.hcmiu.whatsmovie.R;
import vn.edu.hcmiu.whatsmovie.entities.movie;
import vn.edu.hcmiu.whatsmovie.utilities.StringProcess;

/**
 * Created by quyvu-pc on 30/12/2015.
 */
public class SingleMovieAdapter extends BaseAdapter {

    public ArrayList<movie> movies;
    public Context context;
    private static LayoutInflater inflater=null;

    public SingleMovieAdapter(Context activity, ArrayList<movie> movies ){
        this.context = activity;
        this.movies = movies;

    }

    @Override
    public int getCount() {

        return movies.size();
    }

    @Override
    public Object getItem(int i) {

        return i;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = ( LayoutInflater ) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movielist, null);
        TextView tv = (TextView) rowView.findViewById(R.id.textView1);
        ImageView iv = (ImageView) rowView.findViewById(R.id.imageView1);

        tv.setText(StringProcess.processTitile(movies.get(i).getTitle()));
        if(!movies.get(i).getPoster().equals("N/A")) {
            Picasso.with(context).load(movies.get(i).getPoster()).into(iv);
        }else {
            iv.setImageResource(R.drawable.posterna);
        }
        return rowView;
    }
}
