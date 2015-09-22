package com.stylingandroid.materialrss.mvp.presentation.presenters;

import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;

/**
 * Interface that represents a Presenter in the model view presenter Pattern
 * defines methods to manage the Activity / Fragment lifecycle
 */
public interface Presenter {

  /**
   * Called when the presenter is initialized
   */
  void start(FeedDataSource ds);

  /**
   * Called when the presenter is stop, i.e when an activity
   * or a fragment finishes
   */
  void stop();
}
