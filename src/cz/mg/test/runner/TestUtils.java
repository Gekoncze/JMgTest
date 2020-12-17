package cz.mg.test.runner;

import cz.mg.collections.text.Named;
import cz.mg.test.Test;
import cz.mg.test.exceptions.TestCaseException;
import java.lang.reflect.Method;
import java.util.Objects;


public class TestUtils {
    public static void print(String s){
        System.out.print(s);
    }

    public static void println(String s){
        System.out.println(s);
    }

    public static void println(){
        System.out.println();
    }

    public static String getName(Object object){
        if(object == null){
            return "null";
        } else if(object instanceof Named){
            return "'" + ((Named) object).getName() + "'";
        } else {
            return getSimpleName(Objects.toString(object));
        }
    }

    private static String getSimpleName(String className){
        int index = className.lastIndexOf(".") + 1;
        if(index < 0) index = 0;
        if(index > className.length()) index = className.length();
        return className.substring(index);
    }

    public static void printStackTrace(Throwable e){
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

    public static Throwable unwrap(Throwable e){
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }

    public static TestCaseException wrap(Test test, Method testCase, Throwable e){
        return new TestCaseException(test, testCase, unwrap(e));
    }
}
