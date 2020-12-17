package cz.mg.test.event;

import cz.mg.test.Test;
import cz.mg.test.exceptions.TestException;


public abstract class TestAdapter implements TestListener {
    @Override
    public void onStart(Test test) {
    }

    @Override
    public void onSuccess(Test test) {
    }

    @Override
    public void onFailure(Test test, TestException testException) {
    }

    @Override
    public void onEnd(Test test) {
    }
}
