package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Vote;


public class VoteServerCommunication {

    /**
     * Send votes to the server.
     *
     * @param sentQuestion gets question from upvote downvote method.
     * @return returns the vote from server.
     * @throws IOException invalid request
     */
    public static Vote postVote(Question sentQuestion) throws IOException {

        UUID question = sentQuestion.getId();
        UUID user = sentQuestion.getUser().getId();
        int difference = sentQuestion.getUpVote() - sentQuestion.getDownVote();

        HashMap<String, Object> params = new HashMap<>();
        params.put("question", question);
        params.put("user", user);
        params.put("difference", difference);

        String res = Communication.postRequest("http://localhost:8080/vote/create", params);
        Gson gson = new Gson();
        return gson.fromJson(res, Vote.class);
    }
}
