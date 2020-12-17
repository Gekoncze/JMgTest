package cz.mg.test.event;

import cz.mg.test.Test;
import cz.mg.test.exceptions.TestCaseException;

import java.lang.reflect.Method;


public abstract class TestCaseAdapter implements TestCaseListener {
    @Override
    public void onStart(Test test, Method testCase) {
    }

    @Override
    public void onSuccess(Test test, Method testCase) {
    }

    @Override
    public void onFailure(Test test, Method testCase, TestCaseException testCaseException) {
    }

    @Override
    public void onEnd(Test test, Method testCase) {
    }
}
