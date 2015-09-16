package com.stylingandroid.materialrss;

import android.support.v7.app.AppCompatActivity;

import com.stylingandroid.materialrss.infrastructure.dagger.component.ApplicationComponent;

/**
 * Base activity to give access to the application component.
 */
public abstract class BaseActivity extends AppCompatActivity {

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).component();
  }
}
