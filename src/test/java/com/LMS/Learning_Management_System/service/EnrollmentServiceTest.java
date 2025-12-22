package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private NotificationsService notificationsService;
    @Mock private HttpServletRequest request;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Users studentUser;
    private Student student;
    private Course course;
    private Enrollment enrollment;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        UsersType studentType = new UsersType();
        studentType.setUserTypeId(2);

        studentUser = new Users();
        studentUser.setUserId(1);
        studentUser.setUserTypeId(studentType);

        student = new Student();
        student.setUserAccountId(1);

        Instructor instructor = new Instructor();
        instructor.setUserAccountId(10);

        course = new Course();
        course.setCourseId(1);
        course.setInstructorId(instructor);

        enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
    }

    @Test
    void enrollInCourse_success() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);

        enrollmentService.enrollInCourse(enrollment, request);

        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(notificationsService).sendNotification(anyString(), anyInt());
    }

    @Test
    void enrollInCourse_alreadyEnrolled() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(studentUser);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> enrollmentService.enrollInCourse(enrollment, request));

        assertEquals("Student is already enrolled in this course.", ex.getMessage());
    }
}
