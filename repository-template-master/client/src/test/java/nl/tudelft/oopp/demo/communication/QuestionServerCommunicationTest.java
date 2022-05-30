package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.controllers.InitializationController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QuestionServerCommunicationTest {

    private static Room room;
    private static User user;
    private static List<Question> testQuestionList = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        try {
            InitializationController.setUser(
                    RoomServerCommunication.createUser("Bill"));
            InitializationController.setRoom(
                    RoomServerCommunication.postRoom(
                            "Calculus",
                            Timestamp.valueOf("2021-04-14 10:00:00"),
                            Timestamp.valueOf("2021-04-14 12:00:00")));
            user = InitializationController.getUser();
            room = InitializationController.getRoom();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void askQuestion() {
        try {
            Question question = QuestionServerCommunication.askQuestion(
                    "What is the purpose of life?");
            testQuestionList.add(question);
            assertTrue(question.getId() != null
                    && question.getUser().equals(user)
                    && question.getRoom().equals(room)
                    && question.getContent().equals("What is the purpose of life?")
                    && question.getDownVote() == 0
                    && question.getUpVote() == 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllQuestions() {
        try {
            Question question = QuestionServerCommunication.askQuestion(
                    "How does ice cream taste in space?");
            Question question1 = QuestionServerCommunication.askQuestion(
                    "What is your favourite color?");
            testQuestionList.add(question);
            testQuestionList.add(question1);
            List<Question> questionList = QuestionServerCommunication.getAllQuestions(room.getId());
            assertEquals(testQuestionList, questionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getQuestion() {
        try {
            Question testQuestion = QuestionServerCommunication.askQuestion(
                    "How are you doing?");
            testQuestionList.add(testQuestion);
            Question question = QuestionServerCommunication.getQuestion(
                    testQuestion.getId().toString());

            assertTrue(testQuestion.equals(question));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}