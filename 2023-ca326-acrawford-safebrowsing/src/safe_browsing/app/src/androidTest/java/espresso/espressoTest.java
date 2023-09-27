package espresso;



import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.safe_browsing.Activity2;
import com.safe_browsing.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class espressoTest {
    @Rule
    public ActivityScenarioRule<Activity2> activityRule = new ActivityScenarioRule<Activity2>(Activity2.class);

    @Test
    public void titleIsAboveButtonBlacklist(){
        onView(withText("Safe Browsing")).check(matches(isDisplayed()));
        onView(withText("Safe Browsing")).check(isCompletelyAbove(withId(R.id.blacklist_btn)));
    }

    @Test
    public void blacklistActivityTest(){
        onView(withId(R.id.blacklist_btn)).perform(click());
        onView(withText("Blacklist")).check(matches(isDisplayed()));

        //checking adding website error messages
        onView(withId(R.id.add_btn)).perform(click());
        onView(withId(R.id.typed_website)).check(matches(hasErrorText("You must enter a website")));

        //checking adding website functionality
        onView(withId(R.id.typed_website)).perform(typeText("twitch"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_btn)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.blacklisted)).atPosition(2).check(matches(withText("twitch")));

        //checking removing website functionality
        onData(anything()).inAdapterView(withId(R.id.blacklisted)).atPosition(2).perform(click());
        onView(withId(R.id.selected)).check(matches(withText("twitch")));
        onView(withId(R.id.remove_btn)).perform(click());
        onView(withId(R.id.selected)).check(matches(withHint("click a website to remove")));
    }
    @Test
    public void userSettingsActivityTest(){
        onView(withId(R.id.devices_btn)).perform(click());
        onView(withText("Select a user to change settings")).check(matches(isDisplayed()));

        //checking selecting a user to view
        onData(anything()).inAdapterView(withId(R.id.connected)).atPosition(0).perform(click());
        onView(withId(R.id.page_title)).check(matches(withText("testing123")));
        onView(withId(R.id.page_title)).check(isCompletelyAbove(withText("Individual Time Limit Options")));

        //checking the times are put into the text field
        onView(withId(R.id.time_1)).check(matches(withHint("Start Time")));
        onView(withId(R.id.time_1)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.time_1)).check(matches(withText("00:00")));
        onView(withId(R.id.time_2)).check(matches(withHint("End Time")));
        onView(withId(R.id.time_2)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.time_2)).check(matches(withText("00:00")));

        //checking adding website error messages
        onView(withId(R.id.add_btn)).perform(click());
        onView(withId(R.id.typed_website)).check(matches(hasErrorText("You must enter a website")));

        //checking adding website functionality
        onView(withId(R.id.typed_website)).perform(typeText("twitter"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_btn)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.whitelist_sites)).atPosition(2).check(matches(withText("twitter")));

        //checking removing website functionality
        onData(anything()).inAdapterView(withId(R.id.whitelist_sites)).atPosition(2).perform(click());
        onView(withId(R.id.selected)).check(matches(withText("twitter")));
        onView(withId(R.id.remove_btn)).perform(click());
        onView(withId(R.id.selected)).check(matches(withHint("click a website to remove")));
    }
    @Test
    public void userLogsActivityTesting() {
        onView(withId(R.id.user_logs_btn)).perform(click());
        onView(withText("Users")).check(matches(isDisplayed()));
        onView(withText("Users")).check(isCompletelyAbove(withText("Select a User to view their logs")));

        //checking selecting a user to view
        onData(anything()).inAdapterView(withId(R.id.connected)).atPosition(0).perform(click());
        onView(withId(R.id.page_title)).check(matches(isDisplayed()));
        onView(withId(R.id.page_title)).check(isCompletelyAbove(withId(R.id.weekly_check)));

        //check the checkboxes
        onView(withId(R.id.weekly_check)).check(matches((isChecked())));
        onView(withId(R.id.daily_check)).check(matches((isNotChecked())));
        onView(withId(R.id.daily_check)).perform(click());
        onView(withId(R.id.daily_check)).check(matches((isChecked())));
        onView(withId(R.id.weekly_check)).check(matches((isNotChecked())));
        onView(withId(R.id.daily_check)).perform(click());
        onView(withId(R.id.daily_check)).check(matches((isChecked())));
        onView(withId(R.id.weekly_check)).check(matches((isNotChecked())));
    }
    @Test
    public void CreateUserTesting(){
        onView(withId(R.id.devices_btn)).perform(click());
        onView(withText("Select a user to change settings")).check(matches(isDisplayed()));

        //enter information
        onView(withId(R.id.username)).perform(typeText("Bob"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.create_user_btn)).perform(click());

        //check if user is created
        onData(anything()).inAdapterView(withId(R.id.connected)).atPosition(1).check(matches(withText("Bob")));
    }
}
