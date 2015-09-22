package com.stylingandroid.materialrss.mvp.domain.usecases;

import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;

/**
 * Representation of an use case to get the details of a specific feed item.
 */
public interface GetFeedItemUsecase extends Usecase, FeedDataSource.FeedItemDataSourceListener {
  /**
   * Request datasource the item of a
   * feed.
   *
   * @param timestamp of feed item
   */
  void requestFeedItem(long timestamp);

  /**
   * Sends the Feed item thought the communication system
   * to be received by the presenter
   *
   * @param response the response containing the details of the film
   */
  void sendFeedItemToPresenter(FeedItem response);

  interface GetFeedItemUsecaseListener {
    void onFeedItemReceived(FeedItem response);
  }
}
