package nl.tudelft.oopp.demo.data;

import java.util.UUID;
import lombok.Data;

@Data
public class Vote {
    private UUID questionId;
    private UUID userId;
    private int difference;

    /**
     * constuctor for object vote.
     * @param questionId UUID of the question
     * @param userId UUID of the user
     * @param difference int with the difference between up votes and down votes
     */
    public Vote(UUID questionId, UUID userId, int difference) {
        this.questionId = questionId;
        this.userId = userId;
        this.difference = difference;
    }
}
