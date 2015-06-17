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
        setContentView(R.layout.arraylisttomap2);
        listView =(ListView) findViewById(R.id.listView);


        context = this;

        jobDAO = new JobDAO(getApplicationContext());


        String where = this.getIntent().getExtras().getString("where");
        ArrayList<Job> jobs = jobDAO.queryJob(where);








/*
        Job job = jobs.get(1);

        Date date =job.getDateFrom();
     //   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        System.out.println(dateString);
*/
        //  System.out.println("------------>" + job.getTitle());


        listView.setAdapter(new JobsAdapter(context, jobs));


        listView.setOnItemClickListener(new MyOnItemClickListener());
    }//end onCreate




    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            Job coffee = (Job)parent.getItemAtPosition(position);

            Intent intent = new Intent();
            intent.setClass(JobList.this, JobDetail.class);
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
