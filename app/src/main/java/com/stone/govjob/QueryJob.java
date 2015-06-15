package com.stone.govjob;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.stone.myclass.DataHandler;
import com.stone.myclass.Job;
import com.stone.myclass.JobDAO;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class QueryJob extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //============================
    ArrayList<Job> xmljobs,queryResults;
    int annoDate;
    private JobDAO jobDAO;
    String queryWorkPlace,queryPersoKind,queryKeyWord;

    Spinner workPlace_spn,personKind_spn;
    EditText keyWord_edt;
    ArrayAdapter workPlace_adp,personKind_adp;
    Button query_btn;
    //============================

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     *
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryjob);

        //==================================
        // 建立資料庫物件
        jobDAO = new JobDAO(getApplicationContext());

        query_btn = (Button) this.findViewById(R.id.query_btn);
        View.OnClickListener myListener = new beginQuery();
        query_btn.setOnClickListener(myListener);

        //Spinner區段

        workPlace_spn = (Spinner) this.findViewById(R.id.workPlace_spn);
        personKind_spn = (Spinner) this.findViewById(R.id.personKind_spn);
        keyWord_edt = (EditText) this.findViewById(R.id.keyWord_edt);

        workPlace_adp = ArrayAdapter.createFromResource(this, R.array.workPlace, android.R.layout.simple_spinner_item);
        workPlace_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workPlace_spn.setAdapter(workPlace_adp);
        workPlace_spn.setOnItemSelectedListener(new spinnerListener());

        personKind_adp = ArrayAdapter.createFromResource(this, R.array.personKind, android.R.layout.simple_spinner_item);
        personKind_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personKind_spn.setAdapter(personKind_adp);
        personKind_spn.setOnItemSelectedListener(new spinnerListener());
        //==================================

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    //==========================
    public void parseXML(){
        SAXParserFactory spf = SAXParserFactory.newInstance();

        try {
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            DataHandler dataHandler = new DataHandler();
            xr.setContentHandler(dataHandler);
            InputStream is =this.getAssets().open("data.xml");
            xr.parse(new InputSource(is));

            xmljobs = dataHandler.getJobs();
            annoDate = dataHandler.getAnnounceDate();


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<xmljobs.size();i++){
            Job xmlgetjob = xmljobs.get(i);
            jobDAO.insert(xmlgetjob); //塞入資料庫
        }

    }

    class spinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId() == R.id.workPlace_spn) {
                queryWorkPlace = workPlace_adp.getItem(position).toString();
                Log.i("workplace ===>", queryWorkPlace);
            }else if (parent.getId()== R.id.personKind_spn){
                queryPersoKind = personKind_adp.getItem(position).toString();
                Log.i("personkind ===>",queryPersoKind);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class beginQuery implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            queryKeyWord = keyWord_edt.getText().toString();
            Log.i("Start Querying!!!","!!!!!!!");
            /*
            int id = Integer.parseInt(queryKeyWord);
            Job job = jobDAO.queryJob(id);
            Log.i("job=>",job.toString());
            */

            String where ="("
                    +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+queryWorkPlace+"%' "
                    +" AND "+JobDAO.COLUMN_PERSON_KIND+" LIKE '%"+queryPersoKind+"%' "
                    +")";
            String where_keyword =
                    " AND ( "+JobDAO.COLUMN_ORG_NAME+" LIKE '%"+queryKeyWord+"%' "
                            +" OR "+JobDAO.COLUMN_RANK+" LIKE '%"+queryKeyWord+"%' "
                            +" OR "+JobDAO.COLUMN_TITLE+" LIKE '%"+queryKeyWord+"%' "
                            +" OR "+JobDAO.COLUMN_TYPE+" LIKE '%"+queryKeyWord+"%' "
                            +" OR "+JobDAO.COLUMN_WORK_QUALITY+" LIKE '%"+queryKeyWord+"%' "
                            +" OR "+JobDAO.COLUMN_WORK_ITEM+" LIKE '%"+queryKeyWord+"%' "
                            +" OR "+JobDAO.COLUMN_WORK_ADDRESS+" LIKE '%"+queryKeyWord+"%' "
                            +" OR "+JobDAO.COLUMN_CONTACT_METHOD+" LIKE '%"+queryKeyWord+"%' "
                            +")";
            if(!queryKeyWord.equals("")){
                where = where + where_keyword;
            }

            ArrayList<Job> queryResults = jobDAO.queryJob(where);

            if(queryResults != null){
                Log.i("queryResults.size=",Integer.toString(queryResults.size()));
                for(int i =0;i<queryResults.size();i++){
                    Log.i("queryResults.size = ",Integer.toString(queryResults.size()));
                    Log.i("queryResults["+i+"] = ",queryResults.get(i).toString());
                }

            }else{
                Log.i("queryResults","queryResults = null!!");
            }



        }
    }
    //==========================

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        private static int _sectionNumber;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            _sectionNumber = sectionNumber;
            //Log.i("Stone", Integer.toString(sectionNumber));
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //Log.i("Stone", "onCreateView");
            //int state = savedInstanceState.getInt(ARG_SECTION_NUMBER);

            Log.i("Stone", Integer.toString(_sectionNumber));

            View rootView = null;
            switch (_sectionNumber) {
                case 1:
                    rootView = inflater.inflate(R.layout.queryjob, container, false);

                    /*
                    this.
                    getActivity().
                    getSupportFragmentManager().
                    beginTransaction().replace(R.id.container,new data()).
                    commit();
                    */
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.favorite, container, false);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.about, container, false);
                    break;
            }


            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((QueryJob) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }

}
