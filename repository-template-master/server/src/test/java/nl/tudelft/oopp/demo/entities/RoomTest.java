package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomTest {
    @Autowired
    private RoomRepository roomRepository;

    private Timestamp fromTime;
    private Timestamp toTime;
    private Room room;
    private UUID uuid;

    /**
     * Setup method for the tests.
     */
    @BeforeEach
    public void setup() {
        fromTime = Timestamp.valueOf("2021-04-14 10:00:00");
        toTime = Timestamp.valueOf("2021-04-14 12:00:00");

        room = new Room("Calculus", fromTime, toTime, 0);
        roomRepository.save(room);
        uuid = room.getId();
    }

    @Test
    public void saveAndRetrieveRoom() {
        Room room2 = roomRepository.getOne(uuid);
        assertEquals(room, room2);
    }

    @Test
    public void generateSecretId() {
        Room room2 = roomRepository.getOne(uuid);
        assertEquals(room.getSecretId(), room2.getSecretId());
    }
}
