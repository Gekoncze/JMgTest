package cz.mg.test.event.testpackage;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.classdetector.Package;
import cz.mg.test.exceptions.TestPackageException;


public @Utility interface TestPackageListener {
    void onStart(@Mandatory Package testPackage);
    void onSuccess(@Mandatory Package testPackage);
    void onFailure(@Mandatory Package testPackage, @Mandatory TestPackageException testPackageException);
    void onEnd(@Mandatory Package testPackage);
}
