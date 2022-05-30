package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    /**
     * Query that returns all the Questions from a specific Room.
     *
     * @param roomId ID of the Room
     * @return a list of all the Questions in that specific Room
     */
    @Query("SELECT q.id FROM Question q "
            + "JOIN Room r ON q.room=r.id "
            + "WHERE r.id=?1 ORDER BY q.upVote-q.downVote DESC")
    List<Question> getAllQuestions(String roomId);

    /**
     * Query that returns all the answered Questions from a certain Room.
     *
     * @param roomId ID of the Room
     * @return a list of all the answered Questions in the Room.
     */
    @Query("SELECT q FROM Question q "
            + "JOIN Answer a ON q.id=a.question "
            + "JOIN Room r ON q.room=r.id "
            + "WHERE r.id=?1 ORDER BY q.upVote-q.downVote DESC")
    List<Question> getAnsweredQuestions(String roomId);
}
