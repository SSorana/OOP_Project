package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QuestionControllerTest {
    private Timestamp time1;
    private Timestamp time2;
    private User user;
    private Room room;
    private Question question;
    private UserController userController;
    private RoomController roomController;
    private QuestionController questionController;

    @BeforeEach
    void setup() {
        time1 = Timestamp.valueOf("2021-04-09 10:00:00");
        time2 = Timestamp.valueOf("2021-04-09 12:00:00");

        user = new User("Bob", "localhost");
        room = new Room("Calculus", time1, time2, 0);
        question = new Question(user, room, "How are you?");

        userController = Mockito.mock(UserController.class);
        roomController = Mockito.mock(RoomController.class);
        questionController = Mockito.mock(QuestionController.class);
    }

    @Test
    void createQuestion() {
        when(questionController.createQuestion(null, user.getId(), room.getId(), "How are you?"))
                .thenReturn(question);

        Question question2 =
                questionController.createQuestion(null, user.getId(), room.getId(), "How are you?");

        assertEquals(question, question2);
    }

    @Test
    void getQuestion() {
        when(questionController.getQuestion(question.getId()))
                .thenReturn(Optional.ofNullable(question));
        Optional<Question> question2 = questionController.getQuestion(question.getId());
        assertTrue(question2.isPresent());
    }

    @Test
    void getAllQuestion() {
        List<Question> list = new ArrayList<>();
        list.add(question);
        when(questionController.getAllQuestion(room.getId())).thenReturn(list);

        List<Question> list2 = questionController.getAllQuestion(room.getId());
        assertEquals(list, list2);
    }
}