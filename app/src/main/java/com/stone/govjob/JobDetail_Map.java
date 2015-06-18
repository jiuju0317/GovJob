package com.stone.govjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by Entrace on 2015/6/11.
 */


//輸入中文地址取的google map JSON 檔案 解析 經緯度 之後在地圖上ＭＡＲＫ
//整合函數
public class JobDetail_Map extends FragmentActivity {
    TextView textView;

    //String add = "http://maps.googleapis.com/maps/api/geocode/json?address=";

    String string=null;
    InputStream is = null;
    Reader rd = null;
    StringBuilder str = new StringBuilder();

    LatLng latLngReturn= null;

    public GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetail_map);
        mMap=setUpMapIfNeeded(mMap);
        //   textView = (TextView) findViewById(R.id.textView7);
       /*  String kk = this.getIntent().getExtras().getString("address");

        try {
            kk= URLEncoder.encode(kk,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        add = add + kk;
       */

        //從上一夜接中文地址
        try {
            //add=add+URLEncoder.encode(this.getIntent().getExtras().getString("address"),"UTF-8");
            string = URLEncoder.encode(this.getIntent().getExtras().getString("address"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        latLngReturn=returnLatLng (string);
        // string = GoogleMapURLModify(string);
        // textView.setText(add);

        // String json= null;

//        try {
//            json = getJson(string);
//
//            // json = toUtf8(json);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //   textView.setText(json);


        ////////////////////////////////////////////////////////////////////////
   /*
        String jsonStr=json;
        LatLng latLng =null;
        JSONArray resuluts = null;


        try {
               resuluts = new JSONObject(jsonStr).getJSONArray("results");

            System.out.println("------------>" + resuluts.length());



            if (resuluts.length() >= 1) {


                JSONObject jsonObject = resuluts.getJSONObject(0);
                JSONObject laln = jsonObject.getJSONObject("geometry").getJSONObject("location");
                latLng = new LatLng(Double.parseDouble(laln.getString("lat")),
                        Double.parseDouble(laln.getString("lng")));

              // String lat=laln.getString("lat");
               //String lng=laln.getString("lng");

                System.out.println("------------>" + latLng);


            }

            } catch (JSONException e) {
                    e.printStackTrace();
                }

*/
/////////////////////////////////////////////////////////////////////////////////

        // latLngReturn=getLocationByAddress(json);
        // System.out.println("------------>" + latLngReturn);


//////////////////////////////////////////////////////////////
//        MarkerOptions options = new MarkerOptions();
//        options.position(latLngReturn);
//        mMap.addMarker(options);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngReturn,15));
///////////////////////////////////////////////////////////////

        mMap = addMapMark(mMap,latLngReturn);
        // addMapMark(latLngReturn);


        // textView.setText(latLngReturn);

        // double x= latLngReturn;
        //System.out.print(x);

        //System.out.print(latLngReturn+",,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        //System.out.print("----------------,,,,,,,,,,,,,,,,,,,,,,,,,,,,");


    } //onCreate


/*
    @Override
    protected void onResume() {
        super.onResume();

    }
*/
/////////////////原來範例///////////////////////////////////////
/*
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
        }
    }
*/
/////////////////原來範例改輸入有ＭＡＰ參數//////////////////////////////////////////

    private GoogleMap setUpMapIfNeeded(GoogleMap googleMap) {

        GoogleMap gMap=googleMap;
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

        }
        return  gMap;
    }





//    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//    }


    //輸入中文地址串成 google map url
    public String GoogleMapURLModify (String s){

        String add = "http://maps.googleapis.com/maps/api/geocode/json?address=";
        add = add + s;

        return add;
    }



    //輸入（地址） url 回傳（地址）Json
    public String getJson (final String urlString) {
        // String s="";
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    URL url = new URL(urlString);

                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setRequestProperty("User-agent", "IE/6.0");
                    is = urlConnection.getInputStream();
                    rd = new InputStreamReader(is, "UTF-8");

                    char[] buffer = new char[1024];
                    while (rd.read(buffer) != -1)

                    {

                        str.append(new String(buffer));


                    }
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        thread.start();
        try {
            thread.join();
            //Toast.makeText(six.this,str.toString(),Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        //  s=str.toString();
        //s= toUtf8(s);
        //return s;
        return str.toString();

    } //end function get



    //傳入（地址）Json回傳經緯度
    public LatLng getLocationByAddress (String jsonStr){

        LatLng latLng =null;

        try {
            JSONArray resuluts = new JSONObject(jsonStr).getJSONArray("results");
            System.out.println("----------------------------------------->"+resuluts.length() );

            if (resuluts.length()>=1){

                System.out.println("----------------------------------------->" );
                JSONObject jsonObject = resuluts.getJSONObject(0);
                JSONObject laln = jsonObject.getJSONObject("geometry").getJSONObject("location");
                latLng = new LatLng(Double.parseDouble(laln.getString("lat")),
                        Double.parseDouble(laln.getString("lng")));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return latLng;
    } //end function getLocationByAddress



    //////////////////////////////////////////////////////////////
//         整合
//   GoogleMapURLModify    getJson   getLocationByAddress
///////////////////////////////////////////////////////////////
    //輸入中文地址 回傳經緯度
    public LatLng returnLatLng (String s){

        String s1 =s;
        LatLng latLng ;
        s1 = GoogleMapURLModify(s1) ;
        s1 = getJson(s1);
        latLng =getLocationByAddress (s1);

        return  latLng ;

    }//end function returnLatLng

/////////////////////////////////////////////////////////////

    //傳入經緯度  addmark
    public GoogleMap addMapMark (GoogleMap googleMap,LatLng latLng){


        GoogleMap map =googleMap;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);

        map.addMarker(options);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        return map;


    } //end function addMapMark



/*
    public static String toUtf8(String str) {
        String s="";


        try {
            s = new String(str.getBytes("UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return s;
    }//toUtf8

*/




    public void back (View view){
        Intent intent = new Intent();


        setResult(1234, intent);
        finish();





    }








} //eight Acitivity

