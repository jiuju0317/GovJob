package com.stone.govjob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.stone.myclass.DataHandler;
import com.stone.myclass.Job;
import com.stone.myclass.JobDAO;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends ActionBarActivity {

    ArrayList<Job> xmljobs,dbjobs;
    int annoDate;//version.xml取得的版本號
    private JobDAO jobDAO;

    private String versionURL = "http://meamea.byethost5.com/version.xml";//版本號ANNOUNCE_DATE
    private String dataURL = "http://meamea.byethost5.com/data.xml";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        // 建立資料庫物件
        jobDAO = new JobDAO(getApplicationContext());

        Log.i("MainActivity", "onCreate");

        //Loading();


        //Loading區段
        // 建立並顯示進度對話窗
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setTitle("執行中");
        this.progressDialog.setMessage("請稍後...");
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.progressDialog.show();

        // 建立 worker thread，並交付由 main thread 建立的 handler
        // 供 worker thread 稍後與 main thread 溝通
        new Thread(new LoadingXMLThread(this.handler)).start();


    }

    //=====解析XML並塞入資料庫====================
    public void parseXML(){
        SAXParserFactory spf = SAXParserFactory.newInstance();
        int DBAnnoDate = jobDAO.getDBAnnoDate();

        try {
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            DataHandler dataHandler = new DataHandler();
            xr.setContentHandler(dataHandler);

            //取得版本號inputstream
            HttpURLConnection httpURLConnection_version = (HttpURLConnection) new URL(versionURL).openConnection();
            //httpURLConnection.connect();
            InputStream version_is = httpURLConnection_version.getInputStream();

            //取得data inputstream
            HttpURLConnection httpURLConnection_data = (HttpURLConnection) new URL(dataURL).openConnection();
            //httpURLConnection_data.connect();
            InputStream data_is = httpURLConnection_data.getInputStream();

            xr.parse(new InputSource(version_is));
            annoDate = dataHandler.getAnnounceDate();

            Log.d("annoDate", Integer.toString(annoDate));
            Log.d("DBannoDate",Integer.toString(jobDAO.getDBAnnoDate()));

            if(DBAnnoDate==0){ //第一次塞資料
                xr.parse(new InputSource(data_is));
                xmljobs = dataHandler.getJobs();

                for (int i = 0; i < xmljobs.size(); i++) {
                    Job xmlgetjob = xmljobs.get(i);
                    jobDAO.insert(xmlgetjob); //塞入資料庫
                }
                jobDAO.insertDBAnnoDate(annoDate);
            }else if(annoDate>jobDAO.getDBAnnoDate()){ //更新資料
                xr.parse(new InputSource(data_is));
                xmljobs = dataHandler.getJobs();

                for (int i = 0; i < xmljobs.size(); i++) {
                    Job xmlgetjob = xmljobs.get(i);
                    jobDAO.insert(xmlgetjob); //塞入資料庫
                }
                jobDAO.setDBAnnoDate(annoDate);
            }

            httpURLConnection_version.disconnect();
            httpURLConnection_data.disconnect();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //=====執行塞資料庫，塞完告訴Loading畫面關閉====================

    public class LoadingXMLThread implements Runnable {

        private Handler handler;

        public LoadingXMLThread(Handler handler) {
            super();
            this.handler = handler;
        }

        @Override
        public void run() {

            //塞資料庫
            parseXML();

            // 丟一個 message 到 main queue 裡，由 main thread 接手處理
            this.handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // main thread 從 main queue 取得 handler 丟進來的 message
            // 再從該 message 取得 handler 物件
            // 最後呼叫 handler 的 callback method - handleMessage 來關閉進度對話窗
            progressDialog.dismiss();
            //導至搜尋頁面
            Intent go = new Intent(MainActivity.this, QueryJob.class);
            MainActivity.this.startActivity(go);
            MainActivity.this.finish();

        }
    };
}
