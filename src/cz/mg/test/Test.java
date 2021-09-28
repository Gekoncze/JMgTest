package cz.mg.test;

import cz.mg.collections.Clump;
import cz.mg.test.exceptions.DiscrepancyException;

import java.util.Objects;

import static cz.mg.test.runner.TestUtils.getName;


public interface Test {
    default void error(String message){
        throw new DiscrepancyException(message);
    }

    default void assertNull(Object object){
        if(object != null){
            error("Unexpected non-null value.");
        }
    }

    default void assertNotNull(Object object){
        if(object == null){
            error("Unexpected null value.");
        }
    }

    default void assertEquals(Object expectation, Object reality){
        if(!Objects.equals(expectation, reality)){
            error("Expected '" + expectation + "', but got '" + reality + "'.");
        }
    }

    default void assertSame(Object expectation, Object reality){
        if(expectation != reality){
            error("Expected '" + getName(expectation) + "' to be the same object as '" + getName(reality) + "'.");
        }
    }

    default void assertNotSame(Object expectation, Object reality){
        if(expectation == reality){
            error("Expected '" + getName(expectation) + "' to be different object than '" + getName(reality) + "'.");
        }
    }

    default void assertExistenceEquals(Object expectation, Object reality){
        if(expectation == null && reality != null){
            error("Expected null, but got not null.");
        }

        if(expectation != null && reality == null){
            error("Expected not null, but got null.");
        }
    }

    default void assertTypeSame(Object expectation, Object reality){
        assertExistenceEquals(expectation, reality);
        if(expectation != null && reality != null){
            if(expectation.getClass() != reality.getClass()){
                error("Expected type '" + expectation.getClass().getSimpleName() + "', but got '" + reality.getClass().getSimpleName() + "'.");
            }
        }
    }

    default void assertContains(Clump clump, Object wanted){
        if(!Clump.contains(clump, wanted)){
            throw new DiscrepancyException("Missing '" + getName(wanted) + "' in collection.");
        }
    }

    default void assertNotContains(Clump clump, Object wanted){
        if(Clump.contains(clump, wanted)){
            throw new DiscrepancyException("Unexpected '" + getName(wanted) + "' in collection.");
        }
    }

    default void assertContains(Clump clump, Object wanted, int expectedCount){
        int actualCount = 0;
        for(Object object : clump){
            if(object == wanted){
                actualCount++;
            }
        }

        if(actualCount < expectedCount){
            throw new DiscrepancyException("Too few occurrences of '" + getName(wanted) + "' in collection. Expected " + expectedCount + ", but got " + actualCount + ".");
        }

        if(actualCount > expectedCount){
            throw new DiscrepancyException("Too many occurrences of '" + getName(wanted) + "' in collection. Expected " + expectedCount + ", but got " + actualCount + ".");
        }
    }

    default void assertExceptionThrown(RunnableThrowable runnable){
        try {
            runnable.run();
            error("Expected an exception, but got none.");
        } catch (Exception e){
        }
    }

    default void assertExceptionThrown(Class<? extends Exception> type, RunnableThrowable runnable){
        try {
            runnable.run();
            error("Expected an exception, but got none.");
        } catch (Exception e){
            if(!type.isAssignableFrom(e.getClass())){
                error("Expected an exception of type '" + type.getSimpleName() + "', but got '" + e.getClass().getSimpleName() + "'.");
            }
        }
    }

    default void assertExceptionNotThrown(RunnableThrowable runnable){
        try {
            runnable.run();
        } catch (Exception e){
            error("Expected no exception, but got '" + e.getClass().getSimpleName() + "'.");
        }
    }
}
