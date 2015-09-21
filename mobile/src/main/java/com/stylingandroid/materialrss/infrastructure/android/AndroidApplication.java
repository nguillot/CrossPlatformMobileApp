package com.stylingandroid.materialrss.infrastructure.android;

import com.stylingandroid.materialrss.infrastructure.dagger.component.ApplicationComponent;
import com.stylingandroid.materialrss.infrastructure.dagger.component.DaggerApplicationComponent;
import com.stylingandroid.materialrss.infrastructure.dagger.module.ApplicationModule;

/**
 * This class represent the application class.
 */
public class AndroidApplication extends android.app.Application {
  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .build();

  }

  public ApplicationComponent component() {
    return applicationComponent;
  }

  public void setApplicationComponent(ApplicationComponent applicationComponent) {
    this.applicationComponent = applicationComponent;
  }
}
