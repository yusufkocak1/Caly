package com.yube.caly.mongoAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.yube.caly.contact.dailyContact;
import com.yube.caly.singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yusuf on 23.05.2017.
 */

public class getDataAdapter  {

    public ArrayList<dailyContact> arrayList=new ArrayList<>();
    String selectUrl="http://192.168.0.150:1000/api/status";

    public getDataAdapter(Activity activity,String url) {
        this.activity = activity;
        this.selectUrl=url;

    }

    Activity activity;
    ProgressDialog progressDialog;
    String mResult="unsuccess";



    public ArrayList<dailyContact> getArrayList() {


        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, selectUrl, (String)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count=0;
                        while (count<response.length()){
                            try {
                                JSONObject  jsonObject = response.getJSONObject(count);

                                dailyContact dailyContact =new dailyContact(
                                        jsonObject.getString("daily"),
                                        jsonObject.getString("date"),
                                        jsonObject.getString("_id")

                                );


                                arrayList.add(dailyContact);
                                count++;


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity,"Error...",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }

        );
        singleton.getmInstance(activity).addTorequestque(jsonArrayRequest);
        return arrayList;
    }
}
