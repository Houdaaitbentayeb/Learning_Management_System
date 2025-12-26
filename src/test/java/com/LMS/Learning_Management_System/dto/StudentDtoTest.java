package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDtoTest {

    private StudentDto studentDto;

    @Test
    void testParameterizedConstructor() {
        int expectedId = 1001;
        String expectedFirstName = "John";
        String expectedLastName = "Doe";

        studentDto = new StudentDto(expectedId, expectedFirstName, expectedLastName);

        assertAll(
                () -> assertEquals(expectedId, studentDto.getUserAccountId()),
                () -> assertEquals(expectedFirstName, studentDto.getFirstName()),
                () -> assertEquals(expectedLastName, studentDto.getLastName())
        );
    }

    @Test
    void testGetAndSetUserAccountId() {
        studentDto = new StudentDto(1, "Test", "User");
        int newId = 2002;
        studentDto.setUserAccountId(newId);
        assertEquals(newId, studentDto.getUserAccountId());
    }

    @Test
    void testGetAndSetFirstName() {
        studentDto = new StudentDto(1, "Test", "User");
        String newFirstName = "Jane";
        studentDto.setFirstName(newFirstName);
        assertEquals(newFirstName, studentDto.getFirstName());
    }

    @Test
    void testGetAndSetLastName() {
        studentDto = new StudentDto(1, "Test", "User");
        String newLastName = "Smith";
        studentDto.setLastName(newLastName);
        assertEquals(newLastName, studentDto.getLastName());
    }

    @Test
    void testConstructorWithNullValues() {
        studentDto = new StudentDto(3003, null, null);

        assertAll(
                () -> assertEquals(3003, studentDto.getUserAccountId()),
                () -> assertNull(studentDto.getFirstName()),
                () -> assertNull(studentDto.getLastName())
        );
    }

    @Test
    void testConstructorWithEmptyStrings() {
        studentDto = new StudentDto(4004, "", "");

        assertAll(
                () -> assertEquals(4004, studentDto.getUserAccountId()),
                () -> assertEquals("", studentDto.getFirstName()),
                () -> assertEquals("", studentDto.getLastName())
        );
    }

    @Test
    void testSetFirstNameWithNull() {
        studentDto = new StudentDto(5005, "Original", "Name");
        studentDto.setFirstName(null);
        assertNull(studentDto.getFirstName());
    }

    @Test
    void testSetLastNameWithNull() {
        studentDto = new StudentDto(6006, "Original", "Name");
        studentDto.setLastName(null);
        assertNull(studentDto.getLastName());
    }

    @Test
    void testCompleteStudentDto() {
        studentDto = new StudentDto(7007, "Alice", "Johnson");

        assertAll(
                () -> assertEquals(7007, studentDto.getUserAccountId()),
                () -> assertEquals("Alice", studentDto.getFirstName()),
                () -> assertEquals("Johnson", studentDto.getLastName())
        );
    }

    @Test
    void testUpdateAllFields() {
        studentDto = new StudentDto(8008, "Bob", "Brown");

        studentDto.setUserAccountId(9009);
        studentDto.setFirstName("Charlie");
        studentDto.setLastName("White");

        assertAll(
                () -> assertEquals(9009, studentDto.getUserAccountId()),
                () -> assertEquals("Charlie", studentDto.getFirstName()),
                () -> assertEquals("White", studentDto.getLastName())
        );
    }

    @Test
    void testConstructorWithSpecialCharacters() {
        studentDto = new StudentDto(10010, "José", "O'Brien");

        assertAll(
                () -> assertEquals(10010, studentDto.getUserAccountId()),
                () -> assertEquals("José", studentDto.getFirstName()),
                () -> assertEquals("O'Brien", studentDto.getLastName())
        );
    }
}