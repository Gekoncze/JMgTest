package cz.mg.test.exceptions;

import cz.mg.collections.list.List;
import cz.mg.test.Test;


public class TestException extends RuntimeException {
    private final Test test;
    private final List<TestCaseException> failedTestCases;

    public TestException(Test test, List<TestCaseException> failedTestCases) {
        this.test = notNull(test);
        this.failedTestCases = notNull(failedTestCases);
    }

    public Test getTest() {
        return test;
    }

    public List<TestCaseException> getFailedTestCases() {
        return failedTestCases;
    }

    private static <T> T notNull(T cause){
        if(cause == null) throw new NullPointerException();
        return cause;
    }
}
