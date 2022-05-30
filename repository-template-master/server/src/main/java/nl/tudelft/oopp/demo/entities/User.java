package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<Room> rooms;

    String lastIP;

    /**
     * Empty constructor for the User class.
     */
    public User() {

    }

    /**
     * Constructor for the User class.
     * @param username The new user's username
     * @param lastIP The last used IP address for this user
     */
    public User(String username, String lastIP) {
        this.username = username;
        this.lastIP = lastIP;
    }
}
