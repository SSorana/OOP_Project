package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import nl.tudelft.oopp.demo.data.Question;

public class ModerationServerCommunication {

    /**
     * Edits the content of a question on the server.
     * @param modId the secretId that only mods should have access to
     * @param question the question to be edited
     * @throws IOException invalid request
     */
    public static void editQuestion(UUID modId, Question question) throws IOException {
        UUID questionID = question.getId();
        String content = question.getContent();

        HashMap<String, Object> params = new HashMap<>();
        params.put("modID", modId);
        params.put("questionID", questionID);
        params.put("content", content);

        Communication.postRequest("http://localhost:8080/moderation/question/edit", params);
    }

    /**
     * Removes a question from the server.
     * @param modId the secretId that only mods should have access to
     * @param question the question to be removed
     * @throws IOException invalid request
     */
    public static void removeQuestion(UUID modId, Question question) throws IOException {
        UUID questionID = question.getId();

        HashMap<String, Object> params = new HashMap<>();
        params.put("modID", modId);
        params.put("questionID", questionID);

        Communication.postRequest("http://localhost:8080/moderation/question/remove", params);
    }

    /**
     * Timeout a user for a duration of time or indefinitely,
     * accounted by the Ban entity on the Server.
     * @param modId the secretId that only mods should have access to
     * @param userId the ID of the user that needs to get timed out
     * @param roomId the  ID of the room the user is timed out in
     * @param duration the duration of the timeout
     * @throws IOException invalid request
     */
    public static void timeoutUser(UUID modId, UUID userId, UUID roomId, int duration)
            throws IOException {

        HashMap<String, Object> params = new HashMap<>();
        params.put("modID", modId);
        params.put("userID", userId);
        params.put("roomID", roomId);
        params.put("duration", duration);

        Communication.postRequest("http://localhost:8080/moderation/user/ban", params);
    }

    /**
     * User can edit the content of the questions they have asked.
     * @param userId the Id of the user who wants to make an edit
     * @param question the question that needs to be edited
     * @throws IOException invalid request
     */
    public static void userEditQuestion(UUID userId, Question question) throws IOException {
        UUID questionId = question.getId();
        String content = question.getContent();

        HashMap<String, Object> params = new HashMap<>();
        params.put("userID", userId);
        params.put("questionID", questionId);
        params.put("content", content);

        Communication.postRequest("http://localhost:8080/user/question/edit", params);
    }

    /**
     * User can remove the questions that they have asked.
     * @param userId the Id of the user who want to remove the question
     * @param questionId the Id of the question that needs to be removed
     * @throws IOException invalid request
     */
    public static void userRemoveQuestion(UUID userId, UUID questionId) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userID", userId);
        params.put("questionID", questionId);

        Communication.postRequest("http://localhost:8080/user/question/remove", params);
    }
}
