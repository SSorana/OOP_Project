package nl.tudelft.oopp.demo.data;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class Question {

    private UUID id;
    private User user;
    private Room room;
    private String content;
    private Date timestamp;
    private int upVote;
    private int downVote;


    /**
     * Constructor for question.
     *
     * @param user the user who sent the question
     * @param room the room the question was posted in
     * @param content the content of  the question
     * @param timestamp the time when the question was asked
     * @param upVote number of people that upVoted the question
     * @param downVote number of people that downVoted the question
     */
    public Question(UUID id, User user, Room room, String content, Date timestamp,
                    int upVote, int downVote) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.content = content;
        this.timestamp = timestamp;
        this.upVote = upVote;
        this.downVote = downVote;
    }

    /**
     * Empty constructor for the Question class.
     */
    public Question() {

    }

    /**
     *  Creates the string to write to the file.
     *
     * @return the content + timestamp of a question
     */
    public String writeToFile() {
        String result = "";
        result = result + "Question:" + this.content + " (" + this.timestamp + ")" + "\n";
        return result;
    }

    /**
     *  Creates the string to read the chat.
     *
     * @return the username + content + timestamp of a question
     */
    public String writeToChat() {
        String result = "";
        result = result + this.user.getUsername() + ": " + this.content
                + " (" + this.timestamp.getHours() + ":" + this.timestamp.getMinutes() + ":"
                + this.timestamp.getSeconds() + ")\n";
        return result;
    }


    /**
     *  Creates the string to read the chat.
     *
     * @return the username + content + timestamp of a question
     */
    public String spamWriteToChat() {
        String result = "(SPAM) ";
        result = result + this.user.getUsername() + ": " + this.content
                + " (" + this.timestamp + ")\n";
        return result;
    }
}
