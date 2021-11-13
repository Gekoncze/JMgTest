package cz.mg.test.runner;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.classdetector.Package;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadableList;
import cz.mg.test.event.testclass.TestClassClassAdapter;
import cz.mg.test.event.testmethod.TestMethodAdapter;
import cz.mg.test.event.testmethod.TestMethodListener;
import cz.mg.test.event.testclass.TestClassListener;
import cz.mg.test.event.testpackage.TestPackageAdapter;
import cz.mg.test.event.testpackage.TestPackageListener;
import cz.mg.test.exceptions.TestMethodException;
import cz.mg.test.exceptions.TestClassException;
import cz.mg.test.exceptions.TestPackageException;

import java.lang.reflect.Method;


public @Utility class TestDetails {
    private final @Mandatory @Part List<TestPackageException> failedTestPackages = new List<>();
    private final @Mandatory @Part List<TestClassException> failedTests = new List<>();
    private final @Mandatory @Part List<TestMethodException> failedTestCases = new List<>();

    protected final @Mandatory TestPackageListener testPackageListener = new TestPackageAdapter() {
        @Override
        public void onFailure(@Mandatory Package testPackage, @Mandatory TestPackageException testPackageException) {
            failedTestPackages.addLast(testPackageException);
        }
    };

    protected final @Mandatory TestClassListener testClassListener = new TestClassClassAdapter() {
        @Override
        public void onFailure(@Mandatory Class testClass, @Mandatory TestClassException testClassException) {
            failedTests.addLast(testClassException);
        }
    };

    protected final @Mandatory TestMethodListener testMethodListener = new TestMethodAdapter() {
        @Override
        public void onFailure(@Mandatory Method testMethod, @Mandatory TestMethodException testMethodException) {
            failedTestCases.addLast(testMethodException);
        }
    };

    public @Mandatory ReadableList<TestPackageException> getFailedTestPackages() {
        return failedTestPackages;
    }

    public @Mandatory ReadableList<TestClassException> getFailedTests() {
        return failedTests;
    }

    public @Mandatory ReadableList<TestMethodException> getFailedTestCases() {
        return failedTestCases;
    }
}
