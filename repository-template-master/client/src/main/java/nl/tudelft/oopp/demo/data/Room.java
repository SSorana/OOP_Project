package nl.tudelft.oopp.demo.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Room {

    private UUID id;
    private UUID secretId;
    private String name;
    private List<User> userList = new ArrayList<>();
    private Timestamp fromTime;
    private Timestamp toTime;
    private int speed;

    /**
     * Constructor for the Room class.
     *
     * @param id Student ID
     * @param secretId Moderator ID
     * @param name name of the Room
     * @param userList the list of Users in the Room
     * @param fromTime time at which the lecture starts
     * @param toTime time at which the lecture ends
     * @param speed speed of the Room
     */
    public Room(UUID id, UUID secretId,
                String name, List<User> userList,
                Timestamp fromTime, Timestamp toTime, int speed) {
        this.id = id;
        this.secretId = secretId;
        this.name = name;
        this.userList = userList;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.speed = speed;
    }

    /**
     * Empty constructor for the Question class.
     */
    public Room() {

    }

}
