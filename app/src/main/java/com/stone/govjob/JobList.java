package com.stone.govjob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.stone.myclass.Job;
import com.stone.myclass.JobDAO;
import com.stone.myclass.JobsAdapter;
import com.stone.myclass.Common;
import com.stone.myclass.NavigationDrawerFragment;

import java.util.ArrayList;


/**
 * Created by Entrace on 2015/6/15.
 */


//課本的方法
//self draw listView
//使用listView預設的layout條列出三筆地址
//可以查詢單筆地址
//也可以查詢全部結果地址


public class JobList extends Activity {

       //從這裡開始寫Code


    ArrayList<Job> xmljobs,queryResults;
    int annoDate;
    private JobDAO jobDAO;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // main thread 從 main queue 取得 handler 丟進來的 message
            // 再從該 message 取得 handler 物件
            // 最後呼叫 handler 的 callback method - handleMessage 來關閉進度對話窗
            progressDialog.dismiss();
        }
    };




    ListView listView ;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        Log.i("About", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.arraylisttomap2);
        listView =(ListView) findViewById(R.id.listView);


        context = this;

        jobDAO = new JobDAO(getApplicationContext());
        String where = this.getIntent().getExtras().getString("where");
        ArrayList<Job> jobs = jobDAO.queryJob(where);

//        System.out.println("------------>" + jobs.size());
//
//        Job job = jobs.get(1);
//        System.out.println("------------>" + job.get_id());
//        System.out.println("------------>" + job.getTitle());


         listView.setAdapter(new JobsAdapter(context,jobs));





      /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // TODO Auto-generated method stub

                ListView listView = (ListView) adapterView;

                Intent intent = new Intent();


                intent.putExtra("address",listView.getItemAtPosition(i).toString());


                intent.setClass(arraylistToMap2.this, arraylistToMap_markSingle.class);

                startActivityForResult(intent, 3234);

            }
        });
*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout listView = (LinearLayout) view;
                String id = (String) ((TextView)listView.findViewById(R.id.id)).getText();

                Intent intent = new Intent();
                intent.putExtra("id", id);

                intent.setClass(JobList.this, JobDetail.class);
                startActivityForResult(intent, 3234);
            }
        });

    }//end onCreate

}
