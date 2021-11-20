package cz.mg.test.exceptions;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Link;

import java.lang.reflect.Method;


public class TestMethodException extends RuntimeException {
    private final @Mandatory @Link Method testMethod;

    public TestMethodException(@Mandatory Method testMethod, @Mandatory Throwable cause) {
        super(cause);
        this.testMethod = testMethod;
    }

    public @Mandatory Method getTestMethod() {
        return testMethod;
    }
}
