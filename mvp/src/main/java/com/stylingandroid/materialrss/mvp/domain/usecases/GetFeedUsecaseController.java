package com.stylingandroid.materialrss.mvp.domain.usecases;

import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.Feed;

public class GetFeedUsecaseController implements GetFeedUsecase {
  private final FeedDataSource mDataSource;
  private final GetFeedUsecaseListener mListener;

  public GetFeedUsecaseController(final FeedDataSource dataSource, final GetFeedUsecaseListener listener) {
    if (dataSource == null)
      throw new IllegalArgumentException("DataSource cannot be null");

    if (listener == null)
      throw new IllegalArgumentException("listener cannot be null");

    mDataSource = dataSource;
    mListener = listener;
  }

  @Override
  public void onFeedReceived(Feed response, String error) {
    sendFeedToPresenter(response, error);
  }

  @Override
  public void requestFeed() {
    mDataSource.requestFeed(this);
  }

  @Override
  public void sendFeedToPresenter(Feed response, String error) {
    mListener.onFeedReceived(response, error);
  }

  @Override
  public void execute() {
    requestFeed();
  }
}
