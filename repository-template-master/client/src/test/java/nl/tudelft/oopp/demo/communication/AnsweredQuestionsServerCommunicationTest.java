package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import nl.tudelft.oopp.demo.controllers.InitializationController;
import nl.tudelft.oopp.demo.data.Answered;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AnsweredQuestionsServerCommunicationTest {

    private static User user;
    private static Room room;

    @BeforeAll
    public static void setup() {
        try {
            user = RoomServerCommunication.createUser("Jocelyn");
            room = RoomServerCommunication.postRoom(
                    "Room1",
                    Timestamp.valueOf("2021-04-14 10:00:00"),
                    Timestamp.valueOf("2021-04-14 12:00:00"));
            InitializationController.setUser(user);
            InitializationController.setRoom(room);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllAnswersTest() {
        try {
            Room tempRoom = RoomServerCommunication.postRoom(
                    "Room2",
                    Timestamp.valueOf("2021-04-14 10:00:00"),
                    Timestamp.valueOf("2021-04-14 12:00:00"));
            InitializationController.setRoom(tempRoom);
            Question question = QuestionServerCommunication.askQuestion(
                    "What’s the weirdest thing a guest has done at your house?");
            Question question2 = QuestionServerCommunication.askQuestion(
                    "What’s the most imaginative insult you can come up with?");
            AnsweredQuestionsServerCommunication.markAnswered(
                    question.getId(), user.getId(), "vomit on the carpet");
            AnsweredQuestionsServerCommunication.markAnswered(
                    question2.getId(), user.getId(), "I will get fired if I tell you");

            List<Answered> answerList =
                    AnsweredQuestionsServerCommunication.getAllAnswers(tempRoom.getId());
            assertTrue(answerList.size() == 2
                    && answerList.get(0).getContent().equals("vomit on the carpet")
                    && answerList.get(1).getContent().equals("I will get fired if I tell you"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            InitializationController.setRoom(room);
        }
    }

    @Test
    void markAnsweredTest() {
        try {
            Question question = QuestionServerCommunication.askQuestion(
                    "If peanut butter wasn't called peanut butter, what would it be called?");
            Answered answer = AnsweredQuestionsServerCommunication.markAnswered(
                    question.getId(), InitializationController.getUser().getId(), "peanut jelly");
            List<Answered> answeredList =
                    AnsweredQuestionsServerCommunication.getAllAnswers(room.getId());
            Answered expected = answeredList.get(0);
            assertTrue(expected.getQuestion().equals(answer.getQuestion())
                    && expected.getUser().equals(answer.getUser())
                    && expected.getContent().equals(answer.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}