package com.stylingandroid.materialrss.mvp.domain.usecases;

import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.Feed;

/**
 * Representation of an use case to get the feed
 */
public interface GetFeedUsecase extends Usecase, FeedDataSource.FeedDataSourceListener {
  /**
   * Request datasource the feed
   */
  void requestFeed();

  /**
   * Sends the Feed thought the communication system
   * to be received by the presenter in another module
   *
   * @param response the response containing a list with movies, null if error
   * @param error    null if no error
   */
  void sendFeedToPresenter(Feed response, String error);

  interface GetFeedUsecaseListener {
    void onFeedReceived(Feed response, String error);
  }
}
