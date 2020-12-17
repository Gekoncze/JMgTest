package cz.mg.test.exceptions;

import cz.mg.test.Test;

import java.lang.reflect.Method;


public class TestCaseException extends RuntimeException {
    private final Test test;
    private final Method testCase;

    public TestCaseException(Test test, Method testCase, Throwable cause) {
        super(notNull(cause));
        this.test = notNull(test);
        this.testCase = notNull(testCase);
    }

    public Test getTest() {
        return test;
    }

    public Method getTestCase() {
        return testCase;
    }

    private static <T> T notNull(T cause){
        if(cause == null) throw new NullPointerException();
        return cause;
    }
}
