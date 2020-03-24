package com.motofit.app;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class MySingleton {

    @SuppressLint("StaticFieldLeak")
    private static MySingleton mInstance;
    private Context mCtx;
    private RequestQueue requestQueue;

    private MySingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestque(Request<T> request) {
        getRequestQueue().add(request);
    }
}
