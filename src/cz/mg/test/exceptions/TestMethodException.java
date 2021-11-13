package cz.mg.test.exceptions;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Link;

import java.lang.reflect.Method;


public class TestMethodException extends RuntimeException {
    private final @Mandatory @Link Method testMethod;

    public TestMethodException(@Mandatory Method testMethid, @Mandatory Throwable cause) {
        super(unwrap(cause));
        this.testMethod = testMethid;
    }

    public @Mandatory Method getTestMethod() {
        return testMethod;
    }

    private static @Mandatory Throwable unwrap(@Mandatory Throwable e){
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }
}
