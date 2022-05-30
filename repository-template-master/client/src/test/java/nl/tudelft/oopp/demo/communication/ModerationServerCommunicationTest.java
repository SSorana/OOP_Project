package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;
import nl.tudelft.oopp.demo.controllers.InitializationController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ModerationServerCommunicationTest {

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
    void editQuestion() {
        try {
            Question question = QuestionServerCommunication.askQuestion(
                    "How is this application so good?");
            String oldQuestionContent = question.getContent();

            String newQuestionContent = "Do you like penguins?";
            question.setContent(newQuestionContent);

            ModerationServerCommunication.editQuestion(room.getSecretId(), question);

            Question editedQuestion =
                    QuestionServerCommunication.getQuestion(question.getId().toString());
            assertTrue(editedQuestion.getContent().equals(newQuestionContent)
                    && !(oldQuestionContent.equals(newQuestionContent)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeQuestion() {
        try {
            Question questionToRemove = QuestionServerCommunication.askQuestion(
                    "What’s the best Wi-Fi name you’ve seen?");
            UUID questionId = questionToRemove.getId();
            ModerationServerCommunication.removeQuestion(room.getSecretId(), questionToRemove);
            Question questionIsRemoved =
                    QuestionServerCommunication.getQuestion(questionId.toString());
            assertNull(questionIsRemoved);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userEditQuestion() {
        try {
            Question question = QuestionServerCommunication.askQuestion(
                    "Toilet paper, over or under?");
            String oldQuestionContent = question.getContent();

            String newQuestionContent = "How many chickens would it take to kill an elephant?";
            question.setContent(newQuestionContent);
            //the main difference with the other editQuestion test is here, we take the userId
            //and check if the pair matches as opposed to taking the secretId
            ModerationServerCommunication.userEditQuestion(user.getId(), question);

            Question editedQuestion =
                    QuestionServerCommunication.getQuestion(question.getId().toString());
            assertTrue(editedQuestion.getContent().equals(newQuestionContent)
                    && !(oldQuestionContent.equals(newQuestionContent)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userCanOnlyEditOwnQuestion() {
        //Testing the fact that as a regular user you can only edit your own questions
        try {
            Question questionDifferentUser = QuestionServerCommunication.askQuestion(
                    "What would be the absolute worst name you could give your child?");
            User user = RoomServerCommunication.createUser("Jhon");
            assertThrows(IOException.class, () -> {
                ModerationServerCommunication.userEditQuestion(user.getId(), questionDifferentUser);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userRemoveQuestion() {
        try {
            Question questionToRemove = QuestionServerCommunication.askQuestion(
                    "If life were a video game, what would some of the cheat codes be?");
            UUID questionId = questionToRemove.getId();
            //Again difference is that we get the userId and check it against the questionId
            ModerationServerCommunication.userRemoveQuestion(user.getId(), questionId);
            Question questionIsRemoved =
                    QuestionServerCommunication.getQuestion(questionId.toString());
            assertNull(questionIsRemoved);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userCanOnlyRemoveOwnQuestion() {
        //Testing the fact that as a regular user you can only remove your own questions
        try {
            Question questionDifferentUser = QuestionServerCommunication.askQuestion(
                    "What would be the absolute worst name you could give your child?");
            User user = RoomServerCommunication.createUser("Jhon");
            assertThrows(IOException.class, () -> {
                ModerationServerCommunication.userRemoveQuestion(
                        user.getId(), questionDifferentUser.getId());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    @Test
    void timeoutUser() {
        try {
            User userToBan = RoomServerCommunication.createUser("Sam");
            // 0 means it is permanent
            ModerationServerCommunication.timeoutUser(
                    room.getSecretId(), userToBan.getId(), room.getId(), 0);
            InitializationController.setUser(userToBan);
            assertThrows(IOException.class, () -> {
                //Exception since being banned means you can't ask questions in this room
                QuestionServerCommunication.askQuestion("Question?");
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            InitializationController.setUser(user);
        }
    }
    */
}