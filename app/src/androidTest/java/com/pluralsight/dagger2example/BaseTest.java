package com.pluralsight.dagger2example;

import android.content.Context;
import android.util.Log;

import com.pluralsight.dagger2example.application.MockSampleApplication;
import com.pluralsight.dagger2example.rules.IdlingResourceRule;
import com.pluralsight.dagger2example.utils.AssetStringUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class BaseTest {

    @ClassRule
    public static IdlingResourceRule idlingResourceRule;

    protected static MockSampleApplication application;

    @BeforeClass
    public static void testSetup() {
        Context context = getInstrumentation()
                .getTargetContext()
                .getApplicationContext();

        application = (MockSampleApplication) MockSampleApplication.getApplication(context);
        OkHttpClient okHttpClient = application
                .getApplicationComponent()
                .okhttp();
        idlingResourceRule = new IdlingResourceRule(okHttpClient);

        try {
            application.getMockWebServer().start();
        } catch (IOException e) {
            Log.e("BaseTest", "Could not start mock web server", e);
        }
    }

    /**
     * Shutdown mock webserver
     */
    @AfterClass
    public static void testTearDown() {
        try {
            application.getMockWebServer().shutdown();
        } catch (IOException e) {
            Log.e("BaseTest", "Could not stop mock web server", e);
        }
    }


    void setupResponses() {
        application.getMockWebServer().setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) {
                String path = recordedRequest.getPath();
                MockResponse response = new MockResponse();
                response.setResponseCode(200);

                try {
                    if (path.endsWith("contents")) {
                        response.setBody(AssetStringUtil.readAssetString("mock_contents_response.json"));
                    } else if (path.contains("contents")) {
                        response.setBody(AssetStringUtil.readAssetString("mock_content_detail_response.json"));
                    } else if (path.contains("users")) {
                        response.setBody(AssetStringUtil.readAssetString("mock_repository_response.json"));
                    }
                } catch (IOException e) {
                    Log.e("BaseTest", "Could not read file.", e);
                }

                return response;
            }
        });
    }
}
