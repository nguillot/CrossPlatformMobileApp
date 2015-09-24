package com.stylingandroid.materialrss.mvp;

import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;
import com.stylingandroid.materialrss.mvp.presentation.presenters.FeedItemPresenter;
import com.stylingandroid.materialrss.mvp.presentation.views.FeedItemView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * Feed item presenter functional tests.
 */
public class ShouldPresentFeedItemTest {
  public static final long FEED_ITEM_TIMESTAMP = new Date().getTime();
  @Mock
  private FeedDataSource mDataSource;

  @Mock
  private FeedItemView mView;

  private FeedItemPresenter mPresenter;
  private FeedItem mResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mPresenter = new FeedItemPresenter(mView, FEED_ITEM_TIMESTAMP);
    mResponse = new FeedItem();
    mResponse.setTitle("title");
    mResponse.setContent("content");
    mResponse.setPubDate(FEED_ITEM_TIMESTAMP);
  }

  @Test
  public void testThatFeedItemIsCorrectlySetWhenTheViewStarted() {
    prepareDataSourceAnswer();

    mPresenter.start(mDataSource);

    InOrder inOrder = inOrder(mView);
    inOrder.verify(mView).setTitle(mResponse.getTitle());
    inOrder.verify(mView).setBody(mPresenter.feedItemFormattedContentFrom(mResponse));
    inOrder.verify(mView).setDate(mPresenter.feedItemFormattedPubDateFrom(mResponse));
  }

  @Test
  public void testThatPresenterIsCorrectlyDismiss() {
    prepareDataSourceAnswer();

    mPresenter.start(mDataSource);
    mPresenter.stop();

    verify(mDataSource).destroy();
  }

  private void prepareDataSourceAnswer() {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        FeedDataSource.FeedItemDataSourceListener listener = (FeedDataSource.FeedItemDataSourceListener) invocation.getArguments()[1];
        listener.onFeedItemReceived(mResponse);
        return null;
      }
    }).when(mDataSource).requestFeedItem(eq(FEED_ITEM_TIMESTAMP), any(FeedDataSource.FeedItemDataSourceListener.class));
  }
}
