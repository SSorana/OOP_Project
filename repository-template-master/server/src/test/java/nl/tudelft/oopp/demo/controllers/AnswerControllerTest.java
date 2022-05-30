package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Answer;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AnswerControllerTest {
    private Timestamp time1;
    private Timestamp time2;
    private Date date;
    private Question question;
    private User user;
    private Room room;
    private Answer answer;
    private UserController userController;
    private RoomController roomController;
    private QuestionController questionController;
    private AnswerController answerController;

    @BeforeEach
    void setup() {
        time1 = Timestamp.valueOf("2021-04-09 10:00:00");
        time2 = Timestamp.valueOf("2021-04-09 12:00:00");
        date = new Date();

        user = new User("Bob", "localhost");
        room = new Room("Calculus", time1, time2, 0);
        question = new Question(user, room, "How do I exit Vim?");
        answer = new Answer(question, user, "Just delete your OS.", date);

        userController = Mockito.mock(UserController.class);
        roomController = Mockito.mock(RoomController.class);
        questionController = Mockito.mock(QuestionController.class);
        answerController = Mockito.mock(AnswerController.class);
    }

    @Test
    void createAnswer() {
        when(answerController.createAnswer(
                user.getId(),
                room.getId(),
                "Just delete your OS."
        )).thenReturn(answer);

        Answer answer2 = new Answer(question, user, "Just delete your OS.", date);

        assertEquals(answer2, answer);
    }

    @Test
    void getAnswer() {
        UUID uuid = UUID.randomUUID();
        when(answerController.getAnswer(uuid)).thenReturn(Optional.of(answer));

        Optional<Answer> answer2 = answerController.getAnswer(uuid);

        assertEquals(answer2, Optional.of(answer));
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
    void getAllQuestion() {
        List<Answer> list = answerController.getAllQuestion(room.getId());
        assertEquals(new ArrayList<>(), list);
    }
}