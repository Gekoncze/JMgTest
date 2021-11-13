package cz.mg.test.cli.runners;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.test.cli.CommandLineTestRunner;
import cz.mg.test.event.testmethod.TestMethodAdapter;
import cz.mg.test.event.testmethod.TestMethodListener;
import cz.mg.test.exceptions.TestClassException;
import cz.mg.test.exceptions.TestMethodException;
import cz.mg.test.runner.TestRunner;

import java.lang.reflect.Method;


public @Utility class SingleTestClassRunner extends CommandLineTestRunner {
    private final @Mandatory @Part TestRunner testRunner = new TestRunner();

    private final @Mandatory @Part TestMethodListener testMethodListener = new TestMethodAdapter() {
        @Override
        public void onStart(@Mandatory Method testMethod) {
            print("    Running " + testMethod.getName() + " ... ");
        }

        @Override
        public void onSuccess(@Mandatory Method testMethod) {
            println("OK");
        }

        @Override
        public void onFailure(@Mandatory Method testMethod, @Mandatory TestMethodException testMethodException) {
            println("FAILED");
        }
    };

    public SingleTestClassRunner() {
        testRunner.getTestMethodListeners().add(testMethodListener);
    }

    public void run(@Mandatory Class testClass){
        println("Running " + testClass.getSimpleName());

        TestClassException failedTestClass = testRunner.runTestClass(testClass);
        println();

        if(failedTestClass == null){
            println("All tests passed.");
        } else {
            println("Failed test cases:");
            for(TestMethodException failedTestMethod : failedTestClass.getFailedTestMethods()){
                println("    " + failedTestMethod.getTestMethod().getName());
            }
            println();
            for(TestMethodException failedTestCase : failedTestClass.getFailedTestMethods()){
                println("Exception in " + failedTestCase.getTestMethod().getName() + ":");
                String type = failedTestCase.getCause().getClass().getSimpleName();
                String message = failedTestCase.getCause().getMessage();
                println("    " + type + " " + '"' + message + '"');
                printStackTrace(failedTestCase.getCause());
                println();
            }
        }
    }
}
