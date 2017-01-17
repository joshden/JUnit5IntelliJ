package com.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Person {
    private final String givenName;
    private final String surname;
    private final LocalDate dateOfBirth;

    Person(String givenName, String surname, LocalDate dateOfBirth) {
        this.givenName = givenName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    String getDisplayName() {
        return surname + ", " + givenName;
    }

    long getAge() {
        return ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    public static void main(String... args) {
        Person person = new Person("Joey", "Doe", LocalDate.parse("2013-01-12"));
        System.out.println(person.getDisplayName() + ": " + person.getAge() + " years");
        // Doe, Joey: 4 years
    }
}