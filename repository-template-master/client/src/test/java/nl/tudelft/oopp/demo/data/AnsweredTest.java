package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnsweredTest {
    private static User user;
    private static Room room;
    private static Question question;
    private static Date timestamp = new Date();
    private static Answered answered;
    private static Timestamp time1;
    private static Timestamp time2;


    @BeforeEach
    void setup() {
        time1 = Timestamp.valueOf("2021-04-04 10:00:00");
        time1 = Timestamp.valueOf("2021-04-04 12:00:00");
        user = new User(UUID.randomUUID(), "Jocelyn", "Student");
        room = new Room(
                UUID.randomUUID(), UUID.randomUUID(), "OOPP",
                new ArrayList<>(), time1, time2, 0);
        question = new Question(UUID.randomUUID(), user, room,
                "What's the best type of cheese?", new Date(), 0, 0);
        answered = new Answered(question, user, "Feta", timestamp);
    }

    @Test
    void getQuestion() {
        assertEquals(answered.getQuestion(), question);
    }

    @Test
    void setQuestion() {
        Question tempQuestion = new Question(UUID.randomUUID(), user, room,
                "What cheese do you like?", new Date(), 0, 0);
        answered.setQuestion(tempQuestion);
        assertEquals(tempQuestion,answered.getQuestion());
    }

    @Test
    void getUser() {
        assertEquals(answered.getUser(), user);
    }

    @Test
    void setUser() {
        User user2 = new User(UUID.randomUUID(), "Sorana Stan", "lecturer");
        answered.setUser(user2);
        assertEquals(answered.getUser(), user2);
    }

    @Test
    void getContent() {
        assertEquals(answered.getContent(), "Feta");
    }

    @Test
    void setContent() {
        String input = "abc";
        answered.setContent(input);
        assertEquals(answered.getContent(), input);
    }

    @Test
    void getTimestamp() {
        assertEquals(answered.getTimestamp(), timestamp);
    }

    @Test
    void setTimestamp() {
        Date difDate = new Date();
        answered.setTimestamp(difDate);
        assertEquals(answered.getTimestamp(), difDate);
    }

    @Test
    void showOnWindow() {
        assertEquals(answered.showOnWindow(), "Jocelyn: What's the best type of cheese?\n"
                + "  Answer: Feta");
    }

    @Test
    void testEquals() {
        assertTrue(answered.equals(answered));
    }

    @Test
    void testToString() {
        assertEquals(answered.toString(), "Answered(question=" + answered.getQuestion()
                + ", user=" + answered.getUser()
                + ", content=" + answered.getContent()
                + ", timestamp=" + answered.getTimestamp() + ")");
    }
}