package com.stylingandroid.materialrss.infrastructure.android;

import com.stylingandroid.materialrss.R;
import com.stylingandroid.materialrss.infrastructure.datasource.network.NetworkFeedDataSource;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;

/**
 * This class represent the application class.
 */
public class AndroidApplication extends android.app.Application {
  private FeedDataSource dataSource;

  @Override
  public void onCreate() {
    super.onCreate();

    dataSource = new NetworkFeedDataSource(getString(R.string.feed_url), getCacheDir().getAbsolutePath());
  }

  public FeedDataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(FeedDataSource ds) {
    dataSource = ds;
  }
}
