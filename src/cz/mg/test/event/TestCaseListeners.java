package cz.mg.test.event;

import cz.mg.collections.list.List;
import cz.mg.test.Test;
import cz.mg.test.exceptions.TestCaseException;
import java.lang.reflect.Method;


public class TestCaseListeners implements TestCaseListener {
    private final List<TestCaseListener> listeners = new List<>();

    public TestCaseListeners() {
    }

    public void add(TestCaseListener listener){
        listeners.addLast(listener);
    }

    @Override
    public void onStart(Test test, Method testCase) {
        for(TestCaseListener listener : listeners){
            listener.onStart(test, testCase);
        }
    }

    @Override
    public void onSuccess(Test test, Method testCase) {
        for(TestCaseListener listener : listeners){
            listener.onSuccess(test, testCase);
        }
    }

    @Override
    public void onFailure(Test test, Method testCase, TestCaseException testCaseException) {
        for(TestCaseListener listener : listeners){
            listener.onFailure(test, testCase, testCaseException);
        }
    }

    @Override
    public void onEnd(Test test, Method testCase) {
        for(TestCaseListener listener : listeners){
            listener.onEnd(test, testCase);
        }
    }
}
