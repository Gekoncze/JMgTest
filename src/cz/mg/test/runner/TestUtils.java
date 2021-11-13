package cz.mg.test.runner;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.text.Named;

import java.util.Objects;


public @Utility class TestUtils {
    public static @Mandatory String getName(@Optional Object object){
        if(object == null){
            return "null";
        } else if(object instanceof Named){
            return "'" + ((Named) object).getName() + "'";
        } else {
            return getSimpleName(Objects.toString(object));
        }
    }

    public static @Mandatory String getSimpleName(@Mandatory String className){
        int index = className.lastIndexOf(".") + 1;
        if(index > className.length()) index = className.length();
        return className.substring(index);
    }
}
