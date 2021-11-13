package cz.mg.test.event.testpackage;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.classdetector.Package;
import cz.mg.collections.list.List;
import cz.mg.test.exceptions.TestPackageException;


public @Utility class TestPackageListeners implements TestPackageListener {
    private final @Mandatory @Part List<TestPackageListener> listeners = new List<>();

    public TestPackageListeners() {
    }

    public void add(@Mandatory TestPackageListener listener){
        listeners.addLast(listener);
    }

    @Override
    public void onStart(@Mandatory Package testPackage) {
        for(TestPackageListener listener : listeners){
            listener.onStart(testPackage);
        }
    }

    @Override
    public void onSuccess(@Mandatory Package testPackage) {
        for(TestPackageListener listener : listeners){
            listener.onSuccess(testPackage);
        }
    }

    @Override
    public void onFailure(@Mandatory Package testPackage, @Mandatory TestPackageException testPackageException) {
        for(TestPackageListener listener : listeners){
            listener.onFailure(testPackage, testPackageException);
        }
    }

    @Override
    public void onEnd(@Mandatory Package testPackage) {
        for(TestPackageListener listener : listeners){
            listener.onEnd(testPackage);
        }
    }
}
