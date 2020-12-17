package cz.mg.test.event;

import cz.mg.test.Test;
import cz.mg.test.exceptions.TestCaseException;
import java.lang.reflect.Method;


public interface TestCaseListener {
    void onStart(Test test, Method testCase);
    void onSuccess(Test test, Method testCase);
    void onFailure(Test test, Method testCase, TestCaseException testCaseException);
    void onEnd(Test test, Method testCase);
}
