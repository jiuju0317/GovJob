package com.stone.govjob;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends ActionBarActivity {

    ArrayList<Job> xmljobs,dbjobs;
    int annoDate;
    private JobDAO jobDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        // 建立資料庫物件
        jobDAO = new JobDAO(getApplicationContext());

        Log.i("MainActivity", "onCreate");

        //Loading();

        //導至搜尋頁面
        Intent go = new Intent(this, QueryJob.class);
        this.startActivity(go);
        this.finish();
    }



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
}
