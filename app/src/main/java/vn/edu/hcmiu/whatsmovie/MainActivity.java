package vn.edu.hcmiu.whatsmovie;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ListView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import vn.edu.hcmiu.whatsmovie.adapter.ViewPagerAdapter;
import vn.edu.hcmiu.whatsmovie.entities.movie;
import vn.edu.hcmiu.whatsmovie.fragment.NavigationDrawerFragment;
import vn.edu.hcmiu.whatsmovie.fragment.moviesFragment;
import vn.edu.hcmiu.whatsmovie.layout.SlidingTabLayout;
import vn.edu.hcmiu.whatsmovie.security.securityManager;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public String secureToken;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    private CharSequence mTitle;
    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("WhatsMovie");
        getSupportActionBar().setElevation(0);
        Intent intent = getIntent();
        secureToken = intent.getStringExtra("secureToken");
        Bundle bundle = new Bundle();
        bundle.putString("secureToken", secureToken);
        moviesFragment fragobj = new moviesFragment();
        fragobj.setArguments(bundle);


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch(position){
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();


                break;
            case 1:

                break;
            case 2:


                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("logout", "logout");
                startActivity(intent);
                break;


        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private SlidingTabLayout tabs;
        private CharSequence Titles[]={"Movies","Recommendations"};
        private ViewPagerAdapter adapter;
        private int Numboftabs =2;
        private ViewPager pager;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            pager = (ViewPager) rootView.findViewById(R.id.pager);
            tabs = (SlidingTabLayout) rootView.findViewById(R.id.tabs);



            adapter =  new ViewPagerAdapter(getFragmentManager(),Titles,Numboftabs);
            pager.setAdapter(adapter);
            tabs.setDistributeEvenly(true);
            tabs.setViewPager(pager);
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.accent_material_light);
                }


            });

            //GridView gridview = (GridView) rootView.findViewById(R.id.photogridview);

           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, numbers);

           // gridview.setAdapter(adapter);
            return rootView;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
           // GridView gridview = (GridView) this.getActivity().findViewById(R.id.photogridview);
            //gridview.setAdapter(new ButtonAdapter(getActivity().getApplicationContext()));

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



}
