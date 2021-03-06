package com.stone.myclass;

/**
 * Created by STONE on 2015/6/14.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.govjob.About;
import com.stone.govjob.FavoriteList;
import com.stone.govjob.OfficerExam;
import com.stone.govjob.QueryJob;
import com.stone.govjob.R;

//全體的共用Methods放這裡吧
//很怪Common明明不是Static..可是從外面可以直接呼叫..不用new
public class Common {

    //是否第一次進入該頁面 T:第一次進入; F:第二次進入
    public static boolean isNavigationDrawerFragmentCreate = false;

    //紀錄上次Intent的位置..當第一次進入時..切換Layout時要帶入這個
    public static int sectionNumber = 1;

    //紀錄目前的View
    public static View rootView = null;

    //紀錄目前的Context
    public static Context context = null;

    //紀錄目前的title
    public static CharSequence mTitle = null;

    public static void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = context.getString(R.string.title_section1);
                break;
            case 2:
                mTitle = context.getString(R.string.title_section2);
                break;
            case 3:
                mTitle = context.getString(R.string.title_section3);
                break;
            case 4:
                mTitle = context.getString(R.string.title_section4);
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        //private static Context _context;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Context context) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Common.context = context;

            //將編號記錄起來
            Common.sectionNumber = sectionNumber;
            //Log.i("MainActivity", Integer.toString(sectionNumber));
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Log.i("Common", "onCreateView");
            Log.i("Common", Integer.toString(Common.sectionNumber));


            Intent go;

            Log.i("Common", "isNavigationDrawerFragmentCreate = " + Common.isNavigationDrawerFragmentCreate);
            if (!Common.isNavigationDrawerFragmentCreate)
                Log.i("Common", "不是第一次進入該頁面");
            else
                Log.i("Common", "第一次進入該頁面");

            switch (Common.sectionNumber) {
                case 1:
                    //第一次進入該頁面..只需要設定Layout
                    if (Common.isNavigationDrawerFragmentCreate) {
                        Log.i("Common", "設定Layout queryjob");
                        //切換Layout
                        rootView = inflater.inflate(R.layout.queryjob, container, false);
                    }
                    //之後再觸發表示要切換Intent..
                    else {
                        Log.i("Common", "切換Intent queryjob");
                        //切換Activity
                        go = new Intent(Common.context, QueryJob.class);
                        this.startActivity(go);
                        ((Activity)Common.context).finish();
                    }
                    break;
                case 2:
                    //第一次進入該頁面..只需要設定Layout
                    if (Common.isNavigationDrawerFragmentCreate) {
                        Log.i("Common", "設定Layout Favorite");
                        //切換Layout
                        rootView = inflater.inflate(R.layout.joblist, container, false);
                    }
                    //之後再觸發表示要切換Intent..
                    else {
                        Log.i("Common", "切換Intent FavoriteList");
                        //切換Activity
                        go = new Intent(Common.context, FavoriteList.class);
                        this.startActivity(go);
                        ((Activity)Common.context).finish();
                    }
                    break;
                case 3:
                    //第一次進入該頁面..只需要設定Layout
                    if (Common.isNavigationDrawerFragmentCreate) {
                        Log.i("Common", "設定Layout OfficerExam");
                        //切換Layout
                        rootView = inflater.inflate(R.layout.joblist, container, false);
                    }
                    //之後再觸發表示要切換Intent..
                    else {
                        Log.i("Common", "切換Intent OfficerExam");
                        //切換Activity
                        go = new Intent(Common.context, OfficerExam.class);
                        this.startActivity(go);
                        ((Activity)Common.context).finish();
                    }
                    break;
                case 4:
                    //第一次進入該頁面..只需要設定Layout
                    if (Common.isNavigationDrawerFragmentCreate) {
                        Log.i("Common", "設定Layout about");
                        //切換Layout
                        rootView = inflater.inflate(R.layout.about, container, false);
                    }
                    //之後再觸發表示要切換Intent..
                    else {
                        Log.i("Common", "切換Intent about");
                        //切換Activity
                        go = new Intent(Common.context, About.class);
                        this.startActivity(go);
                        ((Activity)Common.context).finish();
                    }
                    break;
            }

            Common.isNavigationDrawerFragmentCreate = false;    //第一次狀態解除
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {


            super.onAttach(activity);
            Common.onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));



            /*
            //Log.i("Common", activity.getComponentName().toString());
            Log.i("Common", activity.getLocalClassName());
            //Log.i("Common", activity.getPackageName());

            switch (activity.getLocalClassName()) {

                case "QueryJob":
                    Log.i("Common", "onAttach QueryJob");
                    super.onAttach(activity);
                    ((QueryJob) activity).onSectionAttached(
                            getArguments().getInt(ARG_SECTION_NUMBER));
                    break;
                case "About":
                    Log.i("Common", "onAttach About");
                    super.onAttach(activity);
                    ((About) activity).onSectionAttached(
                            getArguments().getInt(ARG_SECTION_NUMBER));
                    break;
                case "FavoriteList":
                    Log.i("Common", "onAttach FavoriteList");
                    super.onAttach(activity);
                    ((FavoriteList) activity).onSectionAttached(
                            getArguments().getInt(ARG_SECTION_NUMBER));
                    break;
            }
            */

        }
    }
}
