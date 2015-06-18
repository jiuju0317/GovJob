package com.stone.govjob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.myclass.Job;
import com.stone.myclass.JobDAO;
import com.stone.myclass.LatLngQuery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Entrace on 2015/6/17.
 */


public class JobDetail extends Activity {

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


   // ListView listView ;

    TextView id_;
    TextView title_;
    TextView sysnam;
    TextView org_name;
    TextView work_place;
    TextView WORK_ADDRESS;
    TextView RANK;
    TextView type;
    TextView NUMBER_OF;
    TextView GENDER_TYPE;
    TextView DATE_FROM;
    TextView DATE_TO;
    TextView WORK_QUALITY;
    TextView WORK_ITEM;
    TextView CONTACT_METHOD;
    TextView VIEW_URL;

    Job job;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetail);
       // listView =(ListView) findViewById(R.id.listView);

        id_=(TextView)findViewById(R.id.id);
        title_=(TextView)findViewById(R.id.title);
        sysnam=(TextView)findViewById(R.id.sysnam);
        org_name=(TextView)findViewById(R.id.org_name);
        work_place=(TextView)findViewById(R.id.work_place);
        WORK_ADDRESS=(TextView)findViewById(R.id.WORK_ADDRESS);
        RANK=(TextView)findViewById(R.id.RANK);
        type=(TextView)findViewById(R.id.type);
        NUMBER_OF=(TextView)findViewById(R.id.NUMBER_OF);
        GENDER_TYPE=(TextView)findViewById(R.id.GENDER_TYPE);
        DATE_FROM=(TextView)findViewById(R.id.DATE_FROM);
        DATE_TO=(TextView)findViewById(R.id.DATE_TO);
        WORK_QUALITY=(TextView)findViewById(R.id.WORK_QUALITY);
        WORK_ITEM=(TextView)findViewById(R.id.WORK_ITEM);
        CONTACT_METHOD=(TextView)findViewById(R.id.CONTACT_METHOD);
        VIEW_URL=(TextView)findViewById(R.id.VIEW_URL);





        context = this;

        jobDAO = new JobDAO(getApplicationContext());

        int job_id;
        job_id = this.getIntent().getExtras().getInt("job_id");
        //Toast.makeText(this,add,Toast.LENGTH_SHORT).show();
        System.out.println("------------>" + job_id);
      //  textView.setText(add);

         job = jobDAO.queryJob(job_id);


        id_.setText(Integer.toString(job.get_id()));
        title_.setText(job.getTitle());
        sysnam.setText(job.getSysnam());
        org_name.setText(job.getOrgName());
        work_place.setText(job.getWorkPlaceType());
        WORK_ADDRESS.setText(job.getWorkAddress());
        RANK.setText(job.getRank());
        type.setText(job.getType());

        NUMBER_OF.setText(job.getNumberOf());
        GENDER_TYPE.setText(job.getGenderType());

        DATE_FROM.setText(new SimpleDateFormat("yyyy-MM-dd").format(job.getDateFrom()));
        DATE_TO.setText(new SimpleDateFormat("yyyy-MM-dd").format(job.getDateTo()));

        WORK_QUALITY.setText(job.getWorkQuality());
        WORK_ITEM.setText(job.getWorkItem());
        CONTACT_METHOD.setText(job.getContactMethod());
        VIEW_URL.setText(job.getViewUrl());






/*
        Job job = jobs.get(1);

        Date date =job.getDateFrom();
     //   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        System.out.println(dateString);
*/
        //  System.out.println("------------>" + job.getTitle());


      //  listView.setAdapter(new JobsAdapter(context, jobs));


       // listView.setOnItemClickListener(new MyOnItemClickListener());





    }//end onCreate









/*
    @Override // 回來呼叫的函數
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3234){
            if (resultCode == 1234){
                // string = data.getExtras().getString("ad");
                // textView.setText(string);



            }
        }




    }//end onActivityResult

*/

    public void  gotomap (View view){
        LatLngQuery latLngQuery = new LatLngQuery();
        System.out.println("--------touch---->");
        String address = null;

        try {
            address = URLEncoder.encode(job.getWorkAddress(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(latLngQuery.returnLatLng(address)!=null) {

            Intent intent = new Intent();
            intent.putExtra("address", job.getWorkAddress());
            intent.setClass(JobDetail.this, JobDetail_Map.class);

            startActivityForResult(intent, 3234);

        }else{
            Toast.makeText(JobDetail.this, "此地址無法查詢。", Toast.LENGTH_SHORT).show();
        }

    }



  /*
    public void  markalladd (View view){




        Intent intent = new Intent();
//        intent.putExtra("address","新北市中和區");




        Bundle extras = new Bundle();
        extras.putInt("account", listView.getCount());
        for (int i = 0; i<listView.getCount(); i++) {
            String s;
            s="address" + i;
            extras.putString(s, listView.getItemAtPosition(i).toString());
            //    System.out.println("------------>" + listView.getItemAtPosition(i).toString());

        }

        intent.putExtras(extras);
        intent.setClass(arraylistToMap3.this, arraylistToMap_markAll.class);
        startActivityForResult(intent, 3234);





    }
*/

} // sinfglejobResult Activity