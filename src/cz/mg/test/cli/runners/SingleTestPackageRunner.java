package cz.mg.test.cli.runners;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.classdetector.Package;
import cz.mg.test.cli.CommandLineTestRunner;
import cz.mg.test.event.testclass.TestClassClassAdapter;
import cz.mg.test.event.testclass.TestClassListener;
import cz.mg.test.exceptions.TestClassException;
import cz.mg.test.exceptions.TestPackageException;
import cz.mg.test.runner.TestRunner;


public @Utility class SingleTestPackageRunner extends CommandLineTestRunner {
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
        public void onFailure(@Mandatory Class testClass, @Mandatory TestClassException testClassException) {
            println("FAILED");
        }
    };

    public SingleTestPackageRunner() {
        testRunner.getTestListeners().add(testClassListener);
    }

    public void run(@Mandatory Package testPackage){
        println("Running " + testPackage.getName());

        TestPackageException failedTestPackage = testRunner.runTestPackage(testPackage);
        println();

        if(failedTestPackage == null){
            println("All tests passed.");
        } else {
            println("Failed tests:");
            for(TestClassException failedTest : failedTestPackage.getFailedTests()){
                println("    " + failedTest.getTestClass().getSimpleName());
            }
            println();
            println("Total of " + testRunner.getTestDetails().getFailedTests().count() + " tests failed.");
            println("Total of " + testRunner.getTestDetails().getFailedTestCases().count() + " test cases failed.");
            println("Run failed tests individually to see more details.");
        }
    }
}
