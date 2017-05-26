package com.yube.caly;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yusuf on 21.04.2017.
 */

public class singleton {
    private static singleton mInstance ;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private singleton(Context context){
        mCtx=context;
        requestQueue = getRequestQueue();

    }
    public static synchronized singleton getmInstance(Context context){
        if(mInstance==null){
            mInstance=new singleton(context);
        }
        return mInstance;
    }



    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return requestQueue;
    }
    public <T>void addTorequestque(Request<T> request)
    {
        requestQueue.add(request);
    }
}
