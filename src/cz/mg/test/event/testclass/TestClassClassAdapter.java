package cz.mg.test.event.testclass;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.exceptions.TestClassException;


public abstract @Utility class TestClassClassAdapter implements TestClassListener {
    @Override
    public void onStart(@Mandatory Class testClass) {
    }

    @Override
    public void onSuccess(@Mandatory Class testClass) {
    }

    @Override
    public void onFailure(@Mandatory Class testClass, @Mandatory TestClassException testClassException) {
    }

    @Override
    public void onEnd(@Mandatory Class testClass) {
    }
}
