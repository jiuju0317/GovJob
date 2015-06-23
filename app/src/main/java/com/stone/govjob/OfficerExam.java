package com.stone.govjob;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.stone.myclass.Common;
import com.stone.myclass.GovernmentOfficer;
import com.stone.myclass.NavigationDrawerFragment;
import com.stone.myclass.OfficerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;


public class OfficerExam extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //region NavigationDrawerFragment..這段不要修改

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    Reader rd = null;
    StringBuilder str = new StringBuilder();
    ArrayList<GovernmentOfficer> governmentOfficers = new ArrayList<>();
    ListView listView ;
    private Context context;

    /**
     *
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    //private CharSequence mTitle;

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, Common.PlaceholderFragment.newInstance(position + 1, this))
                .commit();
    }


    public void restoreActionBar() {
        Log.i("About", "restoreActionBar");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(Common.mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            Log.i("About", "onCreateOptionsMenu change layout");
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
        Log.i("About", "onOptionsItemSelected");
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

        Log.i("About", "onCreate");

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        //Common.mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    public void onResume() {

        listView =(ListView) Common.rootView.findViewById(R.id.listView);
        context = this;
        String examPlan = null;
        AssetManager manager = this.getAssets();

        try {
            InputStream is = manager.open("rptExamInfo.xls");
            rd = new InputStreamReader(is, "UTF-8");

            char[] buffer = new char[1024];
            while (rd.read(buffer) != -1)

            {

                str.append(new String(buffer));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        examPlan=str.toString();
        examPlan = examPlan + "</td>";
        System.out.println(examPlan);

        int a = examPlan.indexOf("<tr style='height:28.50pt'");
        String first_segment = null;
        first_segment = examPlan.substring(a);



        String[] b = new String[40];
        b=first_segment.split("<tr style='height:28.50pt'>");


        for (int y = 1; y < b.length; y++) {

            String[] list = b[y].split("<td class=xl31>");
            for (int i = 1; i < list.length; i++) {
                int k = list[i].indexOf("</td>");
                list[i] = list[i].substring(0, k);

            }


            GovernmentOfficer g = new GovernmentOfficer();
            g.setSerialNumber(y);
            g.setName(list[1]);
            g.setLevel(list[2]);
            g.setRegisterDate(list[3]);
            g.setTestDate(list[4]);
            list[5]=list[5].replace("<br>","");
            g.setNote(list[5]);

            // g.showAllItem(g);

            governmentOfficers.add(g);

        }

//        ListIterator it = governmentOfficers.listIterator();
//
//        while (it.hasNext()) {
//
//            GovernmentOfficer gg = (GovernmentOfficer) it.next();
//            gg.showAllItem(gg);
//
//
//        }

        listView.setAdapter(new OfficerAdapter(context, governmentOfficers));

        //  listView.setOnItemClickListener(new MyOnItemClickListener());

        super.onResume();
    }

}
