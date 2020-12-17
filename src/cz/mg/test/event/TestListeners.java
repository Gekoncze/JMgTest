package cz.mg.test.event;

import cz.mg.collections.list.List;
import cz.mg.test.Test;
import cz.mg.test.exceptions.TestException;


public class TestListeners implements TestListener {
    private final List<TestListener> listeners = new List<>();

    public TestListeners() {
    }

    public void add(TestListener listener){
        listeners.addLast(listener);
    }

    @Override
    public void onStart(Test test) {
        for(TestListener listener : listeners){
            listener.onStart(test);
        }
    }

    @Override
    public void onSuccess(Test test) {
        for(TestListener listener : listeners){
            listener.onSuccess(test);
        }
    }

    @Override
    public void onFailure(Test test, TestException testException) {
        for(TestListener listener : listeners){
            listener.onFailure(test, testException);
        }
    }

    @Override
    public void onEnd(Test test) {
        for(TestListener listener : listeners){
            listener.onEnd(test);
        }
    }
}
