package com.stylingandroid.materialrss;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.stylingandroid.materialrss.infrastructure.dagger.component.ApplicationComponent;
import com.stylingandroid.materialrss.net.FeedApi;
import com.stylingandroid.materialrss.rss.model.Feed;

import javax.inject.Inject;

public class DataFragment extends Fragment implements Response.Listener<Feed>, Response.ErrorListener {
  private Feed feed;

  private ApplicationComponent appComponent;
  private FeedConsumer feedConsumer;
  private boolean isLoading = false;

  @Inject
  public FeedApi feedApi;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    appComponent = ((FeedListActivity) getActivity()).getApplicationComponent();
    appComponent.inject(this);

    update();
    return null;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    feedApi.stop();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof FeedConsumer) {
      feedConsumer = (FeedConsumer) activity;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    feedConsumer = null;
  }

  @Override
  public void onResponse(Feed newFeed) {
    this.feed = newFeed;
    if (feedConsumer != null) {
      feedConsumer.setFeed(feed);
    }
    isLoading = false;
  }

  @Override
  public void onErrorResponse(VolleyError error) {
    if (feedConsumer != null) {
      feedConsumer.handleError(error.getLocalizedMessage());
    }
    isLoading = false;
  }

  private void update() {
    if (feed == null && !isLoading()) {
      String url = getString(R.string.feed_url);
      feedApi.updateFeedFrom(url, this, this);
      isLoading = true;
    } else {
      if (feedConsumer != null) {
        feedConsumer.setFeed(feed);
      }
    }
  }

  public boolean isLoading() {
    return isLoading;
  }
}
