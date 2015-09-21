package com.stylingandroid.materialrss.presenter.activities;

import android.support.v7.app.AppCompatActivity;

import com.stylingandroid.materialrss.infrastructure.android.AndroidApplication;
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
  public ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).component();
  }
}
