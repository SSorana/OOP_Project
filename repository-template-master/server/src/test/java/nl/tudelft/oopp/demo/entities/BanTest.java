package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.UUID;
import nl.tudelft.oopp.demo.repositories.BanRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class BanTest {
    private static Ban ban;
    private static Ban ban2;
    private static User user;
    private static Room room;
    private static int duration = 10;
    private static String lastIP = "127.0.0.1";


    @Autowired
    private BanRepository banRepository;

    @BeforeAll
    public static void setup() {
        user = new User("James", lastIP);
        room = new Room();
        ban = new Ban(user, room, duration, lastIP);
        ban2 = new Ban(new User(), new Room(), 25, "ip");
    }

    @Test
    void saveAndRetrieveBan() {
        banRepository.save(ban);
        UUID id = ban.getId();
        Ban banTest = banRepository.getOne(id);
        assertEquals(ban, banTest);
    }

    @Test
    void getUser() {
        assertEquals(user, ban.getUser());
    }

    @Test
    void getRoom() {
        assertEquals(room, ban.getRoom());
    }

    @Test
    void getDuration() {
        assertEquals(duration, ban.getDuration());
    }

    @Test
    void getIp() {
        assertEquals(lastIP, ban.getIp());
    }

    @Test
    void setId() {
        UUID id = UUID.randomUUID();
        ban2.setId(id);
        assertTrue(ban2.getId().equals(id));
    }

    @Test
    void setUser() {
        ban2.setUser(user);
        assertEquals(ban2.getUser(), user);
    }

    @Test
    void setRoom() {
        ban2.setRoom(room);
        assertEquals(room, ban2.getRoom());
    }

    @Test
    void setDuration() {
        ban2.setDuration(duration);
        assertEquals(duration, ban2.getDuration());
    }

    @Test
    void setExpiration() {
        Date expiration = ban.getExpiration();
        ban2.setExpiration(expiration);
        assertTrue(ban2.getExpiration().equals(expiration));
    }

    @Test
    void setIp() {
        ban2.setIp(lastIP);
        assertEquals(ban2.getIp(), lastIP);
    }


    @Test
    void testToString() {
        assertEquals(ban.toString(), "Ban(id=" + ban.getId()
                + ", user=" + ban.getUser()
                + ", room=" + ban.getRoom()
                + ", duration=" + ban.getDuration()
                + ", expiration=" + ban.getExpiration()
                + ", ip=" + ban.getIp() + ")");
    }
}