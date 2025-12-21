package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Submission Entity Tests")
class SubmissionTest {

    @Test
    @DisplayName("Test constructeur par défaut")
    void testDefaultConstructor() {
        Submission submission = new Submission();

        assertNotNull(submission);
        assertEquals(0, submission.getSubmissionId());
        assertNull(submission.getAssignmentId());
        assertNull(submission.getStudentId());
        assertNull(submission.getFilePath());
        assertNull(submission.getGrade());
        assertNull(submission.getFeedback());
        assertNull(submission.getSubmittedAt());
    }

    @Test
    @DisplayName("Test constructeur complet")
    void testFullConstructor() {
        Assignment assignment = new Assignment();
        Student student = new Student();
        Date date = new Date();

        Submission submission = new Submission(
                1,
                assignment,
                student,
                "/files/test.pdf",
                15.5f,
                "Good work",
                date
        );

        assertEquals(1, submission.getSubmissionId());
        assertEquals(assignment, submission.getAssignmentId());
        assertEquals(student, submission.getStudentId());
        assertEquals("/files/test.pdf", submission.getFilePath());
        assertEquals(15.5f, submission.getGrade());
        assertEquals("Good work", submission.getFeedback());
        assertEquals(date, submission.getSubmittedAt());
    }

    @Test
    @DisplayName("Test setters et getters")
    void testSettersAndGetters() {
        Submission submission = new Submission();
        Assignment assignment = new Assignment();
        Student student = new Student();
        Date date = new Date();

        submission.setSubmissionId(2);
        submission.setAssignmentId(assignment);
        submission.setStudentId(student);
        submission.setFilePath("path/file.docx");
        submission.setGrade(18f);
        submission.setFeedback("Excellent");
        submission.setSubmittedAt(date);

        assertEquals(2, submission.getSubmissionId());
        assertEquals(assignment, submission.getAssignmentId());
        assertEquals(student, submission.getStudentId());
        assertEquals("path/file.docx", submission.getFilePath());
        assertEquals(18f, submission.getGrade());
        assertEquals("Excellent", submission.getFeedback());
        assertEquals(date, submission.getSubmittedAt());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        Submission submission = new Submission();
        submission.setSubmissionId(1);

        String result = submission.toString();

        assertNotNull(result);
        assertTrue(result.contains("Submission{"));
        assertTrue(result.contains("submissionId=1"));
    }

    @Test
    @DisplayName("Test grade null accepté")
    void testNullGrade() {
        Submission submission = new Submission();
        submission.setGrade(null);

        assertNull(submission.getGrade());
    }
}
