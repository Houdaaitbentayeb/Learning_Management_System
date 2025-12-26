package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.AssignmentDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SessionAuthService sessionAuthService;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             SubmissionRepository submissionRepository,
                             CourseRepository courseRepository,
                             StudentRepository studentRepository,
                             EnrollmentRepository enrollmentRepository,
                             SessionAuthService sessionAuthService) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.sessionAuthService = sessionAuthService;
    }

    // -------------------- Public methods --------------------

    public void uploadAssignment(AssignmentDto assignmentDto, HttpServletRequest request) {
        Users loggedInUser = requireLoggedInForStudentActions(request);

        Course course = courseRepository.findById(assignmentDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Student student = studentRepository.findById(loggedInUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("You're not a student"));

        ensureEnrolled(student, course);

        ensureNotAlreadySubmitted(student, assignmentDto.getAssignmentId());

        Assignment assignment = new Assignment();
        assignment.setAssignmentId(assignmentDto.getAssignmentId());
        assignment.setDescription(assignmentDto.getAssignmentDescription());
        assignment.setCourseID(course);
        assignment.setDueDate(new Date());
        assignment.setTitle(assignmentDto.getAssignmentTitle());

        Submission submission = new Submission();
        submission.setAssignmentId(assignment);
        submission.setStudentId(student);

        submissionRepository.save(submission);
    }

    public void gradeAssignment(int studentID, int assigID, float grade, HttpServletRequest request) {
        Users instructor = requireLoggedInForInstructorActions(request);

        Assignment assignment = assignmentRepository.findById(assigID)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        ensureInstructorOwnsCourse(instructor, assignment.getCourseID());

        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Submission submission = findSubmissionOrThrow(student, assignment);
        submission.setGrade(grade);
        submissionRepository.save(submission);
    }

    public void saveAssignmentFeedback(int studentID, int assigID, String feedback, HttpServletRequest request) {
        Users instructor = requireLoggedInForInstructorActions(request);

        Assignment assignment = assignmentRepository.findById(assigID)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        ensureInstructorOwnsCourse(instructor, assignment.getCourseID());

        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Submission submission = findSubmissionOrThrow(student, assignment);
        submission.setFeedback(feedback);
        submissionRepository.save(submission);
    }

    public String getFeedback(int assigID, HttpServletRequest request) {
        Users loggedInUser = requireLoggedInForStudentActions(request);

        Assignment assignment = assignmentRepository.findById(assigID)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        Student student = studentRepository.findById(loggedInUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("You're not a student"));

        ensureEnrolled(student, assignment.getCourseID());

        Submission submission = findSubmissionOrThrow(student, assignment);

        return (submission.getFeedback() == null) ? "There is no feedback yet" : submission.getFeedback();
    }

    public List<String> assignmentSubmissions(int assignmentId, HttpServletRequest request) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment with ID " + assignmentId + " not found."));

        Users instructor = sessionAuthService.requireUser(request);
        if (instructor == null) {
            throw new IllegalArgumentException("No logged in user is found.");
        }
        if (instructor.getUserTypeId() == null || instructor.getUserTypeId().getUserTypeId() != 3) {
            throw new IllegalArgumentException("Logged-in user is not an instructor.");
        }
        ensureInstructorOwnsCourse(instructor, assignment.getCourseID(),
                "Logged-in instructor does not have access for this assignment submissions.");

        List<Submission> assignmentSubmissions = submissionRepository.findAllByAssignmentId(assignment);

        List<String> submissions = new ArrayList<>();
        for (Submission submission : assignmentSubmissions) {
            Student student = submission.getStudentId();
            submissions.add(student.getUserAccountId() + ": " + submission.getGrade());
        }
        return submissions;
    }

    public void addAssignment(AssignmentDto assignmentDto, HttpServletRequest request) {
        Users instructor = sessionAuthService.requireUser(request);
        if (instructor == null) {
            throw new IllegalArgumentException("No user is logged in.");
        }
        if (instructor.getUserTypeId() == null || instructor.getUserTypeId().getUserTypeId() != 3) {
            throw new IllegalArgumentException("Logged-in user is not an instructor.");
        }

        Course course = courseRepository.findById(assignmentDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("No such CourseId"));

        if (instructor.getUserId() != course.getInstructorId().getUserAccountId()) {
            throw new IllegalArgumentException("You are not the Instructor of this course");
        }

        boolean exist = assignmentRepository.existsById(assignmentDto.getAssignmentId());
        if (exist) {
            throw new IllegalArgumentException("Assignment already exists");
        }

        Assignment assignment = new Assignment();
        assignment.setDescription(assignmentDto.getAssignmentDescription());
        assignment.setTitle(assignmentDto.getAssignmentTitle());
        assignment.setDueDate(new Date());
        assignment.setCourseID(course);

        assignmentRepository.save(assignment);
    }

    // -------------------- Private helpers (reduce duplication) --------------------

    private Users requireLoggedInUser(HttpServletRequest request, String errorMessage) {
        Users user = sessionAuthService.requireUser(request);
        if (user == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        return user;
    }

    private Users requireLoggedInForStudentActions(HttpServletRequest request) {
        return requireLoggedInUser(request, "Student must be logged in");
    }

    private Users requireLoggedInForInstructorActions(HttpServletRequest request) {
        return requireLoggedInUser(request, "Instructor must be logged in");
    }


    private void ensureEnrolled(Student student, Course course) {
        Boolean isExist = enrollmentRepository.existsByStudentAndCourse(student, course);
        if (!isExist) {
            throw new IllegalArgumentException("You're not enrolled in this course");
        }
    }

    private void ensureNotAlreadySubmitted(Student student, int assignmentId) {
        List<Submission> submissions = submissionRepository.findByStudentId(student);
        for (Submission s : submissions) {
            if (s.getAssignmentId().getAssignmentId() == assignmentId) {
                throw new IllegalArgumentException("You've already submitted this assignment");
            }
        }
    }

    private Submission findSubmissionOrThrow(Student student, Assignment assignment) {
        List<Submission> submissions = submissionRepository.findByStudentId(student);
        if (submissions.isEmpty()) {
            throw new IllegalArgumentException("Student has no submissions");
        }
        for (Submission s : submissions) {
            if (s.getAssignmentId().getAssignmentId() == assignment.getAssignmentId()) {
                return s;
            }
        }
        throw new IllegalArgumentException("Student didn't submit this assignment");
    }

    private void ensureInstructorOwnsCourse(Users instructor, Course course) {
        ensureInstructorOwnsCourse(instructor, course, "You're not the instructor of this course");
    }

    private void ensureInstructorOwnsCourse(Users instructor, Course course, String errorMessage) {
        int instructorId = course.getInstructorId().getUserAccountId();
        if (instructor.getUserId() != instructorId) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
