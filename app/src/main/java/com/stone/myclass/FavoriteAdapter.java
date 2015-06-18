package com.stone.myclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stone.govjob.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Entrace on 2015/6/17.
 */
public class FavoriteAdapter extends BaseAdapter{

    private LayoutInflater myInflatter;
    private ArrayList<Job> jobs;

    public FavoriteAdapter(Context context, ArrayList<Job> list){

        myInflatter=LayoutInflater.from(context);
        jobs=list;

    }


    @Override
    public int getCount() {
        return jobs.size();
        //return 1;
    }

    @Override
    public Object getItem(int position) {
        return jobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parnet) {

        ViewHolder holder;



        if (convertview == null){


           convertview = myInflatter.inflate(R.layout.favorite_list_block,null);
           holder = new ViewHolder();
            holder.id = (TextView) convertview.findViewById(R.id.id);
            holder.datefrom = (TextView) convertview.findViewById(R.id.datefrom);
            holder.title = (TextView) convertview.findViewById(R.id.title);
            holder.sysnam = (TextView) convertview.findViewById(R.id.sysnam);
            holder.orgname = (TextView) convertview.findViewById(R.id.orgname);
            holder.workplace = (TextView) convertview.findViewById(R.id.workplace);
            holder.deadline = (TextView) convertview.findViewById(R.id.deadline);
            holder.workquality= (TextView) convertview.findViewById(R.id.workquality);

            convertview.setTag(holder);

        }else{

           holder = (ViewHolder) convertview .getTag();

        }

        //取得Position所在的一筆資料
        Job job = jobs.get(position);

      //  System.out.println("------------>" + job.get_id());
      //  System.out.println("------------>" + job.getTitle());
/*
        holder.id.setText(job.get_id());
        //holder.datefrom.setText(job.getDateFrom());
        holder.datefrom.setText(null);
        holder.title.setText(job.getTitle());
        holder.sysnam.setText(job.getSysnam());
        holder.orgname.setText(job.getOrgName());
        holder.workplace.setText(job.getWorkPlaceType());
        //holder.deadline.setText(job.getDateTo());
        holder.deadline.setText(null);
        holder.workquality.setText(job.getWorkQuality());

   */


        String datefromS =new SimpleDateFormat("yyyy-MM-dd").format(job.getDateFrom());
        String a ="刊登日期:";
        a = a+datefromS;

        String dateToS =new SimpleDateFormat("yyyy-MM-dd").format(job.getDateTo());
        String b ="截止日期:";
        b = b+dateToS;

        holder.id.setText(Integer.toString(job.get_id()));
        holder.datefrom.setText(a);
        holder.title.setText(job.getTitle());
        holder.sysnam.setText(job.getSysnam());
        holder.orgname.setText(job.getOrgName());
        holder.workplace.setText(job.getWorkPlaceType());
        holder.deadline.setText(b);
        holder.workquality.setText(job.getWorkQuality());

        return convertview;


    }


    static class ViewHolder{
        TextView id;
        TextView datefrom;
        TextView title;
        TextView sysnam;
        TextView orgname;
        TextView workplace;
        TextView deadline;
        TextView workquality;
    }

}// end JobsAdapter
