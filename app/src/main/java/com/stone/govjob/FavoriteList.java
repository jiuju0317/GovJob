package com.stone.govjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.stone.myclass.Common;
import com.stone.myclass.FavoriteAdapter;
import com.stone.myclass.Job;
import com.stone.myclass.JobDAO;
import com.stone.myclass.JobsAdapter;
import com.stone.myclass.NavigationDrawerFragment;

import java.util.ArrayList;


public class FavoriteList extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //region NavigationDrawerFragment..這段不要修改

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     *
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    //private CharSequence mTitle;

    //View rootview;
    ArrayList<Job> xmljobs,queryResults;
    int annoDate;
    private JobDAO jobDAO;
    String queryWorkPlace, queryPersonKind,queryKeyWord;

    Spinner workPlace_spn,personKind_spn;
    EditText keyWord_edt;
    ArrayAdapter workPlace_adp,personKind_adp;
    Button query_btn;


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, Common.PlaceholderFragment.newInstance(position + 1, this))
                .commit();
    }


    public void restoreActionBar() {
        Log.i("Favorite", "restoreActionBar");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(Common.mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            Log.i("Favorite", "onCreateOptionsMenu change layout");
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
        Log.i("Favorite", "onOptionsItemSelected");
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

    //endregion NavigationDrawerFragment..這段不要修改

    //從這裡開始寫Code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        Log.i("Favorite", "onCreate");

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        //Common.mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // 建立資料庫物件
        jobDAO = new JobDAO(getApplicationContext());
    }

    public void onResume() {

        //ArrayList<Job> jobs = jobDAO.getAll();
        ArrayList<Job> jobs = jobDAO.queryAllFavorite();

        if (!jobs.isEmpty()) {
            ListView listView = (ListView) Common.rootView.findViewById(R.id.listView);

            listView.setAdapter(new FavoriteAdapter(this, jobs));

            listView.setOnItemClickListener(new MyOnItemClickListener());
        }
        else {
            Toast.makeText(this, "目前無儲存我的最愛!", Toast.LENGTH_SHORT).show();
        }

        super.onResume();
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            Job coffee = (Job)parent.getItemAtPosition(position);

            Intent intent = new Intent();
            intent.setClass(FavoriteList.this, JobDetail.class);
            intent.putExtra("job_id", coffee.get_id());
            startActivityForResult(intent, 3234);

                 /*
                // 取得被點選之資料
                Job coffee = (Job)parent.getItemAtPosition(position);
                // 取出資料id, title
                String msg = "您選的是:" + coffee.get_id() + ", title" + coffee.getTitle();
                // Toast 顯示
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
              */

        }
    }
}

