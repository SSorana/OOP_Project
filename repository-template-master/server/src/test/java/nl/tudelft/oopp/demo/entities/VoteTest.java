package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.Vote;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.repositories.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VoteTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void saveAndRetrieveVote() {
        String username = "JohnSmith";
        User user = new User(username, "fake IP");
        userRepository.save(user);

        Timestamp fromTime = Timestamp.valueOf("2021-04-14 10:00:00");
        Timestamp toTime = Timestamp.valueOf("2021-04-14 12:00:00");
        String content = "Example question?";
        Room room = new Room("Calculus", fromTime, toTime, 0);
        Question question = new Question(user, room, content);
        questionRepository.save(question);

        int difference = 1;
        Vote vote = new Vote(question, user, difference);
        voteRepository.save(vote);
        UUID voteId = vote.getId();

        Vote vote2 = voteRepository.getOne(voteId);
        assertEquals(vote, vote2);
    }
}
