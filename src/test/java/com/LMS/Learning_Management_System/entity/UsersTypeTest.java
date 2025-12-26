package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UsersType Entity Tests")
class UsersTypeTest {

    @Test
    @DisplayName("Test constructeur par défaut")
    void testDefaultConstructor() {
        UsersType usersType = new UsersType();

        assertNotNull(usersType);
        assertEquals(0, usersType.getUserTypeId());
        assertNull(usersType.getUserTypeName());
        assertNull(usersType.getUsers());
    }

    @Test
    @DisplayName("Test constructeur complet")
    void testFullConstructor() {
        List<Users> usersList = new ArrayList<>();
        UsersType usersType = new UsersType(1, "ADMIN", usersList);

        assertEquals(1, usersType.getUserTypeId());
        assertEquals("ADMIN", usersType.getUserTypeName());
        assertEquals(usersList, usersType.getUsers());
    }

    @Test
    @DisplayName("Test getters et setters")
    void testSettersAndGetters() {
        UsersType usersType = new UsersType();

        usersType.setUserTypeId(2);
        usersType.setUserTypeName("STUDENT");

        List<Users> usersList = new ArrayList<>();
        usersType.setUsers(usersList);

        assertEquals(2, usersType.getUserTypeId());
        assertEquals("STUDENT", usersType.getUserTypeName());
        assertEquals(usersList, usersType.getUsers());
    }

    @Test
    @DisplayName("Test getAuthority")
    void testGetAuthority() {
        UsersType usersType = new UsersType();
        usersType.setUserTypeName("student");

        assertEquals("ROLE_STUDENT", usersType.getAuthority());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        UsersType usersType = new UsersType();
        usersType.setUserTypeId(1);
        usersType.setUserTypeName("TEACHER");

        String result = usersType.toString();

        assertNotNull(result);
        assertTrue(result.contains("UsersType{"));
        assertTrue(result.contains("userTypeId=1"));
        assertTrue(result.contains("userTypeName='TEACHER'"));
    }

    @Test
    @DisplayName("Test userTypeName null")
    void testNullUserTypeName() {
        UsersType usersType = new UsersType();
        usersType.setUserTypeName(null);

        assertNull(usersType.getUserTypeName());
    }
}
