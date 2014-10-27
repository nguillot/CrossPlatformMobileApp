package com.stylingandroid.materialrss;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.stylingandroid.materialrss.adapter.FeedAdapter;
import com.stylingandroid.materialrss.rss.model.Feed;
import com.stylingandroid.materialrss.rss.model.Item;

public class FeedListActivity extends ListActivity
        implements FeedConsumer {
    private static final String DATA_FRAGMENT_TAG = DataFragment.class.getCanonicalName();

    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);

        DataFragment dataFragment = (DataFragment) getFragmentManager().findFragmentByTag(DATA_FRAGMENT_TAG);
        if (dataFragment == null) {
            dataFragment = (DataFragment) Fragment.instantiate(this, DataFragment.class.getName());
            dataFragment.setRetainInstance(true);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(dataFragment, DATA_FRAGMENT_TAG);
            transaction.commit();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent detailIntent = new Intent(this, FeedDetailActivity.class);
        Item item = adapter.getItem(position);
        detailIntent.putExtra(FeedDetailActivity.ARG_ITEM, item);
        startActivity(detailIntent);
    }

    public void setFeed(Feed feed) {
        adapter = new FeedAdapter(this, feed.getItems());
        setListAdapter(adapter);
    }

    @Override
    public void handleError(String message) {
       Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
