package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;
import nl.tudelft.oopp.demo.communication.AnsweredQuestionsServerCommunication;
import nl.tudelft.oopp.demo.communication.QuestionServerCommunication;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;


public class ZenModeController implements Initializable {

    @FXML
    private ListView questionList;

    /**
     * initializes the controller.
     *
     * @param location used to resolve relative paths for the root object.
     * @param resources used to localize the root object.
     */
    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getQuestions();
        getSpeed();
    }

    /**
     * On click of "arrow-back" button it will load the lecturer_view view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void goBackToLecturerView(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("/lecturer_view.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }

    /**
     * Retrieves all the question from the communication file.
     */
    @FXML
    public void getQuestions() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            try {
                ObservableList<String> itemsOnScreen = FXCollections.observableArrayList();
                ObservableList<String> itemsOnServer = FXCollections.observableArrayList();
                List<Question> questionListFromServer = QuestionServerCommunication.getAllQuestions(
                    InitializationController.getRoom().getId());

                for (Question question: questionListFromServer) {
                    itemsOnServer.add(question.writeToChat());
                }

                itemsOnScreen = questionList.getItems();

                if (!itemsOnScreen.equals(itemsOnServer)) {
                    questionList.setItems(itemsOnServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    /**
     * Each second, takes the int speed from the room in server
     * (based on the difference between the number of faster and slower in student view)
     * if that number is bigger than +-10 it resets it and alerts the teacher to go
     * faster/slower.
     */
    public void getSpeed() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            try {
                Room room = RoomServerCommunication.getRoom(
                        InitializationController.getRoom().getId().toString());

                if (room.getSpeed() >= 1 || room.getSpeed() <= -1) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    if (room.getSpeed() == 1) {
                        alert.setTitle("You are going to slow");
                        alert.setContentText("Slow Down");
                    }
                    if (room.getSpeed() == -1) {
                        alert.setTitle("You are going to fast");
                        alert.setContentText("Go Faster");
                    }
                    room.setSpeed(0);
                    alert.setHeaderText(null);

                    alert.show();
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Server Down");
                alert.setContentText("Something went wrong on the server please refresh");
                alert.show();
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
