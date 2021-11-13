package cz.mg.test.cli.runners.special;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.classdetector.ClassDetector;
import cz.mg.classdetector.ClassPackager;
import cz.mg.classdetector.ClassResolver;
import cz.mg.classdetector.Package;
import cz.mg.collections.ToStringBuilder;
import cz.mg.collections.list.List;
import cz.mg.test.cli.CommandLineTestRunner;
import cz.mg.test.cli.runners.SingleTestPackageRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringTokenizer;


public class PackageTestRunner extends CommandLineTestRunner {
    public void run(@Mandatory String pathToJarFile, @Mandatory Class testPackageClass){
        try(FileInputStream stream = new FileInputStream(pathToJarFile)){
            Package root = new ClassPackager().pack(
                new ClassResolver().resolve(
                    new ClassDetector().find(stream)
                )
            );

            String targetPackageFullName = getPackageFullName(testPackageClass);
            Package targetPackage = find(root, targetPackageFullName);
            if(targetPackage != null){
                new SingleTestPackageRunner().run(targetPackage);
            } else {
                throw new IllegalArgumentException("Could not find package '" + targetPackageFullName + "'.");
            }
        } catch (IOException e){
            throw new IllegalArgumentException("Could not open jar file '" + pathToJarFile + "'.");
        }
    }

    private @Optional Package find(@Mandatory Package packagee, @Mandatory String packageFullName){
        if(packagee.getFullName().equals(packageFullName)){
            return packagee;
        }

        for(Package subPackage : packagee.getPackages()){
            Package foundPackage = find(subPackage, packageFullName);
            if(foundPackage != null) return foundPackage;
        }

        return null;
    }

    private @Mandatory String getPackageFullName(@Mandatory Class testPackageClass){
        StringTokenizer tokenizer = new StringTokenizer(testPackageClass.getName(), ".");
        List<String> path = new List<>();
        while(tokenizer.hasMoreTokens()){
            path.addLast(tokenizer.nextToken());
        }
        path.removeLast();
        return new ToStringBuilder<>(path).delim(".").build();
    }
}
