package cz.mg.test;

import cz.mg.collections.Clump;
import cz.mg.test.exceptions.DiscrepancyException;

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
        if(expectation != reality){
            error("Expected " + getName(expectation) + ", but got " + getName(reality) + ".");
        }
    }

    default void assertEqualsNullOrNotNull(Object expectation, Object reality){
        if(expectation == null && reality != null){
            error("Expected null but got not null.");
        }

        if(expectation != null && reality == null){
            error("Expected not null but got null.");
        }
    }

    default void assertContains(Clump clump, Object wanted){
        if(!Clump.contains(clump, wanted)){
            throw new DiscrepancyException("Missing " + getName(wanted) + " in collection.");
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
            throw new DiscrepancyException("Too few occurrences of " + getName(wanted) + " in collection. Expected " + expectedCount + ", but got " + actualCount + ".");
        }

        if(actualCount > expectedCount){
            throw new DiscrepancyException("Too many occurrences of " + getName(wanted) + " in collection. Expected " + expectedCount + ", but got " + actualCount + ".");
        }
    }

    default void assertExceptionThrown(Runnable runnable){
        try {
            runnable.run();
            error("Expected an exception, but got none.");
        } catch (RuntimeException e){
        }
    }
}
