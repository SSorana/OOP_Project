package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import nl.tudelft.oopp.demo.data.Answered;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;

public class LecturerViewController implements Initializable {

    @FXML
    private ListView<String> lecturerChat;

    @FXML
    private TextField chatBox;

    @FXML
    private TextField moderatorLink;

    @FXML
    private TextField studentLink;

    @FXML
    private Label roomNameLabel;

    private static String roomNameLabels;
    private static String studentLinks;
    private static String moderatorLinks;
    private static final ObservableList<String> items = FXCollections.observableArrayList();
    private List<Question> questionListFromServer = new ArrayList<>();


    /**
     * initializes the controller.
     *
     * @param location used to resolve relative paths for the root object.
     * @param resources used to localize the root object.
     */
    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moderatorLink.setText(moderatorLinks);
        studentLink.setText(studentLinks);
        roomNameLabel.setText(roomNameLabels);
        getQuestions();
        getSpeed();
    }


    /**
     * On Click of "Zen" button it will load the zenMode view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    @FXML
    public void goToZenMode(ActionEvent event) throws IOException {
        // generate the new scene
        Parent pane = FXMLLoader.load(getClass().getResource("/zenMode.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }

    /**
     * On Click of "Answered Questions" button it will load the answeredQuestions view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    @FXML
    public void goToAnsweredQuestions(ActionEvent event) throws IOException {
        // generate the new scene
        Parent pane = FXMLLoader.load(getClass().getResource("/answeredQuestions.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }

    /**
     * Sends a question to the server and cleans the text box.
     *
     * @param event on key press enter in textField.
     * @throws IOException if no text is put.
     */
    @FXML
    public void sendQuestion(ActionEvent event) throws IOException {
        String questionContent = chatBox.getText();
        if (!(questionContent.isEmpty() || questionContent == null)) {
            QuestionServerCommunication.askQuestion(questionContent);
            chatBox.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Type question");
            alert.setContentText("Please put a question.");
            alert.show();
        }
    }


    /**
     * On click of "Close" button it will close the lecturer View.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    @FXML
    public void closeTheApplication(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Lecture");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this lecture?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    makeRoomInaccessible();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.close();
            }
        });
    }

    /**
     * Method that makes sure Users can not access a Room after it has been closed.
     */
    private void makeRoomInaccessible() throws IOException {
        Room room = InitializationController.getRoom();
        Timestamp newFromTime = Timestamp.valueOf("1970-01-01 00:00:00");
        Timestamp newToTime = Timestamp.valueOf("1970-01-01 00:00:00");

        RoomServerCommunication.editRoomTimes(room.getId(), newFromTime, newToTime);
    }

    /**
     * Method that determines the role of a User.
     *
     * @return the role of said User
     */
    private String determineRole() {
        User user = InitializationController.getUser();
        return user.getRole();
    }

    /**
     * On click of "Mark as answered" button it will mark a question as
     * answered and send an answer to the server.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    @FXML
    public void markAsAnswered(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);

        try {
            int questionIndex = lecturerChat.getSelectionModel().getSelectedIndex();
            Question questionSelected = questionListFromServer.get(questionIndex);
            String answeredText = chatBox.getText();

            if (answeredText.isBlank()) {
                alert.setTitle("Input an answer");
                alert.setContentText("Please input a answer");
                alert.show();
                throw new IOException("No Answer");
            }

            Answered answer = AnsweredQuestionsServerCommunication.markAnswered(
                    questionSelected.getId(),
                    InitializationController.getUser().getId(),
                    answeredText);

        } catch (Exception e) {
            alert.setTitle("Select a question");
            alert.setContentText("Please select a question.");
            alert.show();
            throw new IOException("No question selected");
        }
    }


    /**
     * Get a list of all the questions from the communication file.
     * add then to the list and shows them on the screen
     */
    @FXML
    public void getQuestions() {
        //Code duplication with the timeline
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

                itemsOnScreen = lecturerChat.getItems();

                if (!itemsOnScreen.equals(itemsOnServer)) {
                    lecturerChat.setItems(itemsOnServer);
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
     * On Click of "Export" button it will load the export view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    public void goToExportQuestions(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("/exportQuestions.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }

    /**
     * Edit the question of a user.
     *
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    public void editQuestion() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);

        try {
            int index = lecturerChat.getSelectionModel().getSelectedIndex();
            Question question = questionListFromServer.get(index);
            String edit;

            TextInputDialog dialog = new TextInputDialog(question.getContent());
            dialog.setTitle("Edit Question");
            dialog.setHeaderText("EDIT QUESTION: " + question.getContent());
            dialog.setContentText("Edited version of the question: ");

            Optional<String> result = dialog.showAndWait();

            if (result.isEmpty()) {
                alert.setTitle("Please input text");
                alert.setContentText("Please input text");
                alert.show();
                throw new IOException("No text provide");
            }

            if (result.isPresent()) {
                edit = result.get();
                question.setContent(edit);
                ModerationServerCommunication.editQuestion(
                        question.getRoom().getSecretId(), question);
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
    public void deleteQuestion(ActionEvent event) throws IOException {
        try {
            int index = lecturerChat.getSelectionModel().getSelectedIndex();
            Question question = questionListFromServer.get(index);
            ModerationServerCommunication
                    .removeQuestion(question.getRoom().getSecretId(), question);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Select Question to Edit");
            alert.setContentText("Please select the question you want to delete");
            alert.show();
            e.printStackTrace();
        }
    }

    /**
     * Ban a user (currently indefinite).
     *
     * @param event press the ban user button
     * @throws IOException whenever an input or output operation is failed or misinterpreted.
     */
    public void banUser(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);

        try {
            int index = lecturerChat.getSelectionModel().getSelectedIndex();
            Question question = questionListFromServer.get(index);
            UUID userId = question.getUser().getId();
            Room room = question.getRoom();
            int duration;

            TextInputDialog dialog = new TextInputDialog("banDuration");
            dialog.setTitle("Ban");
            dialog.setHeaderText("BAN USER: " + question.getUser().getUsername()
                    + ".\n\n*Put 0 for Permaban");
            dialog.setContentText("Duration of Ban (in minutes): ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    duration = Integer.parseInt(result.get());
                    ModerationServerCommunication.timeoutUser(
                            room.getSecretId(), userId, room.getId(), duration);
                } catch (NumberFormatException | IOException e) {
                    alert.setContentText("The number you provided is not possible.");
                    alert.show();
                    throw new IOException("Not a possible number");
                }
            } else {
                alert.setContentText("Please provide a time");
                alert.show();
                throw new IOException("No time selected");
            }

        } catch (Exception e) {
            alert.setContentText("Please select the question, for which you want to ban the user");
            alert.show();
            throw new IOException("Select Question");
        }
    }

    /**
     * Initialize the links to be shown in the lecturer view.
     * @param studentLink the link students join with
     * @param moderatorLink the link moderators join with
     */
    public static void showLink(String studentLink, String moderatorLink, String roomName) {
        studentLinks = studentLink;
        moderatorLinks = moderatorLink;
        roomNameLabels = roomName;
    }

    /**
     * Each second, takes the int speed from the room in server
     * (based on the difference between the number of faster and slower in student view)
     *  if that number is bigger than +-10 it resets it and alerts the teacher to go
     *  faster/slower.
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


