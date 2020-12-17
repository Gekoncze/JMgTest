package cz.mg.test.runner;

import cz.mg.test.Test;
import cz.mg.test.event.TestCaseAdapter;
import cz.mg.test.event.TestCaseListener;
import cz.mg.test.exceptions.TestCaseException;
import cz.mg.test.exceptions.TestException;
import java.lang.reflect.Method;
import static cz.mg.test.runner.TestUtils.*;


public class SingleTestRunner {
    private final TestRunner testRunner = new TestRunner();

    private final TestCaseListener testCaseListener = new TestCaseAdapter() {
        @Override
        public void onStart(Test test, Method testCase) {
            print("    Running " + testCase.getName() + " ... ");
        }

        @Override
        public void onSuccess(Test test, Method testCase) {
            println("OK");
        }

        @Override
        public void onFailure(Test test, Method testCase, TestCaseException testCaseException) {
            println("FAILED");
        }
    };

    public SingleTestRunner() {
        testRunner.getTestCaseListeners().add(testCaseListener);
    }

    public void run(Test test){
        println("Running " + test.getClass().getSimpleName());

        TestException failedTest = testRunner.runTest(test);
        println();

        if(failedTest == null){
            println("All test cases passed.");
        } else {
            println("Failed test cases:");
            for(TestCaseException failedTestCase : failedTest.getFailedTestCases()){
                println("    " + failedTestCase.getTestCase().getName());
            }
            println();
            for(TestCaseException failedTestCase : failedTest.getFailedTestCases()){
                println("Exception in " + failedTestCase.getTestCase().getName() + ":");
                String type = failedTestCase.getCause().getClass().getSimpleName();
                String message = failedTestCase.getCause().getMessage();
                println("    " + type + " " + '"' + message + '"');
                printStackTrace(failedTestCase.getCause());
                println();
            }
        }
    }
}
