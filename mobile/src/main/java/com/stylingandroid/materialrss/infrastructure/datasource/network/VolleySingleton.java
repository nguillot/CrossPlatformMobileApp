package com.stylingandroid.materialrss.infrastructure.datasource.network;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

public final class VolleySingleton {
    private static final int CACHE_SIZE = 1024 * 1024 * 10;
    private static VolleySingleton instance;

    private RequestQueue requestQueue;
    private String cacheDirPath;

    private VolleySingleton(String cacheDirPath) {
        this.cacheDirPath = cacheDirPath;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(String cacheDirPath) {
        if (instance == null) {
            instance = new VolleySingleton(cacheDirPath);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(new File(cacheDirPath), CACHE_SIZE);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void stop() {
        getRequestQueue().stop();
    }
}
