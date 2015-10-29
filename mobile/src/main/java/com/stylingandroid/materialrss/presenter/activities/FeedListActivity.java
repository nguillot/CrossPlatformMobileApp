package com.stylingandroid.materialrss.presenter.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.stylingandroid.materialrss.mvp.models.entities.Feed;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;
import com.stylingandroid.materialrss.mvp.presentation.presenters.FeedPresenter;
import com.stylingandroid.materialrss.mvp.presentation.views.FeedView;
import com.stylingandroid.materialrss.R;
import com.stylingandroid.materialrss.presenter.adapter.FeedAdapter;
import com.stylingandroid.materialrss.infrastructure.draganddrop.DragController;


public class FeedListActivity extends BaseActivity
        implements FeedView, FeedAdapter.ItemClickListener  {
  private RecyclerView mRecyclerView;
  private FeedPresenter mFeedPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.feed_list);


    mRecyclerView = (RecyclerView) findViewById(R.id.list);
    ImageView overlay = (ImageView) findViewById(R.id.overlay);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.addOnItemTouchListener(new DragController(mRecyclerView, overlay));
    mFeedPresenter = new FeedPresenter(this);
  }

  @Override
  protected void onStop() {

    super.onStop();
    mFeedPresenter.stop();
  }

  @Override
  protected void onStart() {

    super.onStart();
    mFeedPresenter.start(getDataSource());
  }

  @Override
  public void itemClicked(FeedItem item) {
    Intent detailIntent = new Intent(FeedListActivity.this, FeedDetailActivity.class);
    detailIntent.putExtra(FeedDetailActivity.ARG_ITEM, item.getPubDate());
    FeedAdapter.ViewHolder viewHolder = (FeedAdapter.ViewHolder) mRecyclerView.findViewHolderForItemId(item.getPubDate());
    String titleName = getString(R.string.transition_title);
    String dateName = getString(R.string.transition_date);
    String bodyName = getString(R.string.transition_body);
    Pair<View, String> titlePair = Pair.create(viewHolder.getTitleView(), titleName);
    Pair<View, String> datePair = Pair.create(viewHolder.getDateView(), dateName);
    Pair<View, String> bodyPair = Pair.create(viewHolder.getBodyView(), bodyName);
    @SuppressWarnings("unchecked")
    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, titlePair, datePair, bodyPair);
    ActivityCompat.startActivity(this, detailIntent, options.toBundle());
  }

  @Override
  public void showFeed(Feed feed) {
    FeedAdapter adapter = new FeedAdapter(feed.getItems(), this);
    mRecyclerView.setAdapter(adapter);
  }

  @Override
  public void showLoading() {
    //TODO
  }

  @Override
  public void hideLoading() {
    //TODO
  }

  @Override
  public void showError(String error) {
    Toast.makeText(this, error, Toast.LENGTH_LONG).show();
  }

  @Override
  public void hideError() {

  }
}
