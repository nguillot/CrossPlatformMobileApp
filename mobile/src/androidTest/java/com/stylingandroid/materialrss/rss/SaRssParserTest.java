package com.stylingandroid.materialrss.rss;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.stylingandroid.materialrss.infrastructure.MockFeedDatasource;
import com.stylingandroid.materialrss.rss.model.Feed;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SaRssParserTest {

  @Test
  public void testRssParser() throws Exception {
    //we'd like to make a real unit test here but the parser class depend of Android
    String feedStream = MockFeedDatasource.getStringFromAssetFile(InstrumentationRegistry.getInstrumentation().getContext());
    SaRssParser parser = SaRssParser.newInstance(new ByteArrayInputStream(feedStream.getBytes(Charset.forName("UTF-8"))));
    Feed feed = parser.parse();

    assertNotNull(feed);
    assertTrue(feed.getItems().size() > 0);
  }
}
