package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RoomTest {
    private static Room room;
    private static Room room2;
    private static final UUID roomId = UUID.randomUUID();
    private static final UUID roomSecretId = UUID.randomUUID();
    private static User user;
    private static User user2;
    private static List<User> userList = new ArrayList<>();
    private static Timestamp fromTime;
    private static Timestamp toTime;

    @BeforeAll
    public static void setup() {
        fromTime = Timestamp.valueOf("2021-04-14 10:00:00");
        toTime = Timestamp.valueOf("2021-04-14 12:00:00");
        user = new User(UUID.randomUUID(), "Ted", "Lecturer");
        user2 = new User(UUID.randomUUID(), "Bob", "Student");
        room2 = new Room(UUID.randomUUID(), UUID.randomUUID(), "testRoom2", userList,
                fromTime, toTime, 0);
        userList.add(user);
        userList.add(user2);
        room = new Room(roomId, roomSecretId, "testRoom", userList, fromTime, toTime, 0);
    }

    @Test
    void getId() {
        assertTrue(room.getId().equals(roomId));
    }

    @Test
    void getSecretId() {
        assertTrue(room.getSecretId().equals(roomSecretId));
    }

    @Test
    void getName() {
        assertEquals(room.getName(), "testRoom");
    }

    @Test
    void getUserList() {
        assertEquals(room.getUserList(), userList);
    }

    @Test
    void setId() {
        room2.setId(roomId);
        assertEquals(roomId, room2.getId());
    }

    @Test
    void setSecretId() {
        room2.setSecretId(roomSecretId);
        assertEquals(roomSecretId, room2.getSecretId());
    }

    @Test
    void setName() {
        room2.setName("randomTestRoom");
        assertTrue(room2.getName().equals("randomTestRoom"));
    }

    @Test
    void setUserList() {
        room2.setUserList(userList);
        assertEquals(userList, room2.getUserList());
    }

    @Test
    void testEquals() {
        Room room3 = new Room(roomId, roomSecretId, "testRoom",
                userList, fromTime, toTime, 0);
        assertTrue(room.equals(room3));
    }

    @Test
    void testToString() {
        assertEquals(room.toString(), "Room(id=" + roomId
                + ", secretId=" + roomSecretId
                + ", name=testRoom, " + "userList=" + userList
                + ", fromTime=" + room.getFromTime()
                + ", toTime=" + room.getToTime()
                + ", speed=" + room.getSpeed() + ")");
    }
}