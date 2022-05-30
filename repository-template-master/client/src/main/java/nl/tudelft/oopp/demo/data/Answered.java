package nl.tudelft.oopp.demo.data;

import java.util.Date;
import lombok.Data;

@Data
public class Answered {

    private Question question;
    private User user;
    private String content;
    private Date timestamp;

    /**
     * Create a new Answer instance.
     *
     * @param question the questions asked by the student.
     * @param user the user who answered the question
     * @param content the answer itself
     * @param timestamp time when the answer was given
     */
    public Answered(Question question, User user, String content, Date timestamp) {
        this.question = question;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    /**
     * Create a string to be shown on the screen.
     *
     * @return a screen with the username, content and answer
     */
    public String showOnWindow() {
        String result = "";
        result = result + this.user.getUsername() + ": " + this.question.getContent()
                + "\n  Answer: " + this.content;
        return result;
    }
}
