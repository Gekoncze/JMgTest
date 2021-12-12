package cz.mg.test.cli;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.runner.TestDetails;

import static cz.mg.test.runner.TestUtils.getSimpleName;


public abstract @Utility class CommandLineTestRunner {
    protected static void print(@Mandatory String s){
        System.out.print(s);
    }

    protected static void println(@Mandatory String s){
        System.out.println(s);
    }

    protected static void println(){
        System.out.println();
    }

    protected static void printException(@Mandatory Throwable e){
        printException(e, true);
    }

    private static void printException(@Mandatory Throwable e, boolean first){
        String type = e.getClass().getSimpleName();
        String message = e.getMessage();
        String causedBy = first ? "" : "Caused by ";
        println("    " + causedBy + type + " " + '"' + message + '"');
        printStackTrace(e);
        println();

        if(e.getCause() != null){
            printException(e.getCause(), false);
        }
    }

    private static void printStackTrace(@Mandatory Throwable e){
        for(int i = 0; i < e.getStackTrace().length; i++){
            StackTraceElement element = e.getStackTrace()[i];
            println(
                "    ." +
                    "(" +
                    getSimpleName(element.getClassName()) +
                    ".java:" +
                    element.getLineNumber() +
                    ")"
            );
        }
    }

    public static void printSummaryResults(@Mandatory TestDetails details){
        if(!details.getFailedTestPackages().isEmpty()){
            println("Total of " + details.getFailedTestPackages().count() + " test packages FAILED.");
        }

        if(!details.getFailedTests().isEmpty()){
            println("Total of " + details.getFailedTests().count() + " tests FAILED.");
        }

        if(!details.getFailedTestCases().isEmpty()){
            println("Total of " + details.getFailedTestCases().count() + " test cases FAILED.");
        }

        if(!details.getPassedTestPackages().isEmpty()){
            println("Total of " + details.getPassedTestPackages().count() + " test packages passed.");
        }

        if(!details.getPassedTests().isEmpty()){
            println("Total of " + details.getPassedTests().count() + " tests passed.");
        }

        if(!details.getPassedTestCases().isEmpty()){
            println("Total of " + details.getPassedTestCases().count() + " test cases passed.");
        }

        if(
            details.getFailedTestPackages().isEmpty() &&
            details.getFailedTests().isEmpty() &&
            details.getFailedTestCases().isEmpty() &&
            details.getPassedTestPackages().isEmpty() &&
            details.getPassedTests().isEmpty() &&
            details.getPassedTestCases().isEmpty()
        ){
            println("WARNING: No tests were run.");
        }
    }
}
