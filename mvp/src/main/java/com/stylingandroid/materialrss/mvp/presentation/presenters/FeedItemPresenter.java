package com.stylingandroid.materialrss.mvp.presentation.presenters;

import com.stylingandroid.materialrss.mvp.domain.usecases.GetFeedItemUsecase;
import com.stylingandroid.materialrss.mvp.domain.usecases.GetFeedItemUsecaseController;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;
import com.stylingandroid.materialrss.mvp.presentation.views.FeedItemView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FeedItemPresenter implements Presenter, GetFeedItemUsecase.GetFeedItemUsecaseListener {
  private static final String NEWLINE = "\\n";
  private static final String BR = "<br />";

  private final DateFormat mDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
  private FeedItemView mView;
  private GetFeedItemUsecase mUsecase;
  private FeedDataSource mFeedDataSource;
  private long mFeedItemTimestamp;

  public FeedItemPresenter(FeedItemView view, long timestamp) {
    mView = view;
    mFeedItemTimestamp = timestamp;
  }

  @Override
  public void onFeedItemReceived(FeedItem response) {
    mView.setTitle(response.getTitle());
    mView.setBody(response.getContent().replaceAll(NEWLINE, BR));
    mView.setDate(mDateFormat.format(new Date(response.getPubDate())));
  }

  @Override
  public void start(FeedDataSource ds) {
    mFeedDataSource = ds;
    mUsecase = new GetFeedItemUsecaseController(mFeedDataSource, this, mFeedItemTimestamp);
    mUsecase.execute();
  }

  @Override
  public void stop() {
    mFeedDataSource.destroy();
    mUsecase = null;
    mFeedDataSource = null;
  }
}
