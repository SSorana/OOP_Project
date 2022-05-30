package nl.tudelft.oopp.demo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.UUID;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.QuestionComparator;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
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
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new room.
     *
     * @param name name of the room/course
     * @return newly created Room object
     */
    @PostMapping("/create")
    public Room createRoom(@RequestParam String name,
                           @RequestParam Timestamp fromTime,
                           @RequestParam Timestamp toTime) {
        Room room = new Room(name, fromTime, toTime, 0);
        return roomRepository.save(room);
    }

    /**
     * Fetches a Room by ID.
     *
     * @param id ID of the room
     * @return The Room object if it is found, or else "null"
     *     (which should also be json deserializable,
     *     so we can easily check the response on the client side)
     */
    @GetMapping("/get")
    public Room getRoom(@RequestParam UUID id) {

        List<Room> listRoom = roomRepository.findAll();
        List<User> userList = userRepository.findAll();
        List<User> resultList = new ArrayList<>();

        for (Room room: listRoom) {
            if (room.getId().equals(id) || room.getSecretId().equals(id)) {
                for (User user: userList) {
                    for (Room room1: user.getRooms()) {
                        if (room1.getId().equals(room.getId())) {
                            resultList.add(user);
                        }
                    }

                }

                room.setUsers(resultList);
                if (room.getSpeed() <= -1 || room.getSpeed() >= 1) {
                    int originalSpeed =  room.getSpeed();
                    room.setSpeed(0);
                    roomRepository.save(room);
                    if (room.getSpeed() <= originalSpeed) {
                        room.setSpeed(-1);
                    }
                    if (room.getSpeed() >= originalSpeed) {
                        room.setSpeed(1);
                    }
                    return room;
                }
                return room;
            }
        }
        throw new CreationException("Room doesn't exist.");
    }

    /**
     * Method that returns all the participants of a specific Room.
     *
     * @param id the ID of the Room
     * @return a list of the Users that are registered in the Room
     */
    @GetMapping("/getAll/{id}")
    public List<User> getAllUsers(@PathVariable UUID id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isEmpty()) {
            throw new CreationException("User doesn't exist.");
        }

        Room room = optionalRoom.get();
        return room.getUsers();
    }

    /**
     * Method that sorts all the Questions in a specific Room
     * according to their priority.
     *
     * @param roomId ID of the Room
     * @return a prioritized List of all the Questions in the chat
     */
    @GetMapping("/refreshFeed/{roomId}")
    public List<Question> refreshFeed(@PathVariable UUID roomId) {
        // retrieve all the Questions in the chat
        List<Question> questions = questionRepository.getAllQuestions(roomId.toString());

        // add the Questions to the PriorityQueue
        PriorityQueue<Question> pq = new PriorityQueue<>(new QuestionComparator());
        pq.addAll(questions);

        // add the sorted Questions to a new List and return it
        List<Question> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll());
        }

        return result;
    }

    /**
     * Method that returns all the answered Questions in a certain Room.
     * 
     * @param roomId ID of the Room
     * @return a List of all the answered Questions in the Room.
     */
    @GetMapping("/answeredQuestions/{roomId}")
    public List<Question> getAnsweredQuestions(@PathVariable UUID roomId) {
        return questionRepository.getAnsweredQuestions(roomId.toString());
    }


    /**
     * gets the speed from the client and adds it to the exsiting speed on the server.
     * @param roomid the id of the room
     * @param speed the speed inputed by the client
     * @return the room with the new speed
     */
    @PostMapping("/postspeed")
    public Room postSpeed(@RequestParam("roomid") UUID roomid,
                          @RequestParam("speed") int speed) {
        List<Room> listRoom = roomRepository.findAll();

        for (Room room: listRoom) {
            if (room.getId().equals(roomid) || room.getSecretId().equals(roomid)) {
                int result = room.getSpeed();
                room.setSpeed(result + speed);
                roomRepository.save(room);

                if (room.getSpeed() <= -1 || room.getSpeed() >= 1) {
                    room.setSpeed(1);
                    return room;
                }
                return room;
            }
        }
        throw new CreationException("Room doesn't exist.");
    }

    /**
     * Endpoint that updates the timeframe of a Room.
     *
     * @param roomId ID of the Room
     * @param fromTime new starting time for the Room
     * @param toTime new ending time for the Room
     */
    @PostMapping("/editTimes")
    public void editTimes(@RequestParam("roomId") UUID roomId,
                          @RequestParam("fromTime") Timestamp fromTime,
                          @RequestParam("toTime") Timestamp toTime) {
        Room room = roomRepository.getOne(roomId);
        room.setFromTime(fromTime);
        room.setToTime(toTime);
        roomRepository.save(room);
    }
}
