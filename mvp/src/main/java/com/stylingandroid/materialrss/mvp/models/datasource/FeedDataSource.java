package com.stylingandroid.materialrss.mvp.models.datasource;

import com.stylingandroid.materialrss.mvp.models.entities.Feed;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;


public interface FeedDataSource {
  void destroy();

  void requestFeed(FeedDataSourceListener onComplete);

  void requestFeedItem(long timeStamp, FeedItemDataSourceListener onComplete);

  interface FeedDataSourceListener {
    void onFeedReceived(Feed feed, String error);
  }

  interface FeedItemDataSourceListener {
    void onFeedItemReceived(FeedItem feedItem);
  }

}
