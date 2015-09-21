package com.stylingandroid.materialrss.infrastructure.datasource.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.stylingandroid.materialrss.infrastructure.datasource.FeedApi;
import com.stylingandroid.materialrss.model.Feed;

/**
 * Feed API implementation.
 */
public class FeedApiImpl implements FeedApi {

  private VolleySingleton volley;

  public FeedApiImpl(Context context) {
    volley = VolleySingleton.getInstance(context);
  }

  @Override
  public void updateFeedFrom(String url, Response.ErrorListener onError, Response.Listener<Feed> onSuccess) {
    volley.addToRequestQueue(new RssRequest(Request.Method.GET, url, onSuccess, onError));
  }

  @Override
  public void stop() {
    volley.stop();
  }
}
