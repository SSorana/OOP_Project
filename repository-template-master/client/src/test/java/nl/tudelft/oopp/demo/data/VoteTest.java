package nl.tudelft.oopp.demo.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoteTest {
    private static Vote vote;
    private static UUID questionId;
    private static UUID userId;
    private static int difference;

    @BeforeEach
    void setup() {
        questionId = UUID.randomUUID();
        userId = UUID.randomUUID();
        difference = 5;
        vote = new Vote(questionId, userId, difference);
    }

    @Test
    void getQuestionId() {
        assertEquals(vote.getQuestionId(), questionId);
    }

    @Test
    void getUserId() {
        assertEquals(vote.getUserId(), userId);
    }

    @Test
    void getDifference() {
        assertEquals(vote.getDifference(), difference);
    }

    @Test
    void setQuestionId() {
        UUID questionId2 = UUID.randomUUID();
        vote.setQuestionId(questionId2);
        assertEquals(vote.getQuestionId(), questionId2);
    }

    @Test
    void setUserId() {
        UUID userId2 = UUID.randomUUID();
        vote.setUserId(userId2);
        assertEquals(vote.getUserId(), userId2);
    }

    @Test
    void setDifference() {
        vote.setDifference(3);
        assertEquals(vote.getDifference(), 3);
    }

    @Test
    void testEquals() {
        assertTrue(vote.equals(vote));
    }

    @Test
    void canEqual() {
        assertTrue(vote.equals(vote));
    }

}