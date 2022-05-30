package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Creates a new user.
     * TODO: should we make usernames unique?
     *
     * @param username Username for the new user
     * @return Newly created User object
     */
    @PostMapping("/create")
    public User createUser(HttpServletRequest req, @RequestParam String username) {
        // This assumes there's nothing between the client and server
        String addr = req.getRemoteAddr();
        User user = new User(username, addr);
        return userRepository.save(user);
    }

    /**
     * Fetches a User by ID.
     *
     * @param id ID of the user
     * @return The User object if it is found, or else "null"
     *     (which should also be json deserializable,
     *     so we can easily check the response on the client side)
     */
    @GetMapping("/get")
    public Optional<User> getUser(@RequestParam UUID id) {
        return userRepository.findById(id);
    }

    /**
     * Method that joins a certain User to a certain Room.
     *
     * @param userId the ID of the User
     * @param roomId the ID of the Room
     */
    @PostMapping("/join")
    public void joinRoom(@RequestParam("user") UUID userId, @RequestParam("room") UUID roomId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        // check if both the User and the Room objects exist
        if (optionalUser.isEmpty() || optionalRoom.isEmpty()) {
            throw new CreationException("User or Room doesn't exist.");
        }

        // add the User and Room to each other's lists
        User user = optionalUser.get();
        Room room = optionalRoom.get();
        user.getRooms().add(room);
        room.getUsers().add(user);
        userRepository.save(user);
        roomRepository.save(room);
    }

    /**
     * Method that returns all the Rooms that a User is registered in.
     *
     * @param id the ID of the User
     * @return a list of the Rooms that the User is registered in
     */
    @GetMapping("/getAll")
    public List<Room> getAllRooms(@RequestParam UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new CreationException("User doesn't exist.");
        }

        User user = optionalUser.get();
        return user.getRooms();
    }

    /**
     * User can remove their own question (sent by mistake / already answered).
     * @param userID the UUID of the user who wants his/her removed
     * @param questionID the UUID of the question that the user sent
     * @throws Exception if the user want to delete a question that he/she did not send
     */
    @PostMapping("/question/remove")
    public void removeQuestion(@RequestParam UUID userID,
                               @RequestParam UUID questionID) {

        Question question = checkUserQuestionPairByID(userID, questionID);
        questionRepository.delete(question);
    }

    /**
     * User can edit their own question (typos).
     * @param userID userID the UUID of the user who wants his/her removed
     * @param questionID the UUID of the question that the user sent
     * @param content the new / edited question content
     * @throws Exception if the user want to edit a question that he/she did not send
     */
    @PostMapping("/question/edit")
    public void editQuestion(@RequestParam UUID userID,
                             @RequestParam UUID questionID,
                             @RequestParam String content) {

        Question question = checkUserQuestionPairByID(userID, questionID);
        question.setContent(content);
        questionRepository.save(question);
    }

    /**
     * Checks if a question - user pair match by comparing ids, if true return question.
     * @param userID userID the UUID of the user to check
     * @param questionID the UUID of the question to check
     * @return the question if it is asked by the provided user
     * @throws Exception if either the user / question don't exist or they don't match
     */
    private Question checkUserQuestionPairByID(
            @RequestParam UUID userID,
            @RequestParam UUID questionID) throws CreationException {

        Optional<User> optionalUser = userRepository.findById(userID);
        Optional<Question> optionalQuestion = questionRepository.findById(questionID);

        if (optionalUser.isEmpty()) {
            throw new CreationException("User doesn't exist.");
        }
        if (optionalQuestion.isEmpty()) {
            throw new CreationException("Question doesn't exist.");
        }
        User user = optionalUser.get();
        Question question = optionalQuestion.get();

        if (!(user.getId().equals(question.getUser().getId()))) {
            throw new CreationException("User and question don't match");
        }

        return question;
    }
}
