package com.stone.myclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stone.govjob.R;
import com.stone.myclass.GovernmentOfficer;

import java.util.ArrayList;

/**
 * Created by Entrace on 2015/6/22.
 */
public class OfficerAdapter extends BaseAdapter {

    private LayoutInflater myInflatter;
    private ArrayList<GovernmentOfficer> governmentOfficers;

    public OfficerAdapter(Context context, ArrayList<GovernmentOfficer> governmentOfficers){

        myInflatter=LayoutInflater.from(context);
        this.governmentOfficers=governmentOfficers;

    }


    @Override
    public int getCount() {
        return governmentOfficers.size();
        //return 1;
    }

    @Override
    public Object getItem(int position) {
        return governmentOfficers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parnet) {

        ViewHolder holder;



        if (convertview == null){


            convertview = myInflatter.inflate(R.layout.listforofficer,null);
            holder = new ViewHolder();
            holder.serialNumber = (TextView) convertview.findViewById(R.id.serialNumber);
            holder.name = (TextView) convertview.findViewById(R.id.name);
            holder.level= (TextView) convertview.findViewById(R.id.level);
            holder.registerDay = (TextView) convertview.findViewById(R.id.rday);
            holder.testDay = (TextView) convertview.findViewById(R.id.tday);
            holder.note = (TextView) convertview.findViewById(R.id.note);


            convertview.setTag(holder);

        }else{

            holder = (ViewHolder) convertview .getTag();

        }

        //取得Position所在的一筆資料
        GovernmentOfficer g = governmentOfficers.get(position);

        //  System.out.println("------------>" + job.get_id());
        //  System.out.println("------------>" + job.getTitle());


      //  String datefromS =new SimpleDateFormat("yyyy-MM-dd").format(job.getDateFrom());
        String a ="刊登日期:";
       // a = a+datefromS;

       // String dateToS =new SimpleDateFormat("yyyy-MM-dd").format(job.getDateTo());
        String b ="截止日期:";
       // b = b+dateToS;

        holder.serialNumber.setText(Integer.toString(g.getSerialNumber()));
        holder.name.setText(g.getName());
        holder.level.setText(g.getLevel());
        holder.registerDay.setText(g.getRegisterDate());
        holder.testDay.setText(g.getTestDate());
        holder.note.setText(g.getNote());





        return convertview;


    }


    static class ViewHolder{
        TextView serialNumber;
        TextView name;
        TextView level;
        TextView registerDay;
        TextView testDay;
        TextView note;

    }




}// end JobsAdapter