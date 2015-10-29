package com.stylingandroid.materialrss.presenter.activities;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.stylingandroid.materialrss.R;
import com.stylingandroid.materialrss.mvp.presentation.presenters.FeedItemPresenter;
import com.stylingandroid.materialrss.mvp.presentation.views.FeedItemView;

public class FeedDetailActivity extends BaseActivity implements FeedItemView {
  public static final String ARG_ITEM = "ARG_ITEM";
  private static final String HTML_MIME_TYPE = "text/html";

  private FeedItemPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.feed_detail);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    TextView title = (TextView) findViewById(R.id.feed_detail_title);
    TextView date = (TextView) findViewById(R.id.feed_detail_date);
    WebView webView = (WebView) findViewById(R.id.feed_detail_body);
    ViewCompat.setTransitionName(title, getString(R.string.transition_title));
    ViewCompat.setTransitionName(date, getString(R.string.transition_date));
    ViewCompat.setTransitionName(webView, getString(R.string.transition_date));

    long itemTimestamp = getIntent().getLongExtra(ARG_ITEM, 0);
    mPresenter = new FeedItemPresenter(this, itemTimestamp);
  }

  @Override
  protected void onStop() {

    super.onStop();
    mPresenter.stop();
  }

  @Override
  protected void onStart() {

    super.onStart();
    mPresenter.start(getDataSource());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem) {
    int id = menuItem.getItemId();
    if (id == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(menuItem);
  }

  @Override
  public void setTitle(String title) {
    TextView titleText = (TextView) findViewById(R.id.feed_detail_title);
    titleText.setText(title);
  }

  @Override
  public void setDate(String date) {
    TextView dateText = (TextView) findViewById(R.id.feed_detail_date);
    dateText.setText(date);
  }

  @Override
  public void setBody(String htmlContent) {
    WebView webView = (WebView) findViewById(R.id.feed_detail_body);
    webView.loadData(htmlContent, HTML_MIME_TYPE, null);
  }
}
