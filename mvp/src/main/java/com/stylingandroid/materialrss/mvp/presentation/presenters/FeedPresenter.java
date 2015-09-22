package com.stylingandroid.materialrss.mvp.presentation.presenters;


import com.stylingandroid.materialrss.mvp.domain.usecases.GetFeedUsecase;
import com.stylingandroid.materialrss.mvp.domain.usecases.GetFeedUsecaseController;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.Feed;
import com.stylingandroid.materialrss.mvp.presentation.views.FeedView;

public class FeedPresenter implements Presenter, GetFeedUsecase.GetFeedUsecaseListener {
  private FeedView mView;
  private GetFeedUsecase mUsecase;
  private FeedDataSource mFeedDataSource;

  public FeedPresenter(FeedView view) {
    mView = view;
  }

  @Override
  public void start(FeedDataSource ds) {
    mView.showLoading();
    mFeedDataSource = ds;
    mUsecase = new GetFeedUsecaseController(mFeedDataSource, this);
    mUsecase.execute();
  }

  @Override
  public void stop() {
    mView.hideError();
    mUsecase = null;
    mFeedDataSource.destroy();
    mFeedDataSource = null;
  }

  @Override
  public void onFeedReceived(Feed response, String error) {
    if (error == null) {
      mView.showFeed(response);
    } else {
      mView.showError(error);
    }
    mView.hideLoading();
  }
}
