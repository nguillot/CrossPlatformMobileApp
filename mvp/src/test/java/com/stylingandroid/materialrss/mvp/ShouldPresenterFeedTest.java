package com.stylingandroid.materialrss.mvp;

import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.Feed;
import com.stylingandroid.materialrss.mvp.presentation.presenters.FeedPresenter;
import com.stylingandroid.materialrss.mvp.presentation.views.FeedView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * Feed presenter functional tests.
 */
public class ShouldPresenterFeedTest {

  public static final String ERROR_MSG = "an error occurred";
  @Mock
  private FeedDataSource mDataSource;

  @Mock
  private FeedView mView;

  private FeedPresenter mPresenter;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mPresenter = new FeedPresenter(mView);
  }

  @Test
  public void testThatLoadingFeedWhenStarting() {
    startPresenterWithOkAnswer();
  }

  @Test
  public void testThatLoadingFeedShowsErrorWhenFailed() {
    prepareDataSourceKOAnswer();

    mPresenter.start(mDataSource);

    //inOrder check the calls order
    InOrder inOrder = inOrder(mView);
    inOrder.verify(mView).showLoading();
    inOrder.verify(mView).showError(ERROR_MSG);
    inOrder.verify(mView).hideLoading();
  }

  @Test
  public void testThatPresenterIsCorrectlyDismissed() {
    startPresenterWithOkAnswer();

    mPresenter.stop();

    verify(mView).hideError();
    verify(mDataSource).destroy();
  }


  private void startPresenterWithOkAnswer() {
    prepareDataSourceOkAnswer();

    mPresenter.start(mDataSource);

    //inOrder check the calls order
    InOrder inOrder = inOrder(mView);
    inOrder.verify(mView).showLoading();
    inOrder.verify(mView).showFeed(any(Feed.class));
    inOrder.verify(mView).hideLoading();
  }

  private void prepareDataSourceOkAnswer() {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        FeedDataSource.FeedDataSourceListener listener = (FeedDataSource.FeedDataSourceListener) invocation.getArguments()[0];
        listener.onFeedReceived(new Feed(), null);
        return null;
      }
    }).when(mDataSource).requestFeed(any(FeedDataSource.FeedDataSourceListener.class));
  }

  private void prepareDataSourceKOAnswer() {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        FeedDataSource.FeedDataSourceListener listener = (FeedDataSource.FeedDataSourceListener) invocation.getArguments()[0];
        listener.onFeedReceived(null, ERROR_MSG);
        return null;
      }
    }).when(mDataSource).requestFeed(any(FeedDataSource.FeedDataSourceListener.class));
  }
}
