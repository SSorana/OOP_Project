package nl.tudelft.oopp.demo.entities;

import java.util.UUID;
import javax.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"Question_id", "User_id"})
)
public class Vote {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Question question;

    @ManyToOne
    private User user;

    private int difference;

    public Vote() {
    }

    /**
     * Create a new Vote instance.
     *
     * @param question question that is being upvoted/downvoted
     * @param user user that is performing the upvote/downvote
     * @param difference int representing the upvote or downvote (1 or -1)
     */
    public Vote(Question question, User user, int difference) {
        this.question = question;
        this.user = user;
        this.difference = difference;
    }
}
