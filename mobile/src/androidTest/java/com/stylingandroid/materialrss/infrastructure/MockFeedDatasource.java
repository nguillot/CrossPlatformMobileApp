package com.stylingandroid.materialrss.infrastructure;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.stylingandroid.materialrss.infrastructure.datasource.network.SaRssParser;
import com.stylingandroid.materialrss.mvp.models.entities.Feed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public final class MockFeedDataSource {

  public static Feed getFeed(Context instrumentationContext) {
    String feedStream = getStringFromAssetFile(instrumentationContext);
    SaRssParser parser = SaRssParser.newInstance(new ByteArrayInputStream(feedStream.getBytes(Charset.forName("UTF-8"))));
    Feed feed;
    try {
      feed = parser.parse();
    } catch (Exception e) {
      feed = null;
    }
    return feed;
  }

  public static String getStringFromAssetFile(Context instrumentationContext) {
    final AssetManager assetManager = instrumentationContext.getResources().getAssets();

    InputStream input;
    try {
      input = assetManager.open("feed.xml");

      int size = input.available();
      byte[] buffer = new byte[size];
      input.read(buffer);
      input.close();

      // byte buffer into a string
      return new String(buffer);
    } catch (IOException e) {
      Log.e("xxx", "Failed to read local file 'feed.xml'", e);
      return "";
    }
  }
}
