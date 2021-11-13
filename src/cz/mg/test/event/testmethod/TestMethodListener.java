package cz.mg.test.event.testmethod;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.exceptions.TestMethodException;

import java.lang.reflect.Method;


public @Utility interface TestMethodListener {
    void onStart(@Mandatory Method testMethod);
    void onSuccess(@Mandatory Method testMethod);
    void onFailure(@Mandatory Method testMethod, @Mandatory TestMethodException testMethodException);
    void onEnd(@Mandatory Method testMethod);
}
