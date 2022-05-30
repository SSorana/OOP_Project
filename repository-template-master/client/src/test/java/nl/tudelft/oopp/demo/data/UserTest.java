package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class UserTest {

    private static User user;
    private static User user2;
    private static UUID user2Id;

    @BeforeAll
    public static void setup() {
        user = new User(UUID.randomUUID(), "Nik", "Lecturer");
        user2Id = UUID.randomUUID();
        user2 = new User(user2Id, "Bob", "Student");
    }

    @Test
    void getId() {
        assertTrue(user2.getId().equals(user2Id));
    }

    @Test
    void getUsername() {
        assertTrue(user2.getUsername().equals("Bob"));
    }

    @Test
    void getRole() {
        assertTrue(user2.getRole().equals("Student"));
    }

    @Test
    void setId() {
        user.setId(user2Id);
        assertTrue(user.getId().equals(user2Id));
    }

    @Test
    void setUsername() {
        user.setUsername("Andy");
        assertTrue(user.getUsername().equals("Andy"));
    }

    @Test
    void setRole() {
        user.setRole("Moderator");
        assertTrue(user.getRole().equals("Moderator"));
    }

    @Test
    void testEquals() {
        User user3 = new User(user2Id, "Bob", "Student");
        assertTrue(user3.equals(user2));
    }

    @Test
    void testToString() {
        assertEquals(user2.toString(), "User(id=" + user2.getId()
                + ", username=" + user2.getUsername()
                + ", role=" + user2.getRole() + ")");
    }
}