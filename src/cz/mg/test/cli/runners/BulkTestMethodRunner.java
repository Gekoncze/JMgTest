package cz.mg.test.cli.runners;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.collections.list.List;
import cz.mg.test.cli.CommandLineTestRunner;
import cz.mg.test.event.testmethod.TestMethodAdapter;
import cz.mg.test.event.testmethod.TestMethodListener;
import cz.mg.test.exceptions.TestMethodException;
import cz.mg.test.runner.TestRunner;

import java.lang.reflect.Method;


public @Utility class BulkTestMethodRunner extends CommandLineTestRunner {
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

    public BulkTestMethodRunner() {
        testRunner.getTestMethodListeners().add(testMethodListener);
    }

    public void run(@Mandatory Method... testMethods){
        run(new List<>(testMethods));
    }

    public void run(@Mandatory List<Method> testMethods){
        println("Running tests");

        List<TestMethodException> failedTestMethods = testRunner.runTestMethods(testMethods);
        println();

        if(failedTestMethods.isEmpty()){
            println("All tests passed.");
        } else {
            println("Failed test cases:");
            for(TestMethodException failedTestMethod : failedTestMethods){
                println("    " + failedTestMethod.getTestMethod().getName());
            }
            println();
            println("Total of " + testRunner.getTestDetails().getFailedTestCases().count() + " test cases failed.");
            println("Run failed tests individually to see more details.");
        }
    }
}
