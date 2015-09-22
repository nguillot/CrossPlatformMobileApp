package com.stylingandroid.materialrss.presenter.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.stylingandroid.materialrss.infrastructure.android.AndroidApplication;
import com.stylingandroid.materialrss.infrastructure.dagger.component.ApplicationComponent;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;

import javax.inject.Inject;

/**
 * Base activity to give access to the application component.
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Inject
  public FeedDataSource dataSource;

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  public ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).component();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getApplicationComponent().inject(this);
  }
}
