package com.stylingandroid.materialrss.mvp.presentation.views;

/**
 * This class represent the FeedItemView.
 */
public interface FeedItemView {
  void setTitle(String title);

  void setDate(String date);

  void setBody(String htmlContent);
}
