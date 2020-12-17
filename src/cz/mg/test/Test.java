package cz.mg.test;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
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

    default void assertContains(Clump colelction, Object... items){
        List<Object> missing = new List<>();
        for(Object item : items){
            if(!Clump.contains(colelction, item)){
                missing.addLast(item);
            }
        }
        if(!missing.isEmpty()){
            List<String> names = new List<>();
            for(Object miss : missing){
                names.addLast(
                    getName(miss)
                );
            }
            throw new DiscrepancyException("Missing " + names.toText(", ") + " in collection.");
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
