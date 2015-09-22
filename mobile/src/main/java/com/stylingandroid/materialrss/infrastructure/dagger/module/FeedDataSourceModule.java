package com.stylingandroid.materialrss.infrastructure.dagger.module;


import android.content.Context;

import com.stylingandroid.materialrss.R;
import com.stylingandroid.materialrss.infrastructure.datasource.network.NetworkFeedDataSource;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This class links the FeedApi to this implementation.
 */
@Module
public class FeedDataSourceModule {

  @Provides @Singleton
  public FeedDataSource getFeedDataSource(Context context) {
    return new NetworkFeedDataSource(context.getString(R.string.feed_url), context.getCacheDir().getAbsolutePath());
  }
}
