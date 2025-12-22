package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.entity.UsersType;
import com.LMS.Learning_Management_System.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    public CustomUserDetailsServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_success() {
        UsersType type = new UsersType();
        type.setUserTypeName("ADMIN");

        Users user = new Users();
        user.setEmail("test@test.com");
        user.setPassword("pwd");
        user.setUserTypeId(type);

        when(usersRepository.findByEmail("test@test.com")).thenReturn(user);

        UserDetails details = service.loadUserByUsername("test@test.com");

        assertEquals("test@test.com", details.getUsername());
        assertEquals("pwd", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_userNotFound() {
        when(usersRepository.findByEmail("x@test.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("x@test.com"));
    }
}
