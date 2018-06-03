package com.pluralsight.dagger2example;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pluralsight.dagger2example.mvp.repository.RepositoryContract;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;

@RunWith(AndroidJUnit4.class)
public class RepositoryActivityTest extends BaseTest {

    @Rule
    public IntentsTestRule<RepositoryActivity> repositoryActivityIntentsTestRule =
            new IntentsTestRule<>(RepositoryActivity.class, true, false);

    RepositoryContract.RepositoryPresenter presenter;

    @Before
    public void beforeTest(){
        presenter = BaseTest.application.getApplicationComponent().repositoryPresenter();

        setupResponses();
    }


    private RepositoryActivity startActivity() {
        return repositoryActivityIntentsTestRule.launchActivity(null);
    }

    @Test
    public void testActivityLaunch(){
        // act
        startActivity();

        // assert
        onView(withId(R.id.rv_repositories));
    }

    @Test
    public void testRepositoryLoaded(){
        // arrange
        final Integer expectedCount = 12;

        // act
        startActivity();

        // assert
        onView(withId(R.id.rv_repositories)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }

                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                assertThat(adapter.getItemCount(), Matchers.not(0));
                assertThat(adapter.getItemCount(), Matchers.is(expectedCount));
            }
        });

        // Because we have a scoped presenter, we can verify particular methods we expect to be called
        // If this presenter wasn't scoped, we would need to some how make it a singleton so we can
        // mock/spy the same object that is injected and the object we obtained from the
        // component.
        Mockito.verify(presenter, atLeast(1)).start();
        Mockito.verify(presenter, atLeast(1)).takeView(Mockito.any(RepositoryContract.RepositoryView.class));
        Mockito.verify(presenter, atLeast(1)).getRepositories(Mockito.anyString());
    }

    @Test
    public void testRepositoryItemClick(){
        // arrange test

        // act
        startActivity();

        onView(withId(R.id.rv_repositories))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // assert
        intended(hasComponent(RepositoryDetailActivity.class.getName()));
    }
}
