package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.demo.communication.AnsweredQuestionsServerCommunication;
import nl.tudelft.oopp.demo.data.Answered;


public class AnsweredQuestionsController implements Initializable {

    @FXML
    private ListView answeredList;

    /**
     * initializes the controller.
     *
     * @param location used to resolve relative paths for the root object.
     * @param resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showQuestions();
    }


    /**
     * On arrow-back button click, loads the student_view page.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void goBackToUserViewController(ActionEvent event) throws IOException {
        if (InitializationController.getUser().getRole().equals("Student")) {
            Parent pane = FXMLLoader.load(getClass().getResource("/student_view.fxml"));
            Scene generateScene = new Scene(pane);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(generateScene);
            window.show();
        } else {
            Parent pane = FXMLLoader.load(getClass().getResource("/lecturer_view.fxml"));
            Scene generateScene = new Scene(pane);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(generateScene);
        }
    }

    /**
     * Show all questions retrieved from the database on the screen.
     *
     */
    @FXML
    public void showQuestions() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            try {
                List<Answered> questionListFromServer;
                UUID room = InitializationController.getRoom().getId();

                questionListFromServer = AnsweredQuestionsServerCommunication
                        .getAllAnswers(room);

                ObservableList<String> itemsOnScreen = FXCollections.observableArrayList();
                ObservableList<String> itemsOnServer = FXCollections.observableArrayList();

                for (Answered question: questionListFromServer) {
                    itemsOnServer.add(question.showOnWindow());
                }

                itemsOnScreen = answeredList.getItems();

                if (!itemsOnScreen.equals(itemsOnServer)) {
                    answeredList.setItems(itemsOnServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
