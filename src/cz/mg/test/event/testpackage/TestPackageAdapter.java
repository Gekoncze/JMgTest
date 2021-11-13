package cz.mg.test.event.testpackage;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.classdetector.Package;
import cz.mg.test.exceptions.TestPackageException;


public abstract @Utility class TestPackageAdapter implements TestPackageListener {
    @Override
    public void onStart(@Mandatory Package testPackage) {
    }

    @Override
    public void onSuccess(@Mandatory Package testPackage) {
    }

    @Override
    public void onFailure(@Mandatory Package testPackage, @Mandatory TestPackageException testPackageException) {
    }

    @Override
    public void onEnd(@Mandatory Package testPackage) {
    }
}
