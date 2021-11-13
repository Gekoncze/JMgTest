package cz.mg.test.event.testmethod;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.exceptions.TestMethodException;

import java.lang.reflect.Method;


public abstract @Utility class TestMethodAdapter implements TestMethodListener {
    @Override
    public void onStart(@Mandatory Method testMethod) {
    }

    @Override
    public void onSuccess(@Mandatory Method testMethod) {
    }

    @Override
    public void onFailure(@Mandatory Method testMethod, @Mandatory TestMethodException testMethodException) {
    }

    @Override
    public void onEnd(@Mandatory Method testMethod) {
    }
}
