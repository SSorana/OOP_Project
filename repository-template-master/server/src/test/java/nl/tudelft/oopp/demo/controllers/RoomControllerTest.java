package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoomControllerTest {
    private Timestamp time1;
    private Timestamp time2;
    private User user;
    private Room room;
    private UserController userController;
    private RoomController roomController;
    private QuestionController questionController;

    @BeforeEach
    void setup() {
        time1 = Timestamp.valueOf("2021-04-09 10:00:00");
        time2 = Timestamp.valueOf("2021-04-09 12:00:00");

        user = new User("Bob", "localhost");
        room = new Room("Calculus", time1, time2, 0);

        userController = Mockito.mock(UserController.class);
        roomController = Mockito.mock(RoomController.class);
        questionController = Mockito.mock(QuestionController.class);
    }

    @Test
    void createRoom() {
        when(roomController.createRoom("Calculus", time1, time2))
                .thenReturn(room);

        Room room2 = roomController.createRoom("Calculus", time1, time2);

        assertEquals(room2, room);
    }

    @Test
    void getRoom() {
        UUID uuid = UUID.randomUUID();
        when(roomController.getRoom(uuid)).thenReturn(room);

        Room room2 = roomController.getRoom(uuid);

        assertEquals(room2, room);
    }

    @Test
    void getAllUsers() {
        when(roomController.getAllUsers(room.getId()))
                .thenReturn(List.of(user));

        room.setUsers(new ArrayList<>());
        room.getUsers().add(user);
        List<User> list = roomController.getAllUsers(room.getId());

        assertEquals(list, room.getUsers());
    }

    @Test
    void refreshFeed() {
        when(roomController.refreshFeed(room.getId()))
                .thenReturn(new ArrayList<>());

        List<Question> list = roomController.refreshFeed(room.getId());
        List<Question> list2 = questionController.getAllQuestion(room.getId());

        assertEquals(list2, list);
    }

    @Test
    void getAnsweredQuestions() {
        List<Question> list = new ArrayList<>();
        assertEquals(list, roomController.getAnsweredQuestions(room.getId()));
    }
}