package cz.mg.test.event.testmethod;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.collections.list.List;
import cz.mg.test.exceptions.TestMethodException;

import java.lang.reflect.Method;


public @Utility class TestMethodListeners implements TestMethodListener {
    private final @Mandatory @Part List<TestMethodListener> listeners = new List<>();

    public TestMethodListeners() {
    }

    public void add(@Mandatory TestMethodListener listener){
        listeners.addLast(listener);
    }

    @Override
    public void onStart(@Mandatory Method testMethod) {
        for(TestMethodListener listener : listeners){
            listener.onStart(testMethod);
        }
    }

    @Override
    public void onSuccess(@Mandatory Method testMethod) {
        for(TestMethodListener listener : listeners){
            listener.onSuccess(testMethod);
        }
    }

    @Override
    public void onFailure(@Mandatory Method testMethod, @Mandatory TestMethodException testMethodException) {
        for(TestMethodListener listener : listeners){
            listener.onFailure(testMethod, testMethodException);
        }
    }

    @Override
    public void onEnd(@Mandatory Method testMethod) {
        for(TestMethodListener listener : listeners){
            listener.onEnd(testMethod);
        }
    }
}
