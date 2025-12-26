package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentDtoTest {

    private AssignmentDto assignmentDto;

    @BeforeEach
    void setUp() {
        assignmentDto = new AssignmentDto();
    }

    @Test
    void testGetAndSetAssignmentId() {
        int expectedId = 1;
        assignmentDto.setAssignmentId(expectedId);
        assertEquals(expectedId, assignmentDto.getAssignmentId());
    }

    @Test
    void testGetAndSetAssignmentTitle() {
        String expectedTitle = "Java Programming Assignment";
        assignmentDto.setAssignmentTitle(expectedTitle);
        assertEquals(expectedTitle, assignmentDto.getAssignmentTitle());
    }

    @Test
    void testGetAndSetAssignmentDescription() {
        String expectedDescription = "Complete the Java exercises";
        assignmentDto.setAssignmentDescription(expectedDescription);
        assertEquals(expectedDescription, assignmentDto.getAssignmentDescription());
    }

    @Test
    void testGetAndSetCourseId() {
        int expectedCourseId = 101;
        assignmentDto.setCourseId(expectedCourseId);
        assertEquals(expectedCourseId, assignmentDto.getCourseId());
    }

    @Test
    void testAssignmentDtoWithAllFields() {
        assignmentDto.setAssignmentId(5);
        assignmentDto.setAssignmentTitle("Final Project");
        assignmentDto.setAssignmentDescription("Build a complete LMS system");
        assignmentDto.setCourseId(202);

        assertEquals(5, assignmentDto.getAssignmentId());
        assertEquals("Final Project", assignmentDto.getAssignmentTitle());
        assertEquals("Build a complete LMS system", assignmentDto.getAssignmentDescription());
        assertEquals(202, assignmentDto.getCourseId());
    }

    @Test
    void testAssignmentDtoWithNullValues() {
        assignmentDto.setAssignmentTitle(null);
        assignmentDto.setAssignmentDescription(null);

        assertNull(assignmentDto.getAssignmentTitle());
        assertNull(assignmentDto.getAssignmentDescription());
    }

    @Test
    void testAssignmentDtoWithEmptyStrings() {
        assignmentDto.setAssignmentTitle("");
        assignmentDto.setAssignmentDescription("");

        assertEquals("", assignmentDto.getAssignmentTitle());
        assertEquals("", assignmentDto.getAssignmentDescription());
    }

    @Test
    void testAssignmentIdWithNegativeValue() {
        assignmentDto.setAssignmentId(-1);
        assertEquals(-1, assignmentDto.getAssignmentId());
    }

    @Test
    void testCourseIdWithZeroValue() {
        assignmentDto.setCourseId(0);
        assertEquals(0, assignmentDto.getCourseId());
    }
}