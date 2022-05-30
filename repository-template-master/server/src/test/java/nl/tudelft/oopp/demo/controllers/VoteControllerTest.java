package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VoteControllerTest {
    private Timestamp time1;
    private Timestamp time2;
    private Date date;
    private Question question;
    private User user;
    private Room room;
    private Vote vote;
    private UserController userController;
    private RoomController roomController;
    private QuestionController questionController;
    private VoteController voteController;

    @BeforeEach
    void setup() {
        time1 = Timestamp.valueOf("2021-04-09 10:00:00");
        time2 = Timestamp.valueOf("2021-04-09 12:00:00");
        date = new Date();

        user = new User("Bob", "localhost");
        room = new Room("Calculus", time1, time2, 0);
        question = new Question(user, room, "How do I exit Vim?");
        vote = new Vote(question, user, 5);

        userController = Mockito.mock(UserController.class);
        roomController = Mockito.mock(RoomController.class);
        questionController = Mockito.mock(QuestionController.class);
        voteController = Mockito.mock(VoteController.class);
    }

    @Test
    void createVote() {
        when(voteController.createVote(
                user.getId(),
                room.getId(),
                5
        )).thenReturn(vote);

        Vote vote2 = new Vote(question, user, 5);

        assertEquals(vote2, vote);
    }

    @Test
    void getVote() {
        UUID uuid = UUID.randomUUID();
        when(voteController.getVote(uuid.toString())).thenReturn(Optional.of(vote));

        Optional<Vote> vote2 = voteController.getVote(uuid.toString());

        assertEquals(vote2, Optional.of(vote));
    }

    @Test
    void getAllVotes() {
        UUID uuid = UUID.randomUUID();

        List<Vote> list = voteController.getAllVotes(uuid.toString());

        assertEquals(new ArrayList<>(), list);
    }
}