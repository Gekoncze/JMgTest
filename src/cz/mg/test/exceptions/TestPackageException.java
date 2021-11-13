package cz.mg.test.exceptions;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Link;
import cz.mg.annotations.storage.Part;
import cz.mg.classdetector.Package;
import cz.mg.collections.list.List;


public class TestPackageException extends RuntimeException {
    private final @Mandatory @Link Package testPackage;
    private final @Mandatory @Part List<TestClassException> failedTests;

    public TestPackageException(@Mandatory Package testPackage, @Mandatory List<TestClassException> failedTests) {
        this.testPackage = testPackage;
        this.failedTests = failedTests;
    }

    public @Mandatory Package getTestPackage() {
        return testPackage;
    }

    public @Mandatory List<TestClassException> getFailedTests() {
        return failedTests;
    }
}
