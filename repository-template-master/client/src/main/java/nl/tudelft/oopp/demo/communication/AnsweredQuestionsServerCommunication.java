package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nl.tudelft.oopp.demo.data.Answered;

public class AnsweredQuestionsServerCommunication {

    /**
     * Retrieves all (answered) Questions from a certain Room.
     *
     * @param room ID of the Room
     * @return a List of all the Questions that fit the query
     * @throws IOException invalid request
     */
    public static List<Answered> getAllAnswers(UUID room) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("room", room);

        String res = Communication.getRequest("http://localhost:8080/answer/getAll", params);

        Gson gson = new Gson();
        return gson.fromJson(res, new TypeToken<List<Answered>>(){}.getType());
    }

    /**
     * Mark a question as answered and return the answer.
     *
     * @param questionId the Id of the question that has been answered
     * @param userId the Id of the user who has provided the answer
     * @param content the answer itself
     * @throws IOException invalid request
     */
    public static Answered markAnswered(
            UUID questionId, UUID userId, String content) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("content", content);
        params.put("user", userId);
        params.put("question", questionId);

        String res = Communication.postRequest("http://localhost:8080/answer/create", params);
        Gson gson = new Gson();
        return gson.fromJson(res, Answered.class);
    }
}
