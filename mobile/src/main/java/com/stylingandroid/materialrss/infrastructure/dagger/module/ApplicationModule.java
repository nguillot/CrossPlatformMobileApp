package com.stylingandroid.materialrss.infrastructure.dagger.module;

import android.content.Context;

import com.stylingandroid.materialrss.AndroidApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the context lifecycle.
 */
@Module
public class ApplicationModule {
  private final Context context;

  public ApplicationModule(Context aContext) {
    this.context = aContext;
  }

  @Provides @Singleton Context context() {
    return this.context;
  }
}
