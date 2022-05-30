package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends JpaRepository<Ban, UUID> {
    /**
     * Query that returns a list of all the Bans for a certain User.
     * @param userId ID of the User
     * @return All Bans for the User whose ID was provided
     */
    @Query("SELECT b FROM Ban b JOIN User u ON b.user=u.id WHERE b.user=?1")
    List<Ban> findBansByUserId(UUID userId);


    /**
     * Query that returns a list of all the Bans for a certain IP address.
     * @param ip IP to look up
     * @return All Bans for this IP
     */
    @Query("SELECT b FROM Ban b WHERE b.ip=?1")
    List<Ban> findBansByIP(String ip);

    /**
     * Query that returns a list of all the Bans for a certain IP address in a
     * specific room.
     * @param ip the IP we want ot check
     * @param roomId the Id of the room where we want to check if the user is banned
     * @return all bans for this IP in this room
     */
    @Query("SELECT b FROM Ban b WHERE b.ip=?1 AND b.room.id=?2")
    List<Ban> findBansByIpAndRoomId(String ip, UUID roomId);

}
