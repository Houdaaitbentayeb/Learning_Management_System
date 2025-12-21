package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Users Entity Tests")
class UsersTest {

    @Test
    @DisplayName("Test constructeur par défaut")
    void testDefaultConstructor() {
        Users users = new Users();

        assertNotNull(users);
        assertEquals(0, users.getUserId());
        assertNull(users.getEmail());
        assertNull(users.getPassword());
        assertNull(users.getRegistrationDate());
        assertNull(users.getUserTypeId());
    }

    @Test
    @DisplayName("Test constructeur complet")
    void testFullConstructor() {
        UsersType usersType = new UsersType();
        Date date = new Date();

        Users users = new Users(1, "test@test.com", "pass123", date, usersType);

        assertEquals(1, users.getUserId());
        assertEquals("test@test.com", users.getEmail());
        assertEquals("pass123", users.getPassword());
        assertEquals(date, users.getRegistrationDate());
        assertEquals(usersType, users.getUserTypeId());
    }

    @Test
    @DisplayName("Test constructeur email/password")
    void testEmailPasswordConstructor() {
        UsersType usersType = new UsersType();

        Users users = new Users("user@test.com", "secret", usersType);

        assertEquals("user@test.com", users.getEmail());
        assertEquals("secret", users.getPassword());
        assertEquals(usersType, users.getUserTypeId());
    }

    @Test
    @DisplayName("Test setters et getters")
    void testSettersAndGetters() {
        Users users = new Users();
        UsersType usersType = new UsersType();
        Date date = new Date();

        users.setUserId(5);
        users.setEmail("email@test.com");
        users.setPassword("password");
        users.setRegistrationDate(date);
        users.setUserTypeId(usersType);

        assertEquals(5, users.getUserId());
        assertEquals("email@test.com", users.getEmail());
        assertEquals("password", users.getPassword());
        assertEquals(date, users.getRegistrationDate());
        assertEquals(usersType, users.getUserTypeId());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        Users users = new Users();
        users.setUserId(1);
        users.setEmail("user@test.com");
        users.setPassword("pass");

        String result = users.toString();

        assertNotNull(result);
        assertTrue(result.contains("Users{"));
        assertTrue(result.contains("userId=1"));
        assertTrue(result.contains("email='user@test.com'"));
    }

    @Test
    @DisplayName("Test email null accepté")
    void testNullEmail() {
        Users users = new Users();
        users.setEmail(null);

        assertNull(users.getEmail());
    }
}
