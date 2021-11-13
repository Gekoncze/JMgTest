package cz.mg.test.cli;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;

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

    protected static void printStackTrace(@Mandatory Throwable e){
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
}
