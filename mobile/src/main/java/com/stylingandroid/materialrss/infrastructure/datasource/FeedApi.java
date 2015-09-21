package com.stylingandroid.materialrss.infrastructure.datasource;

import com.android.volley.Response;
import com.stylingandroid.materialrss.model.Feed;

/**
 * It describes the Feed api.
 */
public interface FeedApi {
  /**
   * It update the feeds from network or cache.
   * @param url the feed url
   * @param onError error callback
   * @param onSuccess success callback
   */
  void updateFeedFrom(String url, Response.ErrorListener onError, Response.Listener<Feed> onSuccess);

  /** Stop network calls. **/
  void stop();
}
