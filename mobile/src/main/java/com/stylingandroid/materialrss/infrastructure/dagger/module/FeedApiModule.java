package com.stylingandroid.materialrss.infrastructure.dagger.module;

import android.content.Context;

import com.stylingandroid.materialrss.net.FeedApi;
import com.stylingandroid.materialrss.net.FeedApiImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This class links the FeedApi to this implementation.
 */
@Module
public class FeedApiModule {

  @Provides @Singleton
  public FeedApi getFeedApi(Context context) {
    return new FeedApiImpl(context);
  }
}
