package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.CourseDto;
import com.LMS.Learning_Management_System.dto.StudentDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private NotificationsService notificationsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private SessionAuthService sessionAuthService;

    @InjectMocks
    private CourseService courseService;

    private Users instructorUser;
    private Users studentUser;
    private Users anotherInstructorUser;
    private Instructor instructor;
    private Course course;
    private UsersType instructorType;
    private UsersType studentType;
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

        anotherInstructorUser = new Users();
        anotherInstructorUser.setUserId(2);
        anotherInstructorUser.setUserTypeId(instructorType);

        studentUser = new Users();
        studentUser.setUserId(3);
        studentUser.setUserTypeId(studentType);

        instructor = new Instructor();
        instructor.setUserAccountId(1);
        instructor.setFirstName("John");

        course = new Course();
        course.setCourseId(1);
        course.setCourseName("Java Basics");
        course.setDescription("Learn Java");
        course.setDuration(40);
        course.setInstructorId(instructor);

        student = new Student();
        student.setUserAccountId(3);
    }

    // ==================== addCourse Tests ====================

    @Test
    void testAddCourse_Success() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(null);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(instructor));

        courseService.addCourse(course, request, 1);

        verify(courseRepository, times(1)).save(course);
        assertNotNull(course.getCreationDate());
    }

    @Test
    void testAddCourse_UserNotLoggedIn() {
        when(sessionAuthService.requireUser(request)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testAddCourse_UserNotInstructor() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testAddCourse_UserTypeIdNull() {

        Users userWithNullType = new Users();
        userWithNullType.setUserId(1);
        userWithNullType.setUserTypeId(null);

        when(sessionAuthService.requireUser(request)).thenReturn(userWithNullType);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testAddCourse_DifferentInstructorId() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 2);
        });

        assertEquals("Logged-in user is an another instructor.", exception.getMessage());
    }

    @Test
    void testAddCourse_DuplicateCourseName() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(course);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("This CourseName already exist", exception.getMessage());
    }

    @Test
    void testAddCourse_InstructorIdNull() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        course.setInstructorId(null);
        when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("InstructorId cannot be null", exception.getMessage());
    }

    @Test
    void testAddCourse_InstructorIdZero() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        Instructor zeroInstructor = new Instructor();
        zeroInstructor.setUserAccountId(0);
        course.setInstructorId(zeroInstructor);

        when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("InstructorId cannot be null", exception.getMessage());
    }

    @Test
    void testAddCourse_InstructorNotFound() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(null);
        when(instructorRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.addCourse(course, request, 1);
        });

        assertEquals("No such Instructor", exception.getMessage());
    }

    // ==================== getAllCourses Tests ====================

    @Test
    void testGetAllCourses_Success() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        List<Course> courses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(courses);

        List<CourseDto> result = courseService.getAllCourses(request);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java Basics", result.get(0).getCourseName());
    }

    @Test
    void testGetAllCourses_NoUserLoggedIn() {
        when(sessionAuthService.requireUser(request)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.getAllCourses(request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testGetAllCourses_EmptyList() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findAll()).thenReturn(new ArrayList<>());

        List<CourseDto> result = courseService.getAllCourses(request);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== getCourseById Tests ====================

    @Test
    void testGetCourseById_InstructorSuccess() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        CourseDto courseDto = courseService.getCourseById(1, request);

        assertNotNull(courseDto);
        assertEquals("Java Basics", courseDto.getCourseName());
        verify(courseRepository, times(1)).findById(1);
    }

    @Test
    void testGetCourseById_StudentEnrolledSuccess() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(Arrays.asList(enrollment));

        CourseDto courseDto = courseService.getCourseById(1, request);

        assertNotNull(courseDto);
        assertEquals("Java Basics", courseDto.getCourseName());
    }

    @Test
    void testGetCourseById_StudentNotEnrolled() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourse(course)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.getCourseById(1, request);
        });

        assertEquals("You are not enrolled to this course.", exception.getMessage());
    }

    @Test
    void testGetCourseById_NoUserLoggedIn() {
        when(sessionAuthService.requireUser(request)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.getCourseById(1, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testGetCourseById_CourseNotFound() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.getCourseById(1, request);
        });

        assertEquals("No course found with the given ID: 1", exception.getMessage());
    }

    // ==================== updateCourse Tests ====================

    @Test
    void testUpdateCourse_Success() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Course updatedCourse = new Course();
        updatedCourse.setCourseName("Advanced Java");
        updatedCourse.setDescription("Learn Advanced Java");
        updatedCourse.setDuration(60);

        courseService.updateCourse(1, updatedCourse, request);

        verify(courseRepository, times(1)).save(course);
        assertEquals("Advanced Java", course.getCourseName());
        assertEquals("Learn Advanced Java", course.getDescription());
        assertEquals(60, course.getDuration());
    }

    @Test
    void testUpdateCourse_NoUserLoggedIn() {
        when(sessionAuthService.requireUser(request)).thenReturn(null);

        Course updatedCourse = new Course();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.updateCourse(1, updatedCourse, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testUpdateCourse_UserNotInstructor() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        Course updatedCourse = new Course();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.updateCourse(1, updatedCourse, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testUpdateCourse_CourseNotFound() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Course updatedCourse = new Course();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.updateCourse(1, updatedCourse, request);
        });

        assertEquals("No course found with the given ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateCourse_NotAuthorized() {
        when(sessionAuthService.requireUser(request)).thenReturn(anotherInstructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Course updatedCourse = new Course();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.updateCourse(1, updatedCourse, request);
        });

        assertEquals("You are not authorized to update or delete this course.", exception.getMessage());
    }

    @Test
    void testUpdateCourse_InstructorIdNull() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        course.setInstructorId(null);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Course updatedCourse = new Course();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.updateCourse(1, updatedCourse, request);
        });

        assertEquals("You are not authorized to update or delete this course.", exception.getMessage());
    }

    // ==================== deleteCourse Tests ====================

    @Test
    void testDeleteCourse_Success() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1, request);

        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    void testDeleteCourse_NoUserLoggedIn() {
        when(sessionAuthService.requireUser(request)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.deleteCourse(1, request);
        });

        assertEquals("No user is logged in.", exception.getMessage());
    }

    @Test
    void testDeleteCourse_NotAuthorized() {
        when(sessionAuthService.requireUser(request)).thenReturn(anotherInstructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.deleteCourse(1, request);
        });

        assertEquals("You are not authorized to update or delete this course.", exception.getMessage());
    }

    // ==================== uploadMediaFile Tests ====================

    @Test
    void testUploadMediaFile_Success() throws Exception {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("example.mp4");
        doNothing().when(mockFile).transferTo(any(File.class));

        courseService.uploadMediaFile(1, mockFile, request);

        verify(mockFile, times(1)).transferTo(any(File.class));
        verify(courseRepository, times(1)).save(course);
        assertNotNull(course.getMedia());
        assertTrue(course.getMedia().contains("example.mp4"));
    }

    @Test
    void testUploadMediaFile_IOExceptionThrown() throws Exception {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("example.mp4");
        doThrow(new IOException("File error")).when(mockFile).transferTo(any(File.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseService.uploadMediaFile(1, mockFile, request);
        });

        assertTrue(exception.getMessage().contains("File upload failed"));
    }

    @Test
    void testUploadMediaFile_NotAuthorized() {
        when(sessionAuthService.requireUser(request)).thenReturn(anotherInstructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        MultipartFile mockFile = mock(MultipartFile.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.uploadMediaFile(1, mockFile, request);
        });

        assertEquals("You are not authorized to update or delete this course.", exception.getMessage());
    }

    // ==================== sendNotificationsToEnrolledStudents Tests ====================

    @Test
    void testSendNotificationsToEnrolledStudents_Success() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        StudentDto studentDto = new StudentDto(3, "Alice", "Smith");
        List<StudentDto> students = Arrays.asList(studentDto);

        when(enrollmentService.viewEnrolledStudents(1, request)).thenReturn(students);

        courseService.sendNotificationsToEnrolledStudents(1, request);

        verify(notificationsService, times(1)).sendNotification(anyString(), eq(3));
    }

    @Test
    void testSendNotificationsToEnrolledStudents_NoStudents() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentService.viewEnrolledStudents(1, request)).thenReturn(new ArrayList<>());

        courseService.sendNotificationsToEnrolledStudents(1, request);

        verify(notificationsService, never()).sendNotification(anyString(), anyInt());
    }
}