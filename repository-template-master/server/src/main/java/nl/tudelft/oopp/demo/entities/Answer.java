package nl.tudelft.oopp.demo.entities;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Data
public class Answer {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @OneToOne
    private Question question;

    private String content;

    @Temporal(TemporalType.DATE)
    private Date timestamp;

    /**
     * Empty constructor for the Answer class.
     */
    public Answer() {

    }

    /**
     * Constructor for the Answer class.
     *
     * @param question  (FK) the question that is being answered
     * @param user      (FK) the user that answered the question?
     * @param content   the typed out answer to the question (optional)
     * @param timestamp when the question was answered
     */
    public Answer(Question question, User user, String content, Date timestamp) {
        this.question = question;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }



}