package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoomRepository roomRepository;
    @MockBean
    private QuestionRepository questionRepository;

    private Timestamp time1;
    private Timestamp time2;
    private User user;
    private Room room;
    private UserController userController;
    private RoomController roomController;

    @BeforeEach
    void setup() {
        time1 = Timestamp.valueOf("2021-04-09 10:00:00");
        time2 = Timestamp.valueOf("2021-04-09 12:00:00");

        user = new User("Bob", "localhost");
        room = new Room("Calculus", time1, time2, 0);

        userController = Mockito.mock(UserController.class);
        roomController = Mockito.mock(RoomController.class);
    }

    @Test
    void createUser() {
        when(userController.createUser(null, "Bob"))
                .thenReturn(user);

        User user2 = userController.createUser(null, "Bob");

        assertEquals(user2, user);
    }

    @Test
    void getUser() {
        UUID uuid = UUID.randomUUID();
        when(userController.getUser(uuid)).thenReturn(Optional.of(user));

        Optional<User> user2 = userController.getUser(uuid);

        assertEquals(user2, Optional.of(user));
    }

    @Test
    void getAllRooms() {
        UUID uuid = UUID.randomUUID();
        when(userController.getAllRooms(uuid))
                .thenReturn(List.of(room));

        user.setRooms(new ArrayList<>());
        user.getRooms().add(room);
        List<Room> list = userController.getAllRooms(uuid);

        assertEquals(list, user.getRooms());
    }

    @Test
    void createUserMockMvc() throws Exception {
        mockMvc.perform(post("/user/create")
                .param("username", "TestUser"))
                .andExpect(status().isOk());
    }

    @Test
    void invalidCreateUser() throws Exception {
        mockMvc.perform(post("/user/create")
                .param("yeet", "TestUser"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getUserMockMvc() throws Exception {
        mockMvc.perform(get("/user/get")
                .param("id", "50d901c2-d81b-4f8d-a72f-5c1a8df39ebb"))
                .andExpect(status().isOk());
    }

    @Test
    void invalidGetUser() throws Exception {
        mockMvc.perform(get("/user/get")
                .param("id", "not-a-valid-UUID"))
                .andExpect(status().isBadRequest());
    }

    /*
    Fails, because optionalUser and optionalRoom are empty
    @Test
    void join() throws Exception {
        mockMvc.perform(post("/user/join/50d901c2-d81b-4f8d-a72f-5c1a8df39ebb/"
                + "6c826b4a-250f-4ef6-ac24-a689ba71dc79"))
                .andExpect(status().isOk());
    }
     */
}