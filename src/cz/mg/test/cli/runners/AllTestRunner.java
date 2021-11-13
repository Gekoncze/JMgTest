package cz.mg.test.cli.runners;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.classdetector.ClassDetector;
import cz.mg.classdetector.ClassPackager;
import cz.mg.classdetector.ClassResolver;
import cz.mg.classdetector.Package;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListSorter;
import cz.mg.test.Test;
import cz.mg.test.cli.CommandLineTestRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;


public class AllTestRunner extends CommandLineTestRunner {
    public void run(@Mandatory String pathToJarFile){
        try(FileInputStream stream = new FileInputStream(pathToJarFile)){
            Package root = new ClassPackager().pack(
                new ClassResolver().resolve(
                    new ClassDetector().find(stream)
                )
            );

            List<Package> allPackages = new List<>();
            collect(root, allPackages);
            ListSorter.sortInPlace(allPackages, Comparator.comparing(a -> a.getFullName()));

            new BulkTestPackageRunner().run(
                (Package[]) new Array<>(allPackages).getJavaArray()
            );
        } catch (IOException e){
            throw new IllegalArgumentException("Could not open jar file '" + pathToJarFile + "'.");
        }
    }

    private void collect(@Mandatory Package packagee, @Mandatory List<Package> allPackages){
        if(hasTests(packagee)){
            allPackages.addLast(packagee);
        }

        for(Package subPackage : packagee.getPackages()){
            collect(subPackage, allPackages);
        }
    }

    private boolean hasTests(@Mandatory Package packagee){
        for(Class clazz : packagee.getClasses()){
            if(Test.class.isAssignableFrom(clazz)){
                return true;
            }
        }
        return false;
    }
}
