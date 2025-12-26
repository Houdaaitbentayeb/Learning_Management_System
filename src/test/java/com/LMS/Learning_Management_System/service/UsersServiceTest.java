package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import com.LMS.Learning_Management_System.util.UserSignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UsersServiceTest {

    @Mock private UsersRepository usersRepository;
    @Mock private UsersTypeRepository usersTypeRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private AdminRepository adminRepository;
    @Mock private InstructorRepository instructorRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private HttpServletRequest request;
    @Mock
    private SessionAuthService sessionAuthService;


    @InjectMocks
    private UsersService usersService;

    public UsersServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_success_student() {
        Users admin = new Users();
        UsersType adminType = new UsersType();
        adminType.setUserTypeId(1);
        admin.setUserTypeId(adminType);

        UsersType studentType = new UsersType();
        studentType.setUserTypeId(2);

        UserSignUpRequest req = new UserSignUpRequest();
        req.setEmail("a@test.com");
        req.setPassword("pwd");
        req.setUserTypeId(2);

        when(sessionAuthService.requireUser(request)).thenReturn(admin);

        when(usersRepository.findByEmail(req.getEmail())).thenReturn(null);
        when(usersTypeRepository.findById(2)).thenReturn(Optional.of(studentType));
        when(passwordEncoder.encode("pwd")).thenReturn("enc");

        usersService.save(req, request);

        verify(studentRepository).save(any(Student.class));
    }
}
