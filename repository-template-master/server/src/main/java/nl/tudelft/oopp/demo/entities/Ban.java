package nl.tudelft.oopp.demo.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;


@Entity
@Data
public class Ban {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private int duration;

    private Date expiration;

    private String ip;

    /**
     * Empty constructor for the Ban class.
     */
    public Ban() {

    }

    // TODO: use lombok for this shit
    /**
     * Yeet a user from your room.
     *
     * @param user user to ban
     * @param room room to ban the user from
     * @param duration time to ban the user for (0 for permaban)
     * @param ip IP address to ban
     */
    public Ban(User user, Room room, int duration, String ip) {
        this.user = user;
        this.room = room;
        this.duration = duration;

        LocalDateTime dateTime;
        if (duration == 0) {
            dateTime =
                    LocalDateTime.now().plus(Duration.of(525600, ChronoUnit.MINUTES));
        } else {
            dateTime =
                    LocalDateTime.now().plus(Duration.of(duration, ChronoUnit.MINUTES));
        }
        this.expiration = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        this.ip = ip;
    }
}
