package cz.mg.test.event.testclass;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.exceptions.TestClassException;


public @Utility interface TestClassListener {
    void onStart(@Mandatory Class testClass);
    void onSuccess(@Mandatory Class testClass);
    void onFailure(@Mandatory Class testClass, @Mandatory TestClassException testClassException);
    void onEnd(@Mandatory Class testClass);
}
