package com.stylingandroid.materialrss.presenter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stylingandroid.materialrss.infrastructure.android.AndroidApplication;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;

/**
 * Base activity to give access to the application component.
 */
public abstract class BaseActivity extends AppCompatActivity {

  /**
   * Get the application data source.
   *
   * @return {@link FeedDataSource}
   */
  public FeedDataSource getDataSource() {
    return ((AndroidApplication) getApplication()).getDataSource();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
}
