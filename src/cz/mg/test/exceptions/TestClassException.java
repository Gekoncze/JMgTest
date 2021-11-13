package cz.mg.test.exceptions;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Link;
import cz.mg.annotations.storage.Part;
import cz.mg.collections.list.List;


public class TestClassException extends RuntimeException {
    private final @Mandatory @Link Class testClass;
    private final @Mandatory @Part List<TestMethodException> failedTestMethods;

    public TestClassException(@Mandatory Class testClass, @Mandatory List<TestMethodException> failedTestMethods) {
        this.testClass = testClass;
        this.failedTestMethods = failedTestMethods;
    }

    public @Mandatory Class getTestClass() {
        return testClass;
    }

    public @Mandatory List<TestMethodException> getFailedTestMethods() {
        return failedTestMethods;
    }
}
