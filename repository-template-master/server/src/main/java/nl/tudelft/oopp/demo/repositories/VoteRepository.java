package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    /**
     * Query that returns a list of all the Votes for a certain Question.
     * @param questionId ID of the Question
     * @return All Votes for the Question whose ID was provided
     */
    @Query("SELECT v.id FROM Vote v JOIN Question q ON v.question=q.id WHERE v.question=?1")
    List<Vote> findByQuestionId(String questionId);
}
