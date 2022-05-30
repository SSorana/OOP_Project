package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;
import nl.tudelft.oopp.demo.communication.AnsweredQuestionsServerCommunication;
import nl.tudelft.oopp.demo.communication.ModerationServerCommunication;
import nl.tudelft.oopp.demo.communication.QuestionServerCommunication;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.communication.VoteServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;

public class StudentViewController implements Initializable {

    @FXML
    private TextField questionBox;

    @FXML
    private Label lecturerName;

    @FXML
    private Label numberOfParticipants;

    @FXML
    private ListView<String> textList;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    @FXML
    private Label notificationForSpeed;

    private static final ObservableList<String> items = FXCollections.observableArrayList();

    private static String participants;
    private static String nameOfLecturer;
    List<Question> questionListFromServer;

    /**
     * initializes the controller.
     *
     * @param location used to resolve relative paths for the root object.
     * @param resources used to localize the root object.
     */
    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfParticipants.setText(participants);
        lecturerName.setText(nameOfLecturer);
        getQuestions();
    }

    /**
     * On Click of "Answered Questions" button it will load the answeredQuestions view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void goToAnsweredQuestions(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("/answeredQuestions.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }


    /**
     * On Click of "help" button it will load the help view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void goToHelpPage(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("/help_view.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }


    /**
     * On Click of "Answered Questions" button it will load the answeredQuestions view.
     *
     * @param event the event that will trigger the function (click button).
     */

    @FXML
    public void backToLogIn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Back to Login");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to leave this lecture?");
        alert.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {
                // generate the new scene
                Parent pane = null;
                try {
                    pane = FXMLLoader.load(getClass().getResource("/log-in.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene generateScene = new Scene(pane);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(generateScene);
                window.show();
            }
        });
    }

    /**
     * event that ask the question.
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void askQuestion() throws IOException {
        String questionContent = questionBox.getText();

        if (!(questionContent.isEmpty() || questionContent == null)) {
            QuestionServerCommunication.askQuestion(questionContent);
            questionBox.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Type question");
            alert.setContentText("Please put a question.");
            alert.show();
        }

        Double delayTime = 3.00;
        questionBox.setDisable(true);

        final Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(delayTime),
                        new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent actionEvent) {
                        questionBox.setDisable(false);
                        }
                }));
        animation.setCycleCount(1);
        animation.play();
    }


    /**
     * Get all the questions from the communication file.
     *
     */
    @FXML
    public void getQuestions() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            try {


                List<String> upvotedQuestion = new ArrayList<>();
                List<String> downVoted = new ArrayList<>();

                questionListFromServer = QuestionServerCommunication
                        .getAllQuestions(InitializationController.getRoom().getId());

                for (Question question: questionListFromServer) {
                    if (question.getDownVote() >= 1) {
                        downVoted.add(question.spamWriteToChat());
                    } else {
                        upvotedQuestion.add(question.writeToChat());
                    }
                }
                ObservableList<String> itemsOnScreen = FXCollections.observableArrayList();
                ObservableList<String> itemsOnServer = FXCollections.observableArrayList();
                itemsOnServer.addAll(upvotedQuestion);
                itemsOnServer.addAll(downVoted);
                itemsOnScreen = textList.getItems();

                if (!itemsOnScreen.equals(itemsOnServer)) {
                    textList.setItems(itemsOnServer);
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

    /**
     * Button is pushed if adds one upvote ot the number of upvotes and sends it to the server.
     *
     * @param event button for upvotes pressed
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    public void upvoteQuestion(ActionEvent event) throws IOException {
        try {
            int index = textList.getSelectionModel().getSelectedIndex();
            Question question = questionListFromServer.get(index);
            int upvotes = question.getUpVote();
            question.setUpVote(++upvotes);
            VoteServerCommunication.postVote(question);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Select Question to Upvote");
            alert.setContentText("Please select the question you want to upvote");
            alert.show();
            throw new IOException("No selected Question");
        }
    }

    /**
     * On button pressed it adds one vote to the downvotes of a question and sends it to the server.
     *
     * @param event press downvote button
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    public void downvoteQuestion(ActionEvent event) throws IOException {
        try {
            int index = textList.getSelectionModel().getSelectedIndex();
            Question question = questionListFromServer.get(index);
            int downvotes = question.getDownVote();
            question.setDownVote(++downvotes);
            VoteServerCommunication.postVote(question);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Select Question to Upvote");
            alert.setContentText("Please select the question you want to upvote");
            alert.show();
            throw new IOException("No selected Question");
        }
    }

    /**
     * Edit selected question.
     *
     * @param event press button select
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    public void userEditQuestion(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);

        try {
            int index = textList.getSelectionModel().getSelectedIndex();
            Question question = questionListFromServer.get(index);
            UUID userId = InitializationController.getUser().getId();
            String edit;

            TextInputDialog dialog = new TextInputDialog(question.getContent());
            dialog.setTitle("Edit Question");
            dialog.setHeaderText("EDIT QUESTION: " + question.getContent());
            dialog.setContentText("Edited version of the question: ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                edit = result.get();
                question.setContent(edit);
                ModerationServerCommunication.userEditQuestion(userId, question);
            } else {
                alert.setTitle("Please input text");
                alert.setContentText("Please input text");
                alert.show();
                throw new IOException("No text provide");
            }
        } catch (Exception e) {
            alert.setTitle("Select Question to Edit");
            alert.setContentText("Please select the question you want to edit");
            alert.show();
            e.printStackTrace();
        }
    }

    /**
     * Delete selected question.
     *
     * @param event press delete button
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    public void userDeleteQuestion(ActionEvent event) throws IOException {
        try {
            int index = textList.getSelectionModel().getSelectedIndex();
            Question question = questionListFromServer.get(index);
            ModerationServerCommunication.userRemoveQuestion(
                    InitializationController.getUser().getId(), question.getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Select Question to Delete");
            alert.setContentText("Please select the question you want to delete");
            alert.show();
            e.printStackTrace();
        }
    }

    /**
     * Initializing the lecturerName and number of participants in the room.
     * @param lecturerName the lecturerName
     * @param size the num of participants in the room
     */
    public static void showContent(String lecturerName, String size) {
        participants = size;
        nameOfLecturer = lecturerName;
    }

    /**
     * increments the speed in room by one.
     * @param event click of the faster button
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    @FXML
    public void upSpeed(ActionEvent event) throws IOException {
        UUID roomid = InitializationController.getRoom().getId();
        RoomServerCommunication.changeLectureSpeed(roomid, 1);
    }

    /**
     * decrements the speed in room by one.
     * @param event click of the slower button
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    @FXML
    public void downSpeed(ActionEvent event) throws IOException {
        UUID roomid = InitializationController.getRoom().getId();
        RoomServerCommunication.changeLectureSpeed(roomid, -1);
    }
}

