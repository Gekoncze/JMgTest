package cz.mg.test.cli.runners;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.collections.list.List;
import cz.mg.test.cli.CommandLineTestRunner;
import cz.mg.test.event.testclass.TestClassClassAdapter;
import cz.mg.test.event.testclass.TestClassListener;
import cz.mg.test.exceptions.TestClassException;
import cz.mg.test.runner.TestRunner;


public @Utility class BulkTestClassRunner extends CommandLineTestRunner {
    private final @Mandatory @Part TestRunner testRunner = new TestRunner();

    private final @Mandatory @Part TestClassListener testClassListener = new TestClassClassAdapter() {
        @Override
        public void onStart(@Mandatory Class testClass) {
            print("    Running " + testClass.getSimpleName() + " ... ");
        }

        @Override
        public void onSuccess(@Mandatory Class testClass) {
            println("OK");
        }

        @Override
        public void onFailure(@Mandatory Class testClass, TestClassException testClassException) {
            println("FAILED");
        }
    };

    public BulkTestClassRunner() {
        testRunner.getTestListeners().add(testClassListener);
    }

    public void run(@Mandatory Class... testClasses){
        run(new List<>(testClasses));
    }

    public void run(@Mandatory List<Class> testClasses){
        println("Running tests");

        List<TestClassException> failedTestClasses = testRunner.runTestClasses(testClasses);
        println();

        if(failedTestClasses.isEmpty()){
            println("All tests passed.");
        } else {
            println("Failed tests:");
            for(TestClassException failedTestClass : failedTestClasses){
                println("    " + failedTestClass.getTestClass().getSimpleName());
            }
            println();
            println("Total of " + testRunner.getTestDetails().getFailedTests().count() + " tests failed.");
            println("Total of " + testRunner.getTestDetails().getFailedTestCases().count() + " test cases failed.");
            println("Run failed tests individually to see more details.");
        }
    }
}
