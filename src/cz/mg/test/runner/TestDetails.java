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
    private final @Mandatory @Part List<Package> passedTestPackages = new List<>();
    private final @Mandatory @Part List<Class> passedTests = new List<>();
    private final @Mandatory @Part List<Method> passedTestCases = new List<>();

    protected final @Mandatory TestPackageListener testPackageListener = new TestPackageAdapter() {
        @Override
        public void onFailure(@Mandatory Package testPackage, @Mandatory TestPackageException testPackageException) {
            failedTestPackages.addLast(testPackageException);
        }

        @Override
        public void onSuccess(@Mandatory Package testPackage) {
            passedTestPackages.addLast(testPackage);
        }
    };

    protected final @Mandatory TestClassListener testClassListener = new TestClassClassAdapter() {
        @Override
        public void onFailure(@Mandatory Class testClass, @Mandatory TestClassException testClassException) {
            failedTests.addLast(testClassException);
        }

        @Override
        public void onSuccess(@Mandatory Class testClass) {
            passedTests.addLast(testClass);
        }
    };

    protected final @Mandatory TestMethodListener testMethodListener = new TestMethodAdapter() {
        @Override
        public void onFailure(@Mandatory Method testMethod, @Mandatory TestMethodException testMethodException) {
            failedTestCases.addLast(testMethodException);
        }

        @Override
        public void onSuccess(@Mandatory Method testMethod) {
            passedTestCases.addLast(testMethod);
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

    public @Mandatory List<Package> getPassedTestPackages() {
        return passedTestPackages;
    }

    public @Mandatory List<Class> getPassedTests() {
        return passedTests;
    }

    public @Mandatory List<Method> getPassedTestCases() {
        return passedTestCases;
    }
}
