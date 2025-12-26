package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Student;
import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    @Mock private HttpServletRequest request;
    @Mock
    private SessionAuthService sessionAuthService;


    @InjectMocks
    private StudentService studentService;

    public StudentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_success() {
        Users user = new Users();
        user.setUserId(1);

        Student student = new Student();
        student.setFirstName("A");

        Student dbStudent = new Student();

        when(sessionAuthService.requireUser(request)).thenReturn(user);
        when(studentRepository.getReferenceById(1)).thenReturn(dbStudent);

        studentService.save(1, student, request);

        verify(studentRepository).save(dbStudent);
    }
}
