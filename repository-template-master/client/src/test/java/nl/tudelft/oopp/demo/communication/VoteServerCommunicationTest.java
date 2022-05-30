package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;

import nl.tudelft.oopp.demo.controllers.InitializationController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.data.Vote;
import org.junit.jupiter.api.Test;

class VoteServerCommunicationTest {

    @Test
    void postVote() {
        try {
            User user = RoomServerCommunication.createUser("Bill");
            Room room = RoomServerCommunication.postRoom(
                    "Calculus",
                    Timestamp.valueOf("2021-04-14 10:00:00"),
                    Timestamp.valueOf("2021-04-14 12:00:00"));
            InitializationController.setUser(user);
            InitializationController.setRoom(room);
            Question question =
                    QuestionServerCommunication.askQuestion("What time is it?");
            question.setUpVote(1);
            Vote vote = VoteServerCommunication.postVote(question);
            assertTrue(vote.getDifference() == 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}