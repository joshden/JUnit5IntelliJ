package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testGetDisplayName() {
        Person person = new Person("Josh", "Hayden");
        String displayName = person.getDisplayName();
        assertEquals("Hayden, Josh", displayName);
    }

}