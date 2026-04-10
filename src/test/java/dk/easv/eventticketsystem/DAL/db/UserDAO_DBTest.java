package dk.easv.eventticketsystem.DAL.db;

import dk.easv.eventticketsystem.BE.Role;
import dk.easv.eventticketsystem.BE.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAO_DBTest {

    private User testUser;

    @BeforeEach
    void setUp() {

        testUser = new User(
                0,
                "John",
                "Doe",
                "johndoe",
                "password123",
                Role.COORDINATOR
        );
    }



    @Test
    void testUserGettersReturnCorrectValues() {
        assertEquals("John", testUser.getFirstName());
        assertEquals("Doe", testUser.getLastName());
        assertEquals("johndoe", testUser.getUsername());
        assertEquals("password123", testUser.getPassword());
        assertEquals(Role.COORDINATOR, testUser.getRole());
    }

    @Test
    void testGetFullNameConcatenatesCorrectly() {
        assertEquals("John Doe", testUser.getFullName());
    }

    @Test
    void testSettersUpdateValues() {
        testUser.setFirstName("Jane");
        testUser.setLastName("Smith");
        testUser.setUsername("janesmith");
        testUser.setPassword("newpass456");
        testUser.setRole(Role.ADMIN);

        assertEquals("Jane", testUser.getFirstName());
        assertEquals("Smith", testUser.getLastName());
        assertEquals("janesmith", testUser.getUsername());
        assertEquals("newpass456", testUser.getPassword());
        assertEquals(Role.ADMIN, testUser.getRole());
    }

    @Test
    void testFullNameUpdatesAfterSetFirstAndLastName() {
        testUser.setFirstName("Alice");
        testUser.setLastName("Wonderland");
        assertEquals("Alice Wonderland", testUser.getFullName());
    }

    @Test
    void testUserRoleIsAdmin() {
        User adminUser = new User(1, "Admin", "User", "admin", "pass", Role.ADMIN);
        assertEquals(Role.ADMIN, adminUser.getRole());
        assertNotEquals(Role.COORDINATOR, adminUser.getRole());
    }

    @Test
    void testUserRoleIsCoordinator() {
        User coordinatorUser = new User(2, "Coord", "User", "coord", "pass", Role.COORDINATOR);
        assertEquals(Role.COORDINATOR, coordinatorUser.getRole());
        assertNotEquals(Role.ADMIN, coordinatorUser.getRole());
    }

    @Test
    void testUserIdIsSetCorrectly() {
        User userWithId = new User(42, "Test", "User", "testuser", "pass", Role.COORDINATOR);
        assertEquals(42, userWithId.getUserId());
    }

    @Test
    void testPasswordCanBeSetToBlank() {

        testUser.setPassword("");
        assertEquals("", testUser.getPassword());
    }

    @Test
    void testUsernameCanBeChanged() {
        testUser.setUsername("newusername");
        assertEquals("newusername", testUser.getUsername());
    }

    @Test
    void testRoleEnumValuesExist() {
        Role[] roles = Role.values();
        assertEquals(2, roles.length);
        assertNotNull(Role.valueOf("ADMIN"));
        assertNotNull(Role.valueOf("COORDINATOR"));
    }
}
