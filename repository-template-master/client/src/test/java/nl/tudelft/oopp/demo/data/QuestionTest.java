package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QuestionTest {
    private static UUID questionId;
    private static UUID questionId2;
    private static User user;
    private static User  user2;
    private static List<User> userList = new ArrayList<User>();
    private static Room room;
    private static Room room2;
    private static Date date;
    private static Date date2;
    private static Question question;
    private static Question question2;
    private static Question question3;
    private static Question question4;
    private static Timestamp fromTime;
    private static Timestamp toTime;

    @BeforeAll
    public static void setup() {
        fromTime = Timestamp.valueOf("2021-04-14 10:00:00");
        toTime = Timestamp.valueOf("2021-04-14 12:00:00");
        questionId = UUID.randomUUID();
        questionId2 = UUID.randomUUID();

        user = new User(UUID.randomUUID(), "Bill", "Student");
        user2 = new User(UUID.randomUUID(), "Bob", "Student");
        userList.add(user);
        userList.add(user2);
        room = new Room(UUID.randomUUID(), UUID.randomUUID(), "testRoom",
                userList, fromTime, toTime, 0);
        room2 = new Room(UUID.randomUUID(), UUID.randomUUID(), "testRoom2",
                userList, fromTime, toTime, 0);
        date = new Date();
        date2 = new Date();

        question = new Question(
                questionId, user2, room, "Does this work?", date, 0, 0);
        question2 = new Question(
                UUID.randomUUID(), user2, room,"Does this work?", date, 2, 4);
        question3 = new Question(
                UUID.randomUUID(), user, room2,"Does this work?", date2, 2, 3);
        question4 = new Question(
                UUID.randomUUID(), user, room2, "Does this work?", date2, 0, 0);
    }

    @Test
    void writeToFileTest() {
        assertEquals(question.writeToFile(), "Question:"
              + "Does this work?" + " (" + date + ")" + "\n");
    }

    @Test
    void writeToChat() {
        assertEquals(question.writeToChat(), "Bob"
              + ": " + "Does this work?" +  " (" + date.getHours() + ":"
                + date.getMinutes() + ":" + date.getSeconds() + ")\n");
    }

    @Test
    void getIdTest() {
        assertEquals(question.getId(),questionId);
    }

    @Test
    void getUserTest() {
        assertEquals(question.getUser(),user2);
    }

    @Test
    void getRoomTest() {
        assertEquals(question.getRoom(),room);
    }

    @Test
    void getContentTest() {
        assertEquals(question2.getContent(),"Does this work?");
    }

    @Test
    void getTimestampTest() {
        assertEquals(question.getTimestamp(),date);
    }

    @Test
    void getUpvote() {
        assertEquals(question3.getUpVote(),2);
    }

    @Test
    void getDownVote() {
        assertEquals(question3.getDownVote(),3);
    }


    @Test
    void setId() {
        question2.setId(questionId2);
        assertEquals(question2.getId(), questionId2);
    }

    @Test
    void setUserTest() {
        question2.setUser(user);
        assertEquals(question2.getUser(), user);
    }

    @Test
    void setRoomTest() {
        question3.setRoom(room);
        assertEquals(question.getRoom(), room);
    }

    @Test
    void setContentTest() {
        question2.setContent("Test");
        assertEquals(question2.getContent(), "Test");
    }

    @Test
    void setTimestampTest() {
        question.setTimestamp(date2);
        assertEquals(question.getTimestamp(), date2);
    }

    @Test
    void setUpvoteTest() {
        question3.setUpVote(4);
        assertEquals(question3.getUpVote(),4);
    }

    @Test
    void setDownVoteTest() {
        question3.setDownVote(5);
        assertEquals(question3.getDownVote(),5);
    }

}