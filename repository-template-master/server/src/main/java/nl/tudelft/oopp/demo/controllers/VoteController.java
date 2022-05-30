package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.Vote;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired QuestionRepository questionRepository;
    @Autowired UserRepository userRepository;
    @Autowired VoteRepository voteRepository;

    /**
     * Creates a new vote.
     *
     * @param questionId ID of the question the user is voting on
     * @param userId ID of the user who is voting
     * @param difference +1 or -1 for upvote/downvote
     * @return Newly created Vote object
     */
    @PostMapping("/create")
    public Vote createVote(@RequestParam("question") UUID questionId,
                           @RequestParam("user") UUID userId,
                           @RequestParam("difference") int difference) {
        Optional<Question> question = questionRepository.findById(questionId);
        Optional<User> user = userRepository.findById(userId);

        // Return HTTP/400 if the given question/user doesn't exist
        if (question.isEmpty() || user.isEmpty()) {
            throw new CreationException("This question/user doesn't exist");
        }

        Question question1 = question.get();
        if (difference == 1) {
            question1.setUpVote(question1.getUpVote() + 1);
        } else {
            question1.setDownVote(question1.getDownVote() + 1);
        }
        questionRepository.save(question1);

        Vote vote = new Vote(question.get(), user.get(), difference);
        return voteRepository.save(vote);
    }

    /**
     * Fetches a Vote by ID.
     *
     * @param id ID of the vote
     * @return The Vote object if it is found, or else "null"
     *     (which should also be JSON deserializable,
     *     so we can easily check the response on the client side)
     */
    @GetMapping("/get/{id}")
    public Optional<Vote> getVote(@PathVariable String id) {
        return voteRepository.findById(UUID.fromString(id));
    }

    /**
     * Fetches all the Votes for a certain question.
     *
     * @param questionId ID of the question
     * @return A list of all the Votes for the question whose ID was provided
     */
    @GetMapping("/getAll/{questionId}")
    public List<Vote> getAllVotes(@PathVariable String questionId) {
        return voteRepository.findByQuestionId(questionId);
    }
}
