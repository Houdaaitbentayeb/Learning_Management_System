package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.StudentDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private NotificationsService notificationsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Users studentUser;
    private Users instructorUser;
    private Users adminUser;
    private Users anotherInstructorUser;
    private Student student;
    private Student anotherStudent;
    private Course course;
    private Enrollment enrollment;
    private Instructor instructor;
    private UsersType studentType;
    private UsersType instructorType;
    private UsersType adminType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        studentType = new UsersType();
        studentType.setUserTypeId(2);

        instructorType = new UsersType();
        instructorType.setUserTypeId(3);

        adminType = new UsersType();
        adminType.setUserTypeId(1);

        studentUser = new Users();
        studentUser.setUserId(1);
        studentUser.setUserTypeId(studentType);

        instructorUser = new Users();
        instructorUser.setUserId(10);
        instructorUser.setUserTypeId(instructorType);

        anotherInstructorUser = new Users();
        anotherInstructorUser.setUserId(20);
        anotherInstructorUser.setUserTypeId(instructorType);

        adminUser = new Users();
        adminUser.setUserId(100);
        adminUser.setUserTypeId(adminType);

        student = new Student();
        student.setUserAccountId(1);
        student.setFirstName("John");
        student.setLastName("Doe");

        anotherStudent = new Student();
        anotherStudent.setUserAccountId(2);
        anotherStudent.setFirstName("Jane");
        anotherStudent.setLastName("Smith");

        instructor = new Instructor();
        instructor.setUserAccountId(10);

        course = new Course();
        course.setCourseId(1);
        course.setCourseName("Java Basics");
        course.setInstructorId(instructor);

        enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
    }

    // ==================== enrollInCourse Tests ====================

    @Test
    void testEnrollInCourse_Success() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);

        enrollmentService.enrollInCourse(enrollment, request);

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
        verify(notificationsService, times(1)).sendNotification(anyString(), eq(10));
    }

    @Test
    void testEnrollInCourse_NoUserLoggedIn() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollment, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_StudentIdMismatch() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        Users differentStudent = new Users();
        differentStudent.setUserId(999);
        differentStudent.setUserTypeId(studentType);

        when(session.getAttribute("user")).thenReturn(differentStudent);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollment, request);
        });

        assertEquals("Student ID mismatch. Please provide the correct ID.", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_StudentNotFound() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollment, request);
        });

        assertEquals("No student found with the given ID.", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_CourseNotFound() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollment, request);
        });

        assertEquals("No course found with the given ID: 1", exception.getMessage());
    }

    @Test
    void testEnrollInCourse_AlreadyEnrolled() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.enrollInCourse(enrollment, request);
        });

        assertEquals("Student is already enrolled in this course.", exception.getMessage());
    }

    // ==================== viewEnrolledStudents Tests ====================

    @Test
    void testViewEnrolledStudents_InstructorSuccess() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student);
        enrollment1.setCourse(course);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(anotherStudent);
        enrollment2.setCourse(course);

        when(enrollmentRepository.findByCourse(course)).thenReturn(Arrays.asList(enrollment1, enrollment2));

        List<StudentDto> result = enrollmentService.viewEnrolledStudents(1, request);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    void testViewEnrolledStudents_AdminSuccess() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(Arrays.asList(enrollment));

        List<StudentDto> result = enrollmentService.viewEnrolledStudents(1, request);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testViewEnrolledStudents_NoUserLoggedIn() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.viewEnrolledStudents(1, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testViewEnrolledStudents_UserIsStudent() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.viewEnrolledStudents(1, request);
        });

        assertEquals("Logged-in user is not an Instructor or Admin.", exception.getMessage());
    }

    @Test
    void testViewEnrolledStudents_UserTypeNull() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        Users userWithNullType = new Users();
        userWithNullType.setUserId(1);
        userWithNullType.setUserTypeId(null);

        when(session.getAttribute("user")).thenReturn(userWithNullType);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.viewEnrolledStudents(1, request);
        });

        assertEquals("Logged-in user is not an Instructor or Admin.", exception.getMessage());
    }

    @Test
    void testViewEnrolledStudents_CourseNotFound() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.viewEnrolledStudents(1, request);
        });

        assertEquals("No course found with the given ID: 1", exception.getMessage());
    }

    @Test
    void testViewEnrolledStudents_InstructorNotAuthorized() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(anotherInstructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.viewEnrolledStudents(1, request);
        });

        assertEquals("You are not the Instructor of this course", exception.getMessage());
    }

    @Test
    void testViewEnrolledStudents_EmptyList() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(new ArrayList<>());

        List<StudentDto> result = enrollmentService.viewEnrolledStudents(1, request);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== removeEnrolledStudent Tests ====================

    @Test
    void testRemoveEnrolledStudent_Success() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        Enrollment enrollmentToRemove = new Enrollment();
        enrollmentToRemove.setEnrollmentId(100);
        enrollmentToRemove.setStudent(student);
        enrollmentToRemove.setCourse(course);

        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(enrollmentToRemove);

        enrollmentService.removeEnrolledStudent(1, 1, request);

        verify(enrollmentRepository, times(1)).deleteById(100);
    }

    @Test
    void testRemoveEnrolledStudent_NoUserLoggedIn() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.removeEnrolledStudent(1, 1, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testRemoveEnrolledStudent_UserNotInstructor() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.removeEnrolledStudent(1, 1, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testRemoveEnrolledStudent_CourseNotFound() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.removeEnrolledStudent(1, 1, request);
        });

        assertEquals("No course found with the given ID: 1", exception.getMessage());
    }

    @Test
    void testRemoveEnrolledStudent_StudentNotFound() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.removeEnrolledStudent(1, 1, request);
        });

        assertEquals("No student found with the given ID.", exception.getMessage());
    }

    @Test
    void testRemoveEnrolledStudent_StudentNotEnrolled() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.removeEnrolledStudent(1, 1, request);
        });

        assertEquals("This student is not enrolled in this course", exception.getMessage());
    }

    @Test
    void testRemoveEnrolledStudent_NotCourseInstructor() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(anotherInstructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.removeEnrolledStudent(1, 1, request);
        });

        assertEquals("You are not the Instructor of this course.", exception.getMessage());
    }

    @Test
    void testRemoveEnrolledStudent_InstructorIdNull() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(instructorUser);

        course.setInstructorId(null);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.removeEnrolledStudent(1, 1, request);
        });

        assertEquals("You are not the Instructor of this course.", exception.getMessage());
    }
}