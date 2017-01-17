package com.example.birthdays;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class BirthdaysClient {

    public void publishRegularPersonAge(String name, long age) throws IOException {
        System.out.println("publishing " + name + "'s age: " + age);
        // HTTP POST with name and age and possibly throw an exception
    }

    public Collection<String> findFamousNamesOfAge(long age) throws IOException {
        System.out.println("finding famous names of age " + age);
        return Arrays.asList(/* HTTP GET with age and possibly throw an exception */);
    }

    public Collection<String> findFamousNamesBornOn(int month, int dayOfMonth) throws IOException {
        System.out.println("finding famous names born on day " + dayOfMonth + " of month " + month);
        return Arrays.asList(/* HTTP GET with month and day and possibly throw an exception */);
    }
}
