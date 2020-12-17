package cz.mg.test.runner;

import cz.mg.collections.list.List;
import cz.mg.test.Test;
import cz.mg.test.event.TestAdapter;
import cz.mg.test.event.TestCaseAdapter;
import cz.mg.test.event.TestCaseListener;
import cz.mg.test.event.TestListener;
import cz.mg.test.exceptions.TestCaseException;
import cz.mg.test.exceptions.TestException;

import java.lang.reflect.Method;


public class TestDetails {
    private final List<TestException> failedTests = new List<>();
    private final List<TestCaseException> failedTestCases = new List<>();

    final TestListener testListener = new TestAdapter() {
        @Override
        public void onFailure(Test test, TestException testException) {
            failedTests.addLast(testException);
        }
    };

    final TestCaseListener testCaseListener = new TestCaseAdapter() {
        @Override
        public void onFailure(Test test, Method testCase, TestCaseException testCaseException) {
            failedTestCases.addLast(testCaseException);
        }
    };

    public List<TestException> getFailedTests() {
        return failedTests;
    }

    public List<TestCaseException> getFailedTestCases() {
        return failedTestCases;
    }
}
