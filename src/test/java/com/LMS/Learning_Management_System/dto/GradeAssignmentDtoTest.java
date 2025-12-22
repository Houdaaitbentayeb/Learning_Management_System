package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class GradeAssignmentDtoTest {

    private GradeAssignmentDto gradeAssignmentDto;

    @BeforeEach
    void setUp() {
        gradeAssignmentDto = new GradeAssignmentDto();
    }

    @Test
    void testGetAndSetStudentId() {
        int expectedStudentId = 123;
        gradeAssignmentDto.setStudentId(expectedStudentId);
        assertEquals(expectedStudentId, gradeAssignmentDto.getStudentId());
    }

    @Test
    void testGetAndSetAssignmentId() {
        int expectedAssignmentId = 456;
        gradeAssignmentDto.setAssignmentId(expectedAssignmentId);
        assertEquals(expectedAssignmentId, gradeAssignmentDto.getAssignmentId());
    }

    @Test
    void testGetAndSetGrade() {
        float expectedGrade = 85.5f;
        gradeAssignmentDto.setGrade(expectedGrade);
        assertEquals(expectedGrade, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testGradeAssignmentDtoWithAllFields() {
        gradeAssignmentDto.setStudentId(100);
        gradeAssignmentDto.setAssignmentId(200);
        gradeAssignmentDto.setGrade(95.75f);

        assertEquals(100, gradeAssignmentDto.getStudentId());
        assertEquals(200, gradeAssignmentDto.getAssignmentId());
        assertEquals(95.75f, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testGradeWithZeroValue() {
        gradeAssignmentDto.setGrade(0.0f);
        assertEquals(0.0f, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testGradeWithMaxValue() {
        gradeAssignmentDto.setGrade(100.0f);
        assertEquals(100.0f, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testGradeWithDecimalValue() {
        gradeAssignmentDto.setGrade(87.35f);
        assertEquals(87.35f, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testGradeWithNegativeValue() {
        gradeAssignmentDto.setGrade(-10.0f);
        assertEquals(-10.0f, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testStudentIdWithZeroValue() {
        gradeAssignmentDto.setStudentId(0);
        assertEquals(0, gradeAssignmentDto.getStudentId());
    }

    @Test
    void testAssignmentIdWithNegativeValue() {
        gradeAssignmentDto.setAssignmentId(-1);
        assertEquals(-1, gradeAssignmentDto.getAssignmentId());
    }

    @Test
    void testMultipleGradeUpdates() {
        gradeAssignmentDto.setGrade(75.0f);
        assertEquals(75.0f, gradeAssignmentDto.getGrade(), 0.001);
        
        gradeAssignmentDto.setGrade(80.5f);
        assertEquals(80.5f, gradeAssignmentDto.getGrade(), 0.001);
        
        gradeAssignmentDto.setGrade(90.0f);
        assertEquals(90.0f, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testDefaultValues() {
        GradeAssignmentDto newDto = new GradeAssignmentDto();
        assertEquals(0, newDto.getStudentId());
        assertEquals(0, newDto.getAssignmentId());
        assertEquals(0.0f, newDto.getGrade(), 0.001);
    }

    @Test
    void testGradeWithVerySmallDecimal() {
        gradeAssignmentDto.setGrade(0.01f);
        assertEquals(0.01f, gradeAssignmentDto.getGrade(), 0.001);
    }

    @Test
    void testStudentIdWithLargeValue() {
        int largeId = 999999;
        gradeAssignmentDto.setStudentId(largeId);
        assertEquals(largeId, gradeAssignmentDto.getStudentId());
    }

    @Test
    void testAssignmentIdWithLargeValue() {
        int largeId = 888888;
        gradeAssignmentDto.setAssignmentId(largeId);
        assertEquals(largeId, gradeAssignmentDto.getAssignmentId());
    }
}