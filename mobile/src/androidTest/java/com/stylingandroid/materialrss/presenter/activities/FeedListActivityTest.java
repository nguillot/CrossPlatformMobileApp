package com.stylingandroid.materialrss.presenter.activities;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.stylingandroid.materialrss.infrastructure.android.AndroidApplication;
import com.stylingandroid.materialrss.R;
import com.stylingandroid.materialrss.infrastructure.MockFeedDataSource;
import com.stylingandroid.materialrss.infrastructure.dagger.component.ApplicationComponent;
import com.stylingandroid.materialrss.infrastructure.dagger.component.DaggerApplicationComponent;
import com.stylingandroid.materialrss.infrastructure.dagger.module.ApplicationModule;
import com.stylingandroid.materialrss.infrastructure.dagger.module.FeedDataSourceModule;
import com.stylingandroid.materialrss.mvp.models.datasource.FeedDataSource;
import com.stylingandroid.materialrss.mvp.models.entities.Feed;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class FeedListActivityTest {

  public static final int DETAIL_ITEM_INDEX = 0;
  @Rule
  public ActivityTestRule<FeedListActivity> mActivityRule =
          new ActivityTestRule<>(FeedListActivity.class, true, false);

  /**
   * ihm date format.
   **/
  private DateFormat mDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
  /** **/
  private DateFormat mTestDf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.US);

  private Context mContext;

  @Mock
  private FeedDataSourceModule mFeedDataSourceModule;
  @Mock
  private FeedDataSource mFeedDataSource;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    initMockDataSource();
    when(mFeedDataSourceModule.getFeedDataSource(mContext)).thenReturn(mFeedDataSource);

    ApplicationComponent component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(mContext))
            .feedDataSourceModule(mFeedDataSourceModule)
            .build();

    AndroidApplication app = (AndroidApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    app.setApplicationComponent(component);

    Intent intent = new Intent();
    mActivityRule.launchActivity(intent);
  }

  private void initMockDataSource() {
    final Feed feed = MockFeedDataSource.getFeed(InstrumentationRegistry.getInstrumentation().getContext());
    //
    mockRequestFeed(feed);
    //
    mockRequestFeedItem(feed);
  }

  private void mockRequestFeedItem(final Feed feed) {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        FeedDataSource.FeedItemDataSourceListener listener = (FeedDataSource.FeedItemDataSourceListener) invocation.getArguments()[1];
        listener.onFeedItemReceived(feed.getItems().get(DETAIL_ITEM_INDEX));
        return null;
      }
    }).when(mFeedDataSource).requestFeedItem(anyLong(), any(FeedDataSource.FeedItemDataSourceListener.class));
  }

  private void mockRequestFeed(final Feed feed) {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        FeedDataSource.FeedDataSourceListener listener = (FeedDataSource.FeedDataSourceListener) invocation.getArguments()[0];
        listener.onFeedReceived(feed, null);
        return null;
      }
    }).when(mFeedDataSource).requestFeed(any(FeedDataSource.FeedDataSourceListener.class));
  }

  @After
  public void tearDown() throws Exception {

  }

  private void checkItemTitleAt(String title, int index) {
    //scroll to the item
    onView(withId(R.id.list)).perform(RecyclerViewActions.scrollToPosition(index));
    //check if displayed
    onView(allOf(withId(R.id.feed_item_title), withText(title))).check(matches(isDisplayed()));
  }

  private void checkItemDescriptionAt(String text, int index) {
    //scroll to the item
    onView(withId(R.id.list)).perform(RecyclerViewActions.scrollToPosition(index));
    //check if displayed
    onView(allOf(withId(R.id.feed_item_description), withText(containsString(text)))).check(matches(isDisplayed()));
  }

  private void checkItemDateAt(String enUSDate, int index) throws ParseException {
    String localizedDate = localizedDateFromEnUSDate(enUSDate);
    //scroll to the item
    onView(withId(R.id.list)).perform(RecyclerViewActions.scrollToPosition(index));
    //check if displayed
    onView(allOf(withId(R.id.feed_item_date), withText(localizedDate))).check(matches(isDisplayed()));
  }

  private String localizedDateFromEnUSDate(String enUSDate) throws ParseException {
    Date date = mTestDf.parse(enUSDate);
    return mDateFormat.format(date);
  }

  private void checkToolbarTitle(String title) {
    onView(withText(title)).check(matches(isDisplayed()));
  }

  @Test
  public void testItems() throws ParseException {
    checkItemTitleAt("Custom Colour Spans", 0);
    checkItemTitleAt("RecyclerView FastScroll – Part 2", 1);
    checkItemTitleAt("RecyclerView FastScroll – Part 1", 2);
    checkItemTitleAt("Scrolling RecyclerView – Part 3", 3);

    checkItemDescriptionAt("Regular readers of Styling Android", 0);
    checkItemDescriptionAt("In the previous article we got our FastScroller control", 1);
    checkItemDescriptionAt("In the previous series we looked at", 2);
    checkItemDescriptionAt("In the previous article we did an exploration", 3);

    checkItemDateAt("Mar 27, 2015", 0);
    checkItemDateAt("Mar 20, 2015", 1);
    checkItemDateAt("Mar 13, 2015", 2);
    checkItemDateAt("Mar 6, 2015", 3);
  }

  @Test
  public void testDetailActivityOpen() throws ParseException {

    onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(DETAIL_ITEM_INDEX, ViewActions.click()));

    checkToolbarTitle(mContext.getString(R.string.title_feed_detail));

    onView(withText("Custom Colour Spans"))
            .check(matches(isDisplayed()));
    onView(withText(localizedDateFromEnUSDate("Mar 27, 2015")))
            .check(matches(isDisplayed()));
    onView(withId(R.id.feed_detail_body))
            .check(matches(isDisplayed()));
  }

  @Test
  public void testGoOnDetailActivityAndBack() throws InterruptedException {
    onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(DETAIL_ITEM_INDEX, ViewActions.click()));

    pressBack();

    //TODO due to the bug https://code.google.com/p/android-test-kit/issues/detail?id=55 we need to wait, an idling resource will be more elegant but takes time to implement but it is only an hack waiting for a fix
    Thread.sleep(500);

    checkToolbarTitle(mContext.getString(R.string.app_name));
  }

  @Test
  public void testMainPageTitle() {
    checkToolbarTitle(mContext.getString(R.string.app_name));
  }
}
