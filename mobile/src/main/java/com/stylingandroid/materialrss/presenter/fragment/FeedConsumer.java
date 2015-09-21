package com.stylingandroid.materialrss.presenter.fragment;

import com.stylingandroid.materialrss.model.Feed;

public interface FeedConsumer {
    void setFeed(Feed feed);

    void handleError(String message);
}
