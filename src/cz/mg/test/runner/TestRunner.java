package cz.mg.test.runner;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Part;
import cz.mg.classdetector.Package;
import cz.mg.collections.list.List;
import cz.mg.test.Test;
import cz.mg.test.annotations.Disabled;
import cz.mg.test.annotations.TestCase;
import cz.mg.test.event.testclass.TestClassListeners;
import cz.mg.test.event.testmethod.TestMethodListeners;
import cz.mg.test.event.testpackage.TestPackageListeners;
import cz.mg.test.exceptions.TestMethodException;
import cz.mg.test.exceptions.TestClassException;
import cz.mg.test.exceptions.TestPackageException;

import java.lang.reflect.Method;


public @Utility class TestRunner {
    private final @Mandatory @Part TestPackageListeners testPackageListeners = new TestPackageListeners();
    private final @Mandatory @Part TestClassListeners testListeners = new TestClassListeners();
    private final @Mandatory @Part TestMethodListeners testMethodListeners = new TestMethodListeners();
    private final @Mandatory @Part TestDetails testDetails = new TestDetails();

    public TestRunner() {
        testPackageListeners.add(testDetails.testPackageListener);
        testListeners.add(testDetails.testClassListener);
        testMethodListeners.add(testDetails.testMethodListener);
    }

    public @Mandatory TestPackageListeners getTestPackageListeners() {
        return testPackageListeners;
    }

    public @Mandatory TestClassListeners getTestListeners() {
        return testListeners;
    }

    public @Mandatory TestMethodListeners getTestMethodListeners() {
        return testMethodListeners;
    }

    public @Mandatory TestDetails getTestDetails() {
        return testDetails;
    }

    public @Mandatory List<TestPackageException> runTestPackages(@Mandatory List<Package> testPackages){
        List<TestPackageException> failedTestPackages = new List<>();
        for(Package testPackage : testPackages){
            TestPackageException result = runTestPackage(testPackage);
            if(result != null) failedTestPackages.addLast(result);
        }
        return failedTestPackages;
    }

    public @Optional TestPackageException runTestPackage(@Mandatory Package testPackage){
        TestPackageException result = null;
        testPackageListeners.onStart(testPackage);

        List<TestClassException> failedTests = runTestClasses(getTests(testPackage));
        if(failedTests.isEmpty()){
            testPackageListeners.onSuccess(testPackage);
        } else {
            result = new TestPackageException(testPackage, failedTests);
            testPackageListeners.onFailure(testPackage, result);
        }

        testPackageListeners.onEnd(testPackage);
        return result;
    }

    public @Mandatory List<TestClassException> runTestClasses(@Mandatory List<Class> tests){
        List<TestClassException> failedTests = new List<>();
        for(Class test : tests){
            TestClassException result = runTestClass(test);
            if(result != null) failedTests.addLast(result);
        }
        return failedTests;
    }

    public @Optional TestClassException runTestClass(@Mandatory Class testClass){
        TestClassException result = null;
        testListeners.onStart(testClass);

        List<TestMethodException> failedTestCases = runTestMethods(getTestMethods(testClass));
        if(failedTestCases.isEmpty()){
            testListeners.onSuccess(testClass);
        } else {
            result = new TestClassException(testClass, failedTestCases);
            testListeners.onFailure(testClass, result);
        }

        testListeners.onEnd(testClass);
        return result;
    }

    public @Mandatory List<TestMethodException> runTestMethods(@Mandatory List<Method> testMethods){
        List<TestMethodException> failedTestCases = new List<>();
        for(Method testCase : testMethods){
            TestMethodException result = runTestMethod(testCase);
            if(result != null) failedTestCases.addLast(result);
        }
        return failedTestCases;
    }

    public @Optional TestMethodException runTestMethod(@Mandatory Method testMethod){
        TestMethodException result = null;
        testMethodListeners.onStart(testMethod);

        try {
            Class testClass = testMethod.getDeclaringClass();
            Test test = (Test) testClass.getConstructor().newInstance();
            testMethod.invoke(test);
            testMethodListeners.onSuccess(testMethod);
        } catch (Throwable e){
            result = new TestMethodException(testMethod, e);
            testMethodListeners.onFailure(testMethod, result);
        }

        testMethodListeners.onEnd(testMethod);
        return result;
    }

    private static @Mandatory List<Class> getTests(@Mandatory Package testPackage){
        List<Class> tests = new List<>();
        for(Class clazz : testPackage.getClasses()){
            if(Test.class.isAssignableFrom(clazz)){
                tests.addLast(clazz);
            }
        }
        return tests;
    }

    private static @Mandatory List<Method> getTestMethods(@Mandatory Class testClass){
        List<Method> testCases = new List<>();
        for(Method method : testClass.getDeclaredMethods()){
            if(method.isAnnotationPresent(TestCase.class)){
                if(!method.isAnnotationPresent(Disabled.class)){
                    TestCase annotation = method.getAnnotation(TestCase.class);
                    testCases.add(method, annotation.order());
                }
            }
        }
        return testCases;
    }
}
