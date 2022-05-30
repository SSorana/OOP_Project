package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;


@Entity
@Data
public class Room {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID secretId;

    private String name;

    private Timestamp fromTime;

    private Timestamp toTime;

    private int speed;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "room_users",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonManagedReference
    private List<User> users;

    // TODO: Add timeslot! What format though? (Only one input on frontend atm)
    //       Remember to add to the constructor and RoomController /create endpoint as well

    /**
     * Empty constructor for the Room class.
     */
    public Room() {
        this.secretId = UUID.randomUUID();
    }

    /**
     * Constructor for the Room class.
     *
     * @param name the name chosen for the Room object
     * @param fromTime time at which the lecture starts
     * @param toTime time at which the lecture ends
     * @param speed speed of the Room
     */
    public Room(String name, Timestamp fromTime, Timestamp toTime, int speed) {
        this.secretId = UUID.randomUUID();
        this.name = name;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.speed = speed;
    }
}
