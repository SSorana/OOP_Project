package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;


public class RoomServerCommunication {

    /**
     * Creates a connection with the server, posts the Room name and the Timeslot.
     * This method is executed when the button generate room is clicked in generate_room.
     *
     * @param roomName the name of the room
     * @param fromTime Timestamp of the start of the lecture
     * @param toTime Timestamp of the end of the lecture
     * @throws IOException invalid request
     */
    public static Room postRoom(String roomName,
                                Timestamp fromTime, Timestamp toTime) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", roomName);
        params.put("fromTime", fromTime);
        params.put("toTime", toTime);

        String res = Communication.postRequest("http://localhost:8080/room/create", params);
        return responseToRoom(res);
    }

    /**
     * Creates a connection with the server, gets de id of a room.
     * This method is executed when the button Connect is pressed in the log-in view
     *
     * @param roomId the id of the room.
     * @throws IOException invalid request
     */
    public static Room getRoom(String roomId) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", roomId);

        // get the response to the request
        String res = Communication.getRequest("http://localhost:8080/room/get", params);
        return responseToRoom(res);
    }

    /**
     * Method that joins an User to a Room.
     *
     * @param userId ID of the User
     * @param roomId ID of the Room
     * @throws IOException invalid request
     */
    public static void joinRoom(String userId, String roomId) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user", userId);
        params.put("room", roomId);

        String res = Communication.postRequest("http://localhost:8080/user/join", params);
    }

    /**
     * Requests the DB to create a User object.
     *
     * @param userName the name of the User
     * @return the User that was created by the DB
     * @throws IOException invalid request
     */
    public static User createUser(String userName) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", userName);

        String res = Communication.postRequest("http://localhost:8080/user/create", params);

        Gson gson = new Gson();
        return gson.fromJson(res, User.class);
    }

    /**
     * Takes an HTTP response and converts it to a room using gson.
     * @param response the response that we want to convert to room
     * @return the room object after it has been initiated
     */
    private static Room  responseToRoom(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, Room.class);
    }

    /**
     * Posts the speed to the server.
     * +1 if the user pressed faster
     * -1 if the user pressed slower.
     *
     * @param roomid the id of the roon the user is in
     * @param speed the speed to post.
     * @return the room with the new speed
     * @throws IOException invalid request
     */
    public static Room changeLectureSpeed(UUID roomid, int speed) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("roomid", roomid);
        params.put("speed", speed);

        String res = Communication.postRequest("http://localhost:8080/room/postspeed", params);
        return responseToRoom(res);
    }

    /**
     * Method that edits the timeframe of a Room.
     *
     * @param roomId ID of the Room
     * @param fromTime new starting time for the Room
     * @param toTime new ending time for the Room
     * @throws IOException invalid request
     */
    public static void editRoomTimes(UUID roomId,
                                     Timestamp fromTime, Timestamp toTime) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        params.put("fromTime", fromTime);
        params.put("toTime", toTime);

        Communication.postRequest("http://localhost:8080/room/editTimes", params);
    }
}


