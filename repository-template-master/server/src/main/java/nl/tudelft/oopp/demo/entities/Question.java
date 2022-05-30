package nl.tudelft.oopp.demo.entities;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;


@Entity
@Data
public class Question {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private String content;

    private Date timestamp;

    private int upVote = 0;

    private int downVote = 0;

    /**
     * Create a new Question instance.
     */
    public Question() {
    }

    /**
     * Constructor for question class.
     *
     * @param user user who asked the question
     * @param room the room in which the question was asked
     * @param content content of the question
     */
    public Question(User user, Room room, String content) {
        this.user = user;
        this.room = room;
        this.content = content;
        LocalDateTime dateTime = LocalDateTime.now();
        this.timestamp = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Increments the number of upVotes.
     */
    public void addUpVote() {
        ++this.upVote;
    }

    /**
     * Decrements the number of upVotes.
     */
    public void removeUpVote() {
        --this.upVote;
    }

    /**
     * Increments the number of downVotes.
     */
    public void addDownVote() {
        ++this.downVote;
    }

    /**
     * Decrements the number of downVotes.
     */
    public void removeDownVote() {
        --this.downVote;
    }

    /**
     * Returns the priority of a Question.
     * @return the difference between the number of upVotes and downVotes
     */
    public double getPriority() {
        long time = this.timestamp.getTime() - Instant.now().toEpochMilli();
        return (upVote - downVote) / Math.max(1.0, time * 5.0);
    }
}