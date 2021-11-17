package cz.mg.test.cli.runners.special;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.classdetector.Package;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListSorter;
import cz.mg.test.Test;
import cz.mg.test.cli.runners.BulkTestPackageRunner;

import java.util.Comparator;


public class AllTestRunner {
    public void run(@Mandatory Package packagee){
        List<Package> allPackages = new List<>();
        addAll(packagee, allPackages);
        ListSorter.sortInPlace(allPackages, Comparator.comparing(Package::getFullName));
        new BulkTestPackageRunner().run(allPackages);
    }

    private void addAll(@Mandatory Package packagee, @Mandatory List<Package> allPackages){
        if(containsTests(packagee)){
            allPackages.addLast(packagee);
        }

        for(Package subPackage : packagee.getPackages()){
            addAll(subPackage, allPackages);
        }
    }

    private boolean containsTests(@Mandatory Package packagee){
        for(Class clazz : packagee.getClasses()){
            if(Test.class.isAssignableFrom(clazz)){
                return true;
            }
        }
        return false;
    }
}
