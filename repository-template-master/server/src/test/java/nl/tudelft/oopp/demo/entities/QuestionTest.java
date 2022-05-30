package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void saveAndRetrieveQuestion() {
        Timestamp fromTime = Timestamp.valueOf("2021-04-14 10:00:00");
        Timestamp toTime = Timestamp.valueOf("2021-04-14 12:00:00");
        User user = new User("JohnSmith", "fake IP");
        Room room = new Room("Calculus", fromTime, toTime, 0);

        String content = "How do I exit Vim?";
        Question question = new Question(user, room, content);
        questionRepository.save(question);
        UUID id = question.getId();

        Question question2 = questionRepository.getOne(id);
        assertEquals(question, question2);
    }
}
