package com.pluralsight.dagger2example;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import com.pluralsight.dagger2example.application.MockSampleApplication;

public class MockSampleTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        return super.newApplication(cl, MockSampleApplication.class.getName(), context);
    }
}
