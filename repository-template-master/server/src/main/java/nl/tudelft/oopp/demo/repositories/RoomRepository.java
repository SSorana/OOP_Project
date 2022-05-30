package nl.tudelft.oopp.demo.repositories;

import java.util.Optional;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Query("SELECT r FROM Room r WHERE r.secretId=?1")
    Optional<Room> getAllBySecretId(UUID secretId);
}
