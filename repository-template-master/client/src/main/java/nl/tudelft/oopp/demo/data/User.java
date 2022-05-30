package nl.tudelft.oopp.demo.data;

import java.util.UUID;
import lombok.Data;

@Data
public class User {

    private UUID id;
    private String username;
    private String role;

    /**
     * Constructor for the User class.
     * @param username the name of the user
     * @param role the role that the user has
     */
    public User(UUID id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    /**
     * Empty constructor for the User class.
     */
    public User() {

    }
}
