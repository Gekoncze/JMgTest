package cz.mg.test.runner;

import cz.mg.collections.list.List;
import cz.mg.test.Test;
import cz.mg.test.annotations.Disabled;
import cz.mg.test.annotations.TestCase;
import cz.mg.test.event.TestCaseListeners;
import cz.mg.test.event.TestListeners;
import cz.mg.test.exceptions.TestCaseException;
import cz.mg.test.exceptions.TestException;
import java.lang.reflect.Method;
import static cz.mg.test.runner.TestUtils.*;


public class TestRunner {
    private final TestListeners testListeners = new TestListeners();
    private final TestCaseListeners testCaseListeners = new TestCaseListeners();
    private final TestDetails testDetails = new TestDetails();

    public TestRunner() {
        testListeners.add(testDetails.testListener);
        testCaseListeners.add(testDetails.testCaseListener);
    }

    public TestListeners getTestListeners() {
        return testListeners;
    }

    public TestCaseListeners getTestCaseListeners() {
        return testCaseListeners;
    }

    public TestDetails getTestDetails() {
        return testDetails;
    }

    public List<TestException> runTests(List<Test> tests){
        List<TestException> failedTests = new List<>();
        for(Test test : tests){
            TestException result = runTest(test);
            if(result != null) failedTests.addLast(result);
        }
        return failedTests;
    }

    public TestException runTest(Test test){
        TestException result = null;
        testListeners.onStart(test);

        List<TestCaseException> failedTestCases = runTestCases(test, getTestCases(test.getClass()));
        if(failedTestCases.isEmpty()){
            testListeners.onSuccess(test);
        } else {
            result = new TestException(test, failedTestCases);
            testListeners.onFailure(test, result);
        }

        testListeners.onEnd(test);
        return result;
    }

    public List<TestCaseException> runTestCases(Test test, List<Method> testCases){
        List<TestCaseException> failedTestCases = new List<>();
        for(Method testCase : testCases){
            TestCaseException result = runTestCase(test, testCase);
            if(result != null) failedTestCases.addLast(result);
        }
        return failedTestCases;
    }

    public TestCaseException runTestCase(Test test, Method testCase){
        TestCaseException result = null;
        testCaseListeners.onStart(test, testCase);

        try {
            testCase.invoke(test);
            testCaseListeners.onSuccess(test, testCase);
        } catch (Throwable e){
            result = wrap(test, testCase, e);
            testCaseListeners.onFailure(test, testCase, result);
        }

        testCaseListeners.onEnd(test, testCase);
        return result;
    }

    private static List<Method> getTestCases(Class<? extends Test> clazz){
        List<Method> testCases = new List<>();
        for(Method method : clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(TestCase.class)){
                if(!method.isAnnotationPresent(Disabled.class)){
                    TestCase annotation = method.getAnnotation(TestCase.class);
                    testCases.add(method, annotation.order());
                }
            }
        }
        return testCases;
    }
}
