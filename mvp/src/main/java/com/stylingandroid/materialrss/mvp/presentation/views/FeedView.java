package com.stylingandroid.materialrss.mvp.presentation.views;

import com.stylingandroid.materialrss.mvp.models.entities.Feed;

/**
 * This class represent the FeedView.
 */
public interface FeedView {
  void showFeed(Feed feed);

  void showLoading();

  void hideLoading();

  void showError(String error);

  void hideError();
}
