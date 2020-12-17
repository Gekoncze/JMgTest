package cz.mg.test.event;

import cz.mg.test.Test;
import cz.mg.test.exceptions.TestException;


public interface TestListener {
    void onStart(Test test);
    void onSuccess(Test test);
    void onFailure(Test test, TestException testException);
    void onEnd(Test test);
}
