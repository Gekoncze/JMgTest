package cz.mg.test.cli.runners;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.test.cli.CommandLineTestRunner;
import cz.mg.test.exceptions.TestMethodException;
import cz.mg.test.runner.TestRunner;

import java.lang.reflect.Method;


public @Utility class SingleTestMethodRunner extends CommandLineTestRunner {
    private final @Mandatory @Part TestRunner testRunner = new TestRunner();

    public SingleTestMethodRunner() {
    }

    public void run(@Mandatory Method testMethod){
        println("Running " + testMethod.getName());

        TestMethodException failedTestCase = testRunner.runTestMethod(testMethod);
        println();

        if(failedTestCase == null){
            println("All tests passed.");
        } else {
            println("Failed test case " + failedTestCase.getTestMethod().getName() + ":");
            String type = failedTestCase.getCause().getClass().getSimpleName();
            String message = failedTestCase.getCause().getMessage();
            println("    " + type + " " + '"' + message + '"');
            printStackTrace(failedTestCase.getCause());
            println();
        }
    }
}
