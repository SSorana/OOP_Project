package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nl.tudelft.oopp.demo.controllers.InitializationController;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;


public class QuestionServerCommunication {

    /**
     * Sends a question to be stored in the database and
     * creates a client-side instance based on the response.
     * @param questionContent the content of the question asked
     * @return returns a new question instance
     * @throws IOException invalid request
     */
    public static Question askQuestion(String questionContent) throws IOException {
        User user = InitializationController.getUser();
        Room room = InitializationController.getRoom();

        System.out.println(questionContent);

        HashMap<String, Object> params = new HashMap<>();
        params.put("user", user.getId());
        params.put("room", room.getId());
        params.put("content", questionContent);

        String res = Communication.postRequest("http://localhost:8080/question/create", params);

        Gson gson = new Gson();
        return gson.fromJson(res, Question.class);
    }

    /**
     * gets all the questions for a specific Room.
     *
     * @param room the id of the room you want
     * @return the list of question based on the roomid
     * @throws IOException invalid request
     */
    public static List<Question> getAllQuestions(UUID room) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("room", room);

        String res = Communication.getRequest("http://localhost:8080/question/getAll", params);

        Gson gson = new Gson();
        return gson.fromJson(res, new TypeToken<List<Question>>(){}.getType());
    }

    /**
     * Retrieves a specific question from the server.
     * @param questionId the id of the question we want to receive
     * @return the question that has this questionId
     * @throws IOException Invalid request
     */
    public static Question getQuestion(String questionId) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", questionId);

        String res = Communication.getRequest("http://localhost:8080/question/get", params);

        Gson gson = new Gson();
        return gson.fromJson(res, Question.class);
    }
}
