package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.LessonDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LessonServiceTest {
    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private LessonAttendanceRepository lessonAttendanceRepository;

    private Users instructorUser;
    private Users studentUser;
    private Lesson lesson;
    private Course course;
    private UsersType instructorType;
    private UsersType studentType;
    private List<Lesson> lessons;
    private List<Enrollment> enrollments;
    private LessonAttendance lessonAttendance;
    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        instructorType = new UsersType();
        instructorType.setUserTypeId(3);

        studentType = new UsersType();
        studentType.setUserTypeId(2);

        instructorUser = new Users();
        instructorUser.setUserId(1);
        instructorUser.setUserTypeId(instructorType);

        studentUser = new Users();
        studentUser.setUserId(2);
        studentUser.setUserTypeId(studentType);

        course = new Course();
        course.setCourseId(1);
        course.setCourseName("Java Basics");
        course.setInstructorId(new Instructor());
        course.getInstructorId().setUserAccountId(1);

        lesson = new Lesson();
        lesson.setLessonId(1);
        lesson.setLessonName("Lesson 1");
        lesson.setCourseId(course);
        lesson.setLessonDescription("The first lesson");
        lesson.setOTP("12345");
        lesson.setLessonOrder(1);
        lesson.setContent("Lesson content");
        lesson.setCreationTime(new Date());

        lessons = new ArrayList<>();
        lessons.add(lesson);

        student = new Student();
        student.setUserAccountId(2);
        student.setUserId(studentUser);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollments = new ArrayList<>();
        enrollments.add(enrollment);

        lessonAttendance = new LessonAttendance();
        lessonAttendance.setLessonId(lesson);
        lessonAttendance.setStudentId(student);
    }

    // Tests for addLesson
    @Test
    void testAddLesson_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.addLesson(lesson, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testAddLesson_UserNotInstructor() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.addLesson(lesson, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testAddLesson_InvalidCourseId() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.addLesson(lesson, request);
        });

        assertEquals("No such CourseId", exception.getMessage());
    }

    @Test
    void testAddLesson_NotLessonInstructor() {
        Users otherInstructor = new Users();
        otherInstructor.setUserId(3);
        otherInstructor.setUserTypeId(instructorType);

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(otherInstructor);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.addLesson(lesson, request);
        });

        assertEquals("You are not the Instructor of this course", exception.getMessage());
    }

    @Test
    void testAddLesson_NullOTP() {
        lesson.setOTP(null);
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.addLesson(lesson, request);
        });

        assertEquals("OTP value cannot be null", exception.getMessage());
    }

    @Test
    void testAddLesson_EmptyOTP() {
        lesson.setOTP("");
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.addLesson(lesson, request);
        });

        assertEquals("OTP value cannot be null", exception.getMessage());
    }

    @Test
    void testAddLesson_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        lessonService.addLesson(lesson, request);

        verify(lessonRepository, times(1)).save(lesson);
        assertNotNull(lesson.getCreationTime());
    }

    // Tests for getLessonById
    @Test
    void testGetLessonById_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.getLessonById(1, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testGetLessonById_LessonNotFound() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(lessonRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.getLessonById(99, request);
        });

        assertTrue(exception.getMessage().contains("No such LessonId"));
    }

    @Test
    void testGetLessonById_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        LessonDto lessonDto = lessonService.getLessonById(1, request);

        assertNotNull(lessonDto);
        assertEquals("Lesson 1", lessonDto.getLessonName());
        assertEquals(1, lessonDto.getCourseId());
        assertEquals("The first lesson", lessonDto.getLessonDescription());
        verify(lessonRepository, times(1)).findById(1);
    }

    // Tests for getLessonsByCourseId
    @Test
    void testGetLessonsByCourseId_InvalidCourseId() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(courseRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.getLessonsByCourseId(99, request);
        });

        assertEquals("No such CourseId", exception.getMessage());
    }

    @Test
    void testGetLessonsByCourseId_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.getLessonsByCourseId(1, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testGetLessonsByCourseId_StudentNotEnrolled() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.getLessonsByCourseId(1, request);
        });

        assertEquals("You are not enrolled this course.", exception.getMessage());
    }

    @Test
    void testGetLessonsByCourseId_StudentSuccess() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);
        when(lessonRepository.findByCourseId(course)).thenReturn(lessons);

        List<LessonDto> lessonDtos = lessonService.getLessonsByCourseId(1, request);

        assertNotNull(lessonDtos);
        assertEquals(1, lessonDtos.size());
        assertEquals("Lesson 1", lessonDtos.get(0).getLessonName());
    }

    @Test
    void testGetLessonsByCourseId_InstructorSuccess() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(lessonRepository.findByCourseId(course)).thenReturn(lessons);

        List<LessonDto> lessonDtos = lessonService.getLessonsByCourseId(1, request);

        assertNotNull(lessonDtos);
        assertEquals(1, lessonDtos.size());
        verify(lessonRepository, times(1)).findByCourseId(course);
    }

    // Tests for updateLesson
    @Test
    void testUpdateLesson_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.updateLesson(1, lesson, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testUpdateLesson_UserNotInstructor() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.updateLesson(1, lesson, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testUpdateLesson_InvalidCourseId() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.updateLesson(1, lesson, request);
        });

        assertEquals("No such CourseId", exception.getMessage());
    }

    @Test
    void testUpdateLesson_NotCourseInstructor() {
        Users otherInstructor = new Users();
        otherInstructor.setUserId(3);
        otherInstructor.setUserTypeId(instructorType);

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(otherInstructor);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.updateLesson(1, lesson, request);
        });

        assertEquals("You are not the Instructor of this course", exception.getMessage());
    }

    @Test
    void testUpdateLesson_LessonNotFound() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(lessonRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.updateLesson(99, lesson, request);
        });

        assertTrue(exception.getMessage().contains("Lesson not found"));
    }

    @Test
    void testUpdateLesson_Success() {
        Lesson updatedLesson = new Lesson();
        updatedLesson.setLessonName("Updated Lesson");
        updatedLesson.setLessonDescription("Updated description");
        updatedLesson.setLessonOrder(2);
        updatedLesson.setContent("Updated content");
        updatedLesson.setOTP("54321");
        updatedLesson.setCourseId(course);

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        lessonService.updateLesson(1, updatedLesson, request);

        assertEquals("Updated Lesson", lesson.getLessonName());
        assertEquals("Updated description", lesson.getLessonDescription());
        verify(lessonRepository, times(1)).save(lesson);
    }

    // Tests for deleteLesson
    @Test
    void testDeleteLesson_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.deleteLesson(1, 1, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testDeleteLesson_UserNotInstructor() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.deleteLesson(1, 1, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testDeleteLesson_InvalidCourseId() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.deleteLesson(1, 99, request);
        });

        assertTrue(exception.getMessage().contains("No course found"));
    }

    @Test
    void testDeleteLesson_NotCourseInstructor() {
        Users otherInstructor = new Users();
        otherInstructor.setUserId(3);
        otherInstructor.setUserTypeId(instructorType);

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(otherInstructor);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.deleteLesson(1, 1, request);
        });

        assertEquals("You are not authorized to show or delete or update this course.", exception.getMessage());
    }

    @Test
    void testDeleteLesson_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        lessonService.deleteLesson(1, 1, request);

        verify(lessonRepository, times(1)).deleteById(1);
    }

    // Tests for StudentEnterLesson
    @Test
    void testStudentEnterLesson_UserNotLoggedIn() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.StudentEnterLesson(1, 1, "12345", request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testStudentEnterLesson_InvalidCourseId() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.StudentEnterLesson(99, 1, "12345", request);
        });

        assertTrue(exception.getMessage().contains("No course found"));
    }

    @Test
    void testStudentEnterLesson_NotEnrolled() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.StudentEnterLesson(1, 1, "12345", request);
        });

        assertEquals("You are not enrolled to this course.", exception.getMessage());
    }

    @Test
    void testStudentEnterLesson_InvalidLessonId() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(enrollments);
        when(lessonRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.StudentEnterLesson(1, 99, "12345", request);
        });

        assertTrue(exception.getMessage().contains("Lesson not found"));
    }

    @Test
    void testStudentEnterLesson_InvalidOTP() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(enrollments);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.StudentEnterLesson(1, 1, "wrongOTP", request);
        });

        assertEquals("OTP does not match.", exception.getMessage());
    }

    @Test
    void testStudentEnterLesson_AlreadyEntered() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(enrollments);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.existsByLessonIdAndStudentId(any(), any())).thenReturn(true);

        lessonService.StudentEnterLesson(1, 1, "12345", request);

        verify(lessonAttendanceRepository, never()).save(any(LessonAttendance.class));
    }

    @Test
    void testStudentEnterLesson_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(enrollments);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.existsByLessonIdAndStudentId(any(), any())).thenReturn(false);

        lessonService.StudentEnterLesson(1, 1, "12345", request);

        verify(lessonAttendanceRepository, times(1)).save(any(LessonAttendance.class));
    }

    // Tests for lessonAttendance
    @Test
    void testLessonAttendance_lessonNotFound() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(lessonRepository.existsById(99)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.lessonAttendance(99, request);
        });

        assertEquals("Lesson with ID 99 not found.", exception.getMessage());
    }

    @Test
    void testLessonAttendance_noLoggedInUser() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.lessonAttendance(1, request);
        });

        assertEquals("No logged in user is found.", exception.getMessage());
    }

    @Test
    void testLessonAttendance_notInstructor() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(studentUser);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.lessonAttendance(1, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testLessonAttendance_notLessonInstructor() {
        Users inValidInstructorUser = new Users();
        inValidInstructorUser.setUserId(3);
        inValidInstructorUser.setUserTypeId(instructorType);

        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(inValidInstructorUser);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lessonService.lessonAttendance(1, request);
        });

        assertEquals("Logged-in instructor does not have access for this lesson attendances.", exception.getMessage());
    }

    @Test
    void testLessonAttendance_Success() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.findAllByLessonId(lesson)).thenReturn(List.of(lessonAttendance));

        List<String> attendances = lessonService.lessonAttendance(1, request);

        assertEquals(1, attendances.size());
        assertEquals("2", attendances.get(0));
    }

    @Test
    void testLessonAttendance_EmptyList() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(instructorUser);
        when(lessonRepository.existsById(1)).thenReturn(true);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.findAllByLessonId(lesson)).thenReturn(new ArrayList<>());

        List<String> attendances = lessonService.lessonAttendance(1, request);

        assertTrue(attendances.isEmpty());
    }
}
