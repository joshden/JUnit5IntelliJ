package com.example;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void testGetDisplayName() {
        Person person = new Person("Josh", "Hayden", null);
        String displayName = person.getDisplayName();
        assertEquals("Hayden, Josh", displayName);
    }

    @Test
    void testGetAge() {
        LocalDate dateOfBirth = LocalDate.parse("2013-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-17");
        Person person = new Person("Joey", "Doe", dateOfBirth, ()->currentDate);
        long age = person.getAge();
        assertEquals(4, age);
    }

    @Test
    void testPublishAge() {
        LocalDate dateOfBirth = LocalDate.parse("2000-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-01");
        Person person = new Person("Joe", "Sixteen", dateOfBirth, ()->currentDate);
        person.publishAge();
    }

}