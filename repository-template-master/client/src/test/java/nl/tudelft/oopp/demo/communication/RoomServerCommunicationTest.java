package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomServerCommunicationTest {

    private Timestamp time1;
    private Timestamp time2;

    @BeforeEach
    void setup() {
        time1 = Timestamp.valueOf("2021-04-14 10:00:00");
        time2 = Timestamp.valueOf("2021-04-14 12:00:00");
    }

    @Test
    void postRoomTest() {
        try {
            Room room = RoomServerCommunication.postRoom("roomTest", time1, time2);
            assertEquals(room,RoomServerCommunication.getRoom(room.getId().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getRoomTest() {
        try {
            Room testRoom = RoomServerCommunication.postRoom("test", time1, time2);
            Room sameRoom = RoomServerCommunication.getRoom(testRoom.getId().toString());
            assertEquals(testRoom, sameRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createUserTest() {
        try {
            User user = RoomServerCommunication.createUser("Bill");
            assertTrue(user.getUsername().equals("Bill") && user.getId() != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void joinRoomTest() {
        try {
            User user = RoomServerCommunication.createUser("Bill");
            Room room = RoomServerCommunication.postRoom(
                    "Calc", time1, time2);
            RoomServerCommunication.joinRoom(
                    user.getId().toString(), room.getId().toString());
            //create a test userList that should be the same as the one received from the server
            List<User> testUserList = new ArrayList<>();
            testUserList.add(user);
            List<User> userList =
                    RoomServerCommunication.getRoom(room.getId().toString()).getUserList();
            assertNotNull(RoomServerCommunication.getRoom(room.getId().toString()).getUserList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void changeLectureSpeed() {
        try {
            Room room = RoomServerCommunication.postRoom(
                    "Calc", time1, time2);
            RoomServerCommunication.changeLectureSpeed(room.getId(), -1);
            room = RoomServerCommunication.getRoom(room.getId().toString());
            assertEquals(1, room.getSpeed());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}