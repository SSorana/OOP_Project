package nl.tudelft.oopp.demo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Answer;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.AnswerRepository;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired private AnswerRepository answerRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private UserRepository userRepository;

    /**
     * Creates a new answer.
     *
     * @param questionId ID of the question that is being answered
     * @param userId ID of the (moderator!) user that is answering the question
     * @param content Text content of the answer
     * @return Newly created Answer object
     */
    @PostMapping("/create")
    public Answer createAnswer(@RequestParam("question") UUID questionId,
                               @RequestParam("user") UUID userId,
                               @RequestParam("content") String content) {
        Optional<Question> question = questionRepository.findById(questionId);
        Optional<User> user = userRepository.findById(userId);
        LocalDateTime dateTime = LocalDateTime.now();
        Date timestamp = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        // Return HTTP/400 if the given question/user doesn't exist
        if (question.isEmpty() || user.isEmpty()) {
            throw new CreationException("This question/user doesn't exist");
        }

        Answer answer = new Answer(question.get(), user.get(), content, timestamp);
        return answerRepository.save(answer);
    }

    /**
     * Fetches an Answer by ID.
     *
     * @param id ID of the answer
     * @return The Answer object if it is found, or else "null"
     *     (which should also be JSON deserializable,
     *     so we can easily check the response on the client side)
     */
    @GetMapping("/get")
    public Optional<Answer> getAnswer(@RequestParam UUID id) {
        return answerRepository.findById(id);
    }

    /**
     * Returns all the Questions from a certain Room.
     *
     * @param room ID of the Room
     * @return a List of all the Questions in that room
     */
    @GetMapping("/getAll")
    public List<Answer> getAllQuestion(@RequestParam("room") UUID room) {

        List<Answer> result = new ArrayList<>();
        List<Answer> questionForRoom = answerRepository.findAll();

        for (Answer answer: questionForRoom) {
            if (answer.getQuestion().getRoom().getId().equals(room)) {
                result.add(answer);
            }
        }
        return result;
    }
}
