package com.stylingandroid.materialrss.mvp.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Feed implements Serializable {
  public static final Feed NONE = new Feed();

  private final List<FeedItem> items = new ArrayList<FeedItem>();

  public void addItem(FeedItem item) {
    items.add(item);
  }

  public List<FeedItem> getItems() {
    return items;
  }
}
