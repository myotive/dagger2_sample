package com.pluralsight.dagger2example;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pluralsight.dagger2example.di.RepositoryDetailComponent;
import com.pluralsight.dagger2example.mvp.repositoryContent.RepositoryContentContract;
import com.pluralsight.dagger2example.network.models.Owner;
import com.pluralsight.dagger2example.network.models.Repository;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RepositoryDetailActivityTest extends BaseTest {
    @Rule
    public IntentsTestRule<RepositoryDetailActivity> repositoryDetailActivityIntentsTestRule =
            new IntentsTestRule<>(RepositoryDetailActivity.class, true, false);

    RepositoryContentContract.RepositoryContentPresenter presenter;

    private String owner = "myotive";
    private String repository =  "blog-fragments-2017";

    @Before
    public void beforeTest(){
        RepositoryDetailComponent component = application.generateRepositoryDetailComponent(owner, repository);
        presenter = component.repositoryContentPresenter();

        setupResponses();
    }

    private RepositoryDetailActivity startActivity() {

        Context context = getInstrumentation()
                .getTargetContext()
                .getApplicationContext();

        Repository repository = new Repository();
        repository.setName(this.repository);
        Owner owner = new Owner();
        owner.setLogin(this.owner);
        repository.setOwner(owner);

        Intent intent = RepositoryDetailActivity.getIntent(context, repository);
        return repositoryDetailActivityIntentsTestRule.launchActivity(intent);
    }

    @Test
    public void testActivityLaunch(){
        // act
        startActivity();

        // assert
        onView(withId(R.id.rv_contents));
    }

    @Test
    public void testContentsLoaded(){
        // arrange
        final Integer expectedCount = 16;

        // act
        startActivity();

        // assert
        verifyRecyclerViewCount(expectedCount);
    }

    @Test
    public void testContentsOnClick(){
        // arrange
        final Integer initialCount = 16;
        final Integer finalCount =  4;

        // act
        startActivity();

        // assert
        verifyRecyclerViewCount(initialCount);
        Mockito.verify(presenter).getRepositoryContent();

        onView(withId(R.id.rv_contents))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        verifyRecyclerViewCount(finalCount);
        onView(withId(R.id.rv_contents))
                .check(ViewAssertions.matches(hasDescendant(withText("src"))));

        Mockito.verify(presenter).getRepositorySubDirectory(Mockito.anyString());
    }

    @Test
    public void testSubDirectoryBackButton(){
        // arrange
        final Integer initialCount = 16;
        final Integer subDirectoryCount =  4;

        // act
        startActivity();

        // assert
        verifyRecyclerViewCount(initialCount);
        Mockito.verify(presenter).getRepositoryContent();

        onView(withId(R.id.rv_contents))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        verifyRecyclerViewCount(subDirectoryCount);

        Espresso.pressBack();

        verifyRecyclerViewCount(initialCount);
    }

    /**
     * Verify the contents recycler view has the expected number of items
     * @param expectedCount Expected Number of Items
     */
    private void verifyRecyclerViewCount(final Integer expectedCount){
        onView(withId(R.id.rv_contents)).check(new ViewAssertion() {
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
    }
}
