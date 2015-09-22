package com.stylingandroid.materialrss.infrastructure.dagger.component;

import android.content.Context;

import com.stylingandroid.materialrss.presenter.activities.BaseActivity;
import com.stylingandroid.materialrss.infrastructure.dagger.module.ApplicationModule;
import com.stylingandroid.materialrss.infrastructure.dagger.module.FeedDataSourceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 * Inspired from @{link https://github.com/android10/Android-CleanArchitecture}
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, FeedDataSourceModule.class})
public interface ApplicationComponent {
  void inject(BaseActivity activity);

  //Exposed to sub-graphs.
  Context context();
}
