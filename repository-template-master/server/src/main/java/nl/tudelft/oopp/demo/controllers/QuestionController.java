package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import nl.tudelft.oopp.demo.entities.Ban;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.QuestionComparator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BanRepository;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/question")
public class  QuestionController {
    @Autowired private UserRepository userRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private BanRepository banRepository;

    // TODO: THIS IS BROKEN
    private List<Question> questionList = new ArrayList<Question>();

    /**
     * Creates a new question.
     *
     * @param userId ID of the user asking the question
     * @param roomId ID of the room where the question is being asked
     * @param content Text content of the question
     * @return Newly created Question object
     *
     */
    @PostMapping("/create")
    public Question createQuestion(HttpServletRequest req,
                                   @RequestParam("user") UUID userId,
                                   @RequestParam("room") UUID roomId,
                                   @RequestParam("content") String content) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Room> room = roomRepository.findById(roomId);

        // Return HTTP/400 if the given user/room doesn't exist
        if (user.isEmpty() || room.isEmpty()) {
            throw new CreationException("This user/room doesn't exist");
        }

        // If the user's IP has been banned in a specific room, tell them to GTFO
        List<Ban> bans = banRepository.findBansByIpAndRoomId(req.getRemoteAddr(), roomId);
        if (!bans.isEmpty()) {
            for (Ban ban : bans) {
                Date expires = ban.getExpiration();
                Date current = new Date(System.currentTimeMillis());
                if (current.compareTo(expires) >= 0) {
                    banRepository.delete(ban);
                } else {
                    throw new CreationException("You got banned mate, outta here");
                }
            }
        }

        Question question = new Question(user.get(), room.get(), content);
        questionList.add(question);
        return questionRepository.save(question);
    }

    /**
     * Fetches a Question by ID.
     * @param id ID of the question
     * @return The Question object if it is found, or else "null"
     *     (which should also be json deserializable,
     *     so we can easily check the response on the client side)
     */
    @GetMapping("/get")
    public Optional<Question> getQuestion(@RequestParam UUID id) {
        return questionRepository.findById(id);
    }


    /**
     * Returns all the Questions from a certain Room.
     *
     * @param room ID of the Room
     * @return a List of all the Questions in that room
     */
    @GetMapping("/getAll")
    public List<Question> getAllQuestion(@RequestParam("room") UUID room) {


        List<Question> result = new ArrayList<>();
        List<Question> questionForRoom = questionRepository.findAll();

        for (Question question: questionForRoom) {
            if (question.getRoom().getId().equals(room)) {
                result.add(question);
            }
        }

        result.sort(new QuestionComparator());
        return result;
    }
}
