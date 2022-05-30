package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.util.Map;
import javafx.scene.control.Alert;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Communication {

    private static final OkHttpClient client = new OkHttpClient();


    /**
     * Sends a POST request with a HashMap of parameters to a specified endpoint.
     *
     * @param url URL of the API endpoint
     * @param params HashMap with all of the POST parameters
     * @return API response
     * @throws IOException When things go boom
     */
    public static String postRequest(String url, Map<String, Object> params) throws IOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        // Add every value in the hashmap as a POST form parameter
        for (Map.Entry<String, Object> param : params.entrySet()) {
            formBodyBuilder.add(param.getKey(), String.valueOf(param.getValue()));
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBodyBuilder.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }


    /**
     * Sends a GET request with a HashMap of parameters to a specified endpoint.
     *
     * @param url URL of the API endpoint
     * @param params HashMap with all of the GET parameters
     * @return API response
     * @throws IOException When things go boom
     */
    public static String getRequest(String url, Map<String, Object> params) throws IOException {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();

        // Add every value in the hashmap as a query parameter
        for (Map.Entry<String, Object> param : params.entrySet()) {
            httpUrlBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));
        }

        Request request = new Request.Builder()
                .url(httpUrlBuilder.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

}
