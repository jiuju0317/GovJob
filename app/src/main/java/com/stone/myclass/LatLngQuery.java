package com.stone.myclass;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by meamea on 15/6/18.
 */
public class LatLngQuery {

    //String string=null;
    InputStream is = null;
    Reader rd = null;
    StringBuilder str = new StringBuilder();

    LatLng latLngReturn= null;

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
}
