package com.example;

import com.example.birthdays.BirthdaysClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonTest {
    private BirthdaysClient birthdaysClient = mock(BirthdaysClient.class);

    @Test
    void testGetDisplayName() {
        Person person = new Person("Josh", "Hayden", null, null, birthdaysClient);
        String displayName = person.getDisplayName();
        assertEquals("Hayden, Josh", displayName);
    }

    @Test
    void testGetAge() {
        LocalDate dateOfBirth = LocalDate.parse("2013-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-17");
        Person person = new Person("Joey", "Doe", dateOfBirth, ()->currentDate, birthdaysClient);
        long age = person.getAge();
        assertEquals(4, age);
    }

    @Test
    void testPublishAge() throws IOException, PersonException {
        LocalDate dateOfBirth = LocalDate.parse("2000-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-01");
        Person person = new Person("Joe", "Sixteen", dateOfBirth, ()->currentDate, birthdaysClient);
        verifyZeroInteractions(birthdaysClient);
        person.publishAge();
        verify(birthdaysClient).publishRegularPersonAge("Joe Sixteen", 16);
    }

    @Test
    void testPublishAge_IOException() throws IOException {
        LocalDate dateOfBirth = LocalDate.parse("2000-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-01");

        Person person = new Person("Joe", "Sixteen", dateOfBirth, ()->currentDate, birthdaysClient);

        IOException ioException = new IOException();
        doThrow(ioException).when(birthdaysClient).publishRegularPersonAge("Joe Sixteen", 16);

        try {
            person.publishAge();
            fail("expected exception not thrown");
        }
        catch (PersonException e) {
            assertSame(ioException, e.getCause());
            assertEquals("Failed to publish Joe Sixteen age 16", e.getMessage());
        }
    }

    @Test
    void testGetThoseInCommon() throws IOException, PersonException {
        LocalDate dateOfBirth = LocalDate.parse("2000-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-01");
        Person person = new Person("Joe", "Sixteen", dateOfBirth, ()->currentDate, birthdaysClient);

        when(birthdaysClient.findFamousNamesOfAge(16)).thenReturn(Arrays.asList("JoeFamous Sixteen", "Another Person"));
        when(birthdaysClient.findFamousNamesBornOn(1, 2)).thenReturn(Arrays.asList("Jan TwoKnown"));

        Set<String> thoseInCommon = person.getThoseInCommon();

        assertAll(
                setContains(thoseInCommon, "Another Person"),
                setContains(thoseInCommon, "Jan TwoKnown"),
                setContains(thoseInCommon, "JoeFamous Sixteen"),
                ()-> assertEquals(3, thoseInCommon.size())
        );
    }

    private <T> Executable setContains(Set<T> set, T expected) {
        return () -> assertTrue(set.contains(expected), "Should contain " + expected);
    }

    @Test
    void testGetThoseInCommon_BornOn_IOException() throws IOException {
        LocalDate dateOfBirth = LocalDate.parse("2000-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-01");
        Person person = new Person("Joe", "Sixteen", dateOfBirth, ()->currentDate, birthdaysClient);

        when(birthdaysClient.findFamousNamesOfAge(16)).thenReturn(Arrays.asList("JoeFamous Sixteen", "Another Person"));
        IOException ioException = new IOException();
        when(birthdaysClient.findFamousNamesBornOn(1, 2)).thenThrow(ioException);

        try {
            person.getThoseInCommon();
            fail("expected exception not thrown");
        }
        catch (PersonException e) {
            assertSame(ioException, e.getCause());
            assertEquals("Error finding famous people born on month 1 day 2", e.getMessage());
        }
    }

    @Test
    void testGetThoseInCommon_OfAge_IOException() throws IOException {
        LocalDate dateOfBirth = LocalDate.parse("2000-01-02");
        LocalDate currentDate = LocalDate.parse("2017-01-01");
        Person person = new Person("Joe", "Sixteen", dateOfBirth, ()->currentDate, birthdaysClient);

        IOException ioException = new IOException();
        when(birthdaysClient.findFamousNamesOfAge(16)).thenThrow(ioException);
        when(birthdaysClient.findFamousNamesBornOn(1, 2)).thenReturn(Arrays.asList("Jan TwoKnown"));

        try {
            person.getThoseInCommon();
            fail("expected exception not thrown");
        }
        catch (PersonException e) {
            assertSame(ioException, e.getCause());
            assertEquals("Error finding famous people of age 16", e.getMessage());
        }
    }


}