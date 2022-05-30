package nl.tudelft.oopp.demo.controllers;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Ban;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BanRepository;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/moderation")
public class ModerationController {
    @Autowired private BanRepository banRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private RoomRepository roomRepository;

    /**
     * Ban a user from asking questions in a certain room.
     *
     * @param modID secret moderator ID of the room, used for "authentication"
     * @param userID the id of the user to ban
     * @param roomID the id of the room to ban this user from
     * @param duration time to ban this user (0 for permaban)
     * @throws Exception if user didn't send a valid secret ID with the request
     */
    @PostMapping("/user/ban")
    public void timeoutUser(@RequestParam UUID modID,
                            @RequestParam UUID userID,
                            @RequestParam UUID roomID,
                            @RequestParam int duration) throws Exception {

        Optional<User> optionalUser = userRepository.findById(userID);
        Optional<Room> optionalRoom = roomRepository.findById(roomID);
        if (optionalUser.isEmpty() || optionalRoom.isEmpty()) {
            throw new CreationException("User or Room don't exist");
        }
        User user = optionalUser.get();
        Room room = optionalRoom.get();

        if (!modID.equals(room.getSecretId())) {
            throw new Exception("Not a valid moderator ID");
        }

        String ip = user.getLastIP();
        Ban ban = new Ban(user, room, duration, ip);
        banRepository.save(ban);
    }


    /**
     * Remove a student's question (in case of spam etc).
     *
     * @param modID secret moderator ID of the room, used for "authentication"
     * @param questionID  the ID of the question to remove
     * @throws Exception if user didn't send a valid secret ID with the request
     */
    @PostMapping("/question/remove")
    public void removeQuestion(@RequestParam UUID modID,
                               @RequestParam UUID questionID) throws Exception {

        Question question = checkQuestionExistsAndValidModID(modID, questionID);

        questionRepository.delete(question);
    }

    /**
     * Edit a student’s question (to clarify for example).
     *
     * @param modID secret moderator ID of the room, used for "authentication"
     * @param questionID ID of the question to edit
     * @param content new question content
     * @throws Exception if user didn't send a valid secret ID with the request
     */
    @PostMapping("/question/edit")
    public void editQuestion(@RequestParam UUID modID,
                             @RequestParam UUID questionID,
                             @RequestParam String content) throws Exception {

        Question question = checkQuestionExistsAndValidModID(modID, questionID);

        question.setContent(content);
        questionRepository.save(question);
    }

    /**
     * Check if a question exists and ModID is correct. If true return the question.
     * @param modID the ID of the mod we want to confirm
     * @param questionID the ID of the question to be checked
     * @return returns the question if the questionID and modID are correct
     * @throws Exception if the moderator ID provided it incorrect
     */
    private Question checkQuestionExistsAndValidModID(
            @RequestParam UUID modID,
            @RequestParam UUID questionID) throws Exception {

        Optional<Question> optionalQuestion = questionRepository.findById(questionID);
        if (optionalQuestion.isEmpty()) {
            throw new CreationException("Question doesn't exist");
        }
        Question question = optionalQuestion.get();

        if (!modID.equals(question.getRoom().getSecretId())) {
            throw new Exception("Not a valid moderator ID");
        }

        return question;
    }
}
