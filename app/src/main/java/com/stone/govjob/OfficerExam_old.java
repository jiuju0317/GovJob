package com.stone.govjob;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.stone.myclass.GovernmentOfficer;
import com.stone.myclass.OfficerAdapter;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Entrace on 2015/6/11.
 */
public class OfficerExam_old extends FragmentActivity {

    Reader rd = null;
    StringBuilder str = new StringBuilder();
    ArrayList<GovernmentOfficer> governmentOfficers = new ArrayList<>();
    ListView listView ;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joblist);

        listView =(ListView) findViewById(R.id.listView);
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


    }

} //end Acitivity