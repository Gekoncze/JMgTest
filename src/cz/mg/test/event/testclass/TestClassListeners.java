package cz.mg.test.event.testclass;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.collections.list.List;
import cz.mg.test.exceptions.TestClassException;


public @Utility class TestClassListeners implements TestClassListener {
    private final @Mandatory @Part List<TestClassListener> listeners = new List<>();

    public TestClassListeners() {
    }

    public void add(@Mandatory TestClassListener listener){
        listeners.addLast(listener);
    }

    @Override
    public void onStart(@Mandatory Class testClass) {
        for(TestClassListener listener : listeners){
            listener.onStart(testClass);
        }
    }

    @Override
    public void onSuccess(@Mandatory Class testClass) {
        for(TestClassListener listener : listeners){
            listener.onSuccess(testClass);
        }
    }

    @Override
    public void onFailure(@Mandatory Class testClass, @Mandatory TestClassException testClassException) {
        for(TestClassListener listener : listeners){
            listener.onFailure(testClass, testClassException);
        }
    }

    @Override
    public void onEnd(@Mandatory Class testClass) {
        for(TestClassListener listener : listeners){
            listener.onEnd(testClass);
        }
    }
}
