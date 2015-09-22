package com.stylingandroid.materialrss.infrastructure.datasource.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.Feed;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;

/**
 * Feed API implementation.
 */
public class NetworkFeedDataSource implements FeedDataSource, Response.Listener<Feed>, Response.ErrorListener {

  private Feed mFeed;

  private VolleySingleton volley;
  private FeedDataSourceListener mOnCompleteFeedRequest;
  private boolean mIsLoading = false;
  private String mFeedUrl;
  private FeedItemDataSourceListener mOnCompleteFeedItemRequest;
  private long mTimeStamp;

  public NetworkFeedDataSource(String feedUrl, String cacheDirPath) {
    initVolley(cacheDirPath);
    mFeedUrl = feedUrl;
  }

  @Override
  public void destroy() {
    mOnCompleteFeedRequest = null;
    volley.stop();
  }

  @Override
  public void requestFeed(FeedDataSourceListener onComplete) {
    mOnCompleteFeedRequest = onComplete;
    update(mFeedUrl);
  }

  @Override
  public void requestFeedItem(long timeStamp, FeedItemDataSourceListener onComplete) {
    if (onComplete == null)
      return;

    mOnCompleteFeedItemRequest = onComplete;
    mTimeStamp = timeStamp;
    update(mFeedUrl);
  }

  private void onFeedItemReceived(long timeStamp) {
    for (FeedItem item : mFeed.getItems()) {
      if (item.getPubDate() == timeStamp) {
        mOnCompleteFeedItemRequest.onFeedItemReceived(item);
        break;
      }
    }
    mIsLoading = false;
  }

  @Override
  public void onResponse(Feed newFeed) {
    this.mFeed = newFeed;
    if (mOnCompleteFeedRequest != null) {
      mOnCompleteFeedRequest.onFeedReceived(mFeed, null);
    }
    if (mOnCompleteFeedItemRequest != null) {
      onFeedItemReceived(mTimeStamp);
    }
    mIsLoading = false;
  }

  @Override
  public void onErrorResponse(VolleyError error) {
    if (mOnCompleteFeedRequest != null) {
      mOnCompleteFeedRequest.onFeedReceived(null, error.getLocalizedMessage());
    }
  }

  private void initVolley(String cacheDirPath) {
    if (volley == null) {
      volley = VolleySingleton.getInstance(cacheDirPath);
    } else {
      volley.stop();
    }
  }

  private void update(String url) {
    if(isLoading()) {
      return;
    }

    if (mFeed == null) {
      volley.addToRequestQueue(new RssRequest(Request.Method.GET, url, this, this));
      mIsLoading = true;
    } else {
      if (mOnCompleteFeedRequest != null) {
        mOnCompleteFeedRequest.onFeedReceived(mFeed, null);
      }
      if (mOnCompleteFeedItemRequest != null) {
        onFeedItemReceived(mTimeStamp);
      }
    }
  }

  private boolean isLoading() {
    return mIsLoading;
  }
}
