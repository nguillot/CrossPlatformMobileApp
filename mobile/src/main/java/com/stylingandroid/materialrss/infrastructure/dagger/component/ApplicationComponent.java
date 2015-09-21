package com.stylingandroid.materialrss.infrastructure.dagger.component;

import android.content.Context;

import com.stylingandroid.materialrss.presenter.activities.BaseActivity;
import com.stylingandroid.materialrss.presenter.fragment.DataFragment;
import com.stylingandroid.materialrss.infrastructure.dagger.module.ApplicationModule;
import com.stylingandroid.materialrss.infrastructure.dagger.module.FeedApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 * Inspired from @{link https://github.com/android10/Android-CleanArchitecture}
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, FeedApiModule.class})
public interface ApplicationComponent {
  void inject(BaseActivity activity);
  void inject(DataFragment fragment);

  //Exposed to sub-graphs.
  Context context();
}
