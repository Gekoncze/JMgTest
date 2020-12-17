package cz.mg.test.runner;

import cz.mg.collections.list.List;
import cz.mg.test.Test;
import cz.mg.test.event.TestAdapter;
import cz.mg.test.event.TestListener;
import cz.mg.test.exceptions.TestException;
import static cz.mg.test.runner.TestUtils.*;


public class BulkTestRunner {
    private final TestRunner testRunner = new TestRunner();

    private final TestListener testListener = new TestAdapter() {
        @Override
        public void onStart(Test test) {
            print("    Running " + test.getClass().getSimpleName() + " ... ");
        }

        @Override
        public void onSuccess(Test test) {
            println("OK");
        }

        @Override
        public void onFailure(Test test, TestException testException) {
            println("FAILED");
        }
    };

    public BulkTestRunner() {
        testRunner.getTestListeners().add(testListener);
    }

    public void run(Test... tests){
        println("Running tests");

        List<TestException> failedTests = testRunner.runTests(new List<>(tests));
        println();

        if(failedTests.isEmpty()){
            println("All tests passed.");
        } else {
            println("Failed tests:");
            for(TestException failedTest : failedTests){
                println("    " + failedTest.getTest().getClass().getSimpleName());
            }
            println();
            println("Total of " + testRunner.getTestDetails().getFailedTests().count() + " tests failed.");
            println("Total of " + testRunner.getTestDetails().getFailedTestCases().count() + " test cases failed.");
            println("Run failed tests individually to see more details.");
        }
    }
}
