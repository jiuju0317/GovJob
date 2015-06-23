package com.stone.govjob;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.stone.myclass.Common;
import com.stone.myclass.Job;
import com.stone.myclass.JobDAO;
import com.stone.myclass.NavigationDrawerFragment;

import java.util.ArrayList;


public class QueryJob extends ActionBarActivity
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
        Fragment frg1 = Common.PlaceholderFragment.newInstance(position + 1, this);
        //rootview = frg1.getView();
        fragmentManager.beginTransaction()
                .replace(R.id.container, frg1)
                .commit();
    }


    public void restoreActionBar() {
        Log.i("QueryJob", "restoreActionBar");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(Common.mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            Log.i("QueryJob", "onCreateOptionsMenu change layout");
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
        Log.i("QueryJob", "onOptionsItemSelected");
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

        Log.i("QueryJob", "onCreate");

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

        query_btn = (Button) Common.rootView.findViewById(R.id.query_btn);

        View.OnClickListener myListener = new beginQuery();
        query_btn.setOnClickListener(myListener);

        //Spinner區段

        workPlace_spn = (Spinner) Common.rootView.findViewById(R.id.workPlace_spn);
        personKind_spn = (Spinner) Common.rootView.findViewById(R.id.personKind_spn);
        keyWord_edt = (EditText) Common.rootView.findViewById(R.id.keyWord_edt);

        workPlace_adp = ArrayAdapter.createFromResource(this, R.array.workPlace, android.R.layout.simple_spinner_item);
        workPlace_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workPlace_spn.setAdapter(workPlace_adp);
        workPlace_spn.setOnItemSelectedListener(new spinnerListener());

        personKind_adp = ArrayAdapter.createFromResource(this, R.array.personKind, android.R.layout.simple_spinner_item);
        personKind_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personKind_spn.setAdapter(personKind_adp);
        personKind_spn.setOnItemSelectedListener(new spinnerListener());

        super.onResume();
    }

    //=====處理spinner選項====================
    class spinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId() == R.id.workPlace_spn) {
                queryWorkPlace = workPlace_adp.getItem(position).toString();
                Resources arrays =getResources();
                String[] arrWorkPlace=arrays.getStringArray(R.array.workPlace);

                if(queryWorkPlace.equals(arrWorkPlace[0])){
                    queryWorkPlace = "";
                }else if(queryWorkPlace.equals(arrWorkPlace[1])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[2]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[3]+"%'"
                            +") AND ";
                }else if(queryWorkPlace.equals(arrWorkPlace[4])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[5]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[6]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[7]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[8]+"%'"
                            +") AND ";
                }else if(queryWorkPlace.equals(arrWorkPlace[9])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[10]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[11]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[12]+"%'"
                            +") AND ";
                }else if(queryWorkPlace.equals(arrWorkPlace[13])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[14]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[15]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[16]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[17]+"%'"
                            +") AND ";
                }else if(queryWorkPlace.equals(arrWorkPlace[18])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[19]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[20]+"%'"
                            +") AND ";
                }else if(queryWorkPlace.equals(arrWorkPlace[21])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[22]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[23]+"%'"
                            +") AND ";
                }else if(queryWorkPlace.equals(arrWorkPlace[24])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[25]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[26]+"%'"
                            +") AND ";
                }else if(queryWorkPlace.equals(arrWorkPlace[27])){
                    queryWorkPlace = "("
                            +JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[28]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[29]+"%'"
                            +" OR "+JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+arrWorkPlace[30]+"%'"
                            +") AND ";
                }else{
                    queryWorkPlace = JobDAO.COLUMN_WORK_PLACE_TYPE+" LIKE '%"+queryWorkPlace+"%' AND ";
                }


            }else if (parent.getId()== R.id.personKind_spn){
                queryPersonKind = personKind_adp.getItem(position).toString();
                Log.i("personkind ===>", queryPersonKind);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    //=====查詢====================
    class beginQuery implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            queryKeyWord = keyWord_edt.getText().toString();
            Log.i("Start Querying!!!","!!!!!!!");

            String where ="("+queryWorkPlace
                    +JobDAO.COLUMN_PERSON_KIND+" LIKE '%"+ queryPersonKind +"%' "
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

            /*
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
            */

            //ArrayList<Job> queryResults = jobDAO.queryJob(where);

            int cnt = jobDAO.getCount(where);

            if (cnt > 0) {
                Intent go = new Intent(QueryJob.this, JobList.class);
                go.putExtra("where", where);
                QueryJob.this.startActivity(go);
                //((Activity)QueryJob.this).finish();
            }
            else{
                Toast.makeText(QueryJob.this, "查無資料!請重新查詢!", Toast.LENGTH_SHORT).show();
            }


        }
    }




    public void goexam (View view){
        Intent intent = new Intent();
        String addressS="";
        switch (view.getId()) {

            case R.id.officer : // 測試Intent 換頁


                intent.setClass(QueryJob.this, OfficerExam_old.class);

                startActivity(intent);

                break;





        }


    }//end goexam function





}
