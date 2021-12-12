package cz.mg.test.cli.runners;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.classdetector.Package;
import cz.mg.collections.list.List;
import cz.mg.test.cli.CommandLineTestRunner;
import cz.mg.test.event.testpackage.TestPackageAdapter;
import cz.mg.test.event.testpackage.TestPackageListener;
import cz.mg.test.exceptions.TestPackageException;
import cz.mg.test.runner.TestRunner;


public @Utility class BulkTestPackageRunner extends CommandLineTestRunner {
    private final @Mandatory @Part TestRunner testRunner = new TestRunner();

    private final @Mandatory @Part TestPackageListener testPackageListener = new TestPackageAdapter() {
        @Override
        public void onStart(@Mandatory Package testPackage) {
            print("    Running " + testPackage.getFullName() + " ... ");
        }

        @Override
        public void onSuccess(@Mandatory Package testPackage) {
            println("OK");
        }

        @Override
        public void onFailure(@Mandatory Package testPackage, @Mandatory TestPackageException testPackageException) {
            println("FAILED");
        }
    };

    public BulkTestPackageRunner() {
        testRunner.getTestPackageListeners().add(testPackageListener);
    }

    public void run(@Mandatory Package... testPackages){
        run(new List<>(testPackages));
    }

    public void run(@Mandatory List<Package> testPackages){
        println("Running tests");

        List<TestPackageException> failedTestPackages = testRunner.runTestPackages(testPackages);
        println();

        if(failedTestPackages.isEmpty()){
            println("All tests passed.");
        } else {
            println("Failed test packages:");
            for(TestPackageException failedPackage : failedTestPackages){
                println("    " + failedPackage.getTestPackage().getFullName());
            }
            println();
            println("Total of " + testRunner.getTestDetails().getFailedTestPackages().count() + " test packages failed.");
            println("Total of " + testRunner.getTestDetails().getFailedTests().count() + " tests failed.");
            println("Total of " + testRunner.getTestDetails().getFailedTestCases().count() + " test cases failed.");
            println("Total of " + testRunner.getTestDetails().getPassedTestPackages().count() + " test packages passed.");
            println("Total of " + testRunner.getTestDetails().getPassedTests().count() + " tests passed.");
            println("Total of " + testRunner.getTestDetails().getPassedTestCases().count() + " test cases passed.");
            println();
            println("Run failed tests individually to see more details.");
        }
    }
}
