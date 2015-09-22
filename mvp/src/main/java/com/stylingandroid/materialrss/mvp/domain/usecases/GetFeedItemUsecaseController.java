package com.stylingandroid.materialrss.mvp.domain.usecases;

import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;

public class GetFeedItemUsecaseController implements GetFeedItemUsecase {
  private final FeedDataSource mDataSource;
  private final GetFeedItemUsecaseListener mListener;
  private final long mTimestamp;

  public GetFeedItemUsecaseController(final FeedDataSource dataSource, final GetFeedItemUsecaseListener listener, final long timestamp) {
    if (dataSource == null)
      throw new IllegalArgumentException("DataSource cannot be null");

    if (listener == null)
      throw new IllegalArgumentException("Listener cannot be null");

    if (timestamp <= 0)
      throw new IllegalArgumentException("Timestamp cannot be <= 0");

    mDataSource = dataSource;
    mListener = listener;
    mTimestamp = timestamp;
  }

  @Override
  public void requestFeedItem(long timestamp) {
    mDataSource.requestFeedItem(timestamp, this);
  }

  @Override
  public void sendFeedItemToPresenter(FeedItem response) {
    mListener.onFeedItemReceived(response);
  }

  @Override
  public void onFeedItemReceived(FeedItem feedItem) {
    sendFeedItemToPresenter(feedItem);
  }

  @Override
  public void execute() {
    requestFeedItem(mTimestamp);
  }
}
