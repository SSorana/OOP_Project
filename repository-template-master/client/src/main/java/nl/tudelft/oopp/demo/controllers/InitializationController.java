package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;

@Data
public class InitializationController implements Initializable {

    private static User user;
    private static Room room;

    @FXML
    private TextField userName;

    @FXML
    private TextField roomLogin;

    @FXML
    private TextField roomName;

    @FXML
    private TextField lecturerName;

    @FXML
    private TextField timeStampFrom;

    @FXML
    private TextField timeStampTo;

    @FXML
    private DatePicker timeStampDay;


    /**
     * initializes the controller.
     *
     * @param location  used to resolve relative paths for the root object.
     * @param resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * On "Generate" button click, loads the generate_room view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void generateButton(ActionEvent event) throws IOException {
        // If user chooses the generate button in the login screen
        Parent pane = FXMLLoader.load(getClass().getResource("/generate_room.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }

    /**
     * On Click of "Generate" button it will show the lecturer view.
     *
     * @param event the event that will trigger the function (generate button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    public void generateRoom(ActionEvent event) throws IOException {
        String roomName = this.roomName.getText();
        String name = lecturerName.getText();

        // get the date and time of the Room
        String startTime = this.timeStampFrom.getText();
        String endTime = this.timeStampTo.getText();
        String date = this.timeStampDay.getValue().toString();
        Timestamp startingTime = Timestamp.valueOf(date + " " + startTime + ":00:00");
        Timestamp endingTime = Timestamp.valueOf(date + " " + endTime + ":00:00");

        //the other information for timeslot needs to be processed as well
        //check that roomName and Lecturer name are not empty
        if (roomName.isEmpty() || name.isEmpty()) {
            System.out.println("Room and Lecturer name can't be empty");
        } else {
            user = RoomServerCommunication.createUser(name);
            user.setRole("Lecturer");
            room = RoomServerCommunication.postRoom(roomName, startingTime, endingTime);
            //Lecturer joins the room
            RoomServerCommunication.joinRoom(user.getId().toString(), room.getId().toString());

            LecturerViewController.showLink(room.getId().toString(),
                    room.getSecretId().toString(), roomName);
            StudentViewController.showContent(name, String.valueOf(room.getUserList().size()));

            Parent pane = FXMLLoader.load(getClass().getResource("/lecturer_view.fxml"));
            Scene generateScene = new Scene(pane);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(generateScene);
            window.show();
        }
    }

    /**
     * On "Connect" button click, loads the student_view view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void connectButton(ActionEvent event) throws IOException {
        // Get the room if valid Id was provided
        String roomId = roomLogin.getText();
        try {
            room = RoomServerCommunication.getRoom(roomId);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Non-Existent Room");
            alert.setContentText("The Room Doesn't exist");
            alert.show();
            throw new IOException("Room doesn't exist");
        }

        // check if fromTime <= currentTime <= toTime
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (currentTime.before(room.getFromTime())
                || currentTime.after(room.getToTime())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Chosen room is not in the current time frame!");
            alert.show();
            throw new IOException("Wrong room time frame.");
        }

        // Create user based on name provided
        String name = userName.getText();
        if (name.isEmpty()) {
            System.out.println("Name can't be empty");
        } else {
            user = RoomServerCommunication.createUser(name);
        }

        // User joins the room
        RoomServerCommunication.joinRoom(user.getId().toString(), room.getId().toString());

        LecturerViewController.showLink(room.getId().toString(),
                room.getSecretId().toString(), room.getName());

        // Check if user has joined with Mod Link or User Link and initiate the right view
        if (roomId.equals(room.getSecretId().toString())) {
            user.setRole("Moderator");
            Parent pane = FXMLLoader.load(getClass().getResource("/lecturer_view.fxml"));
            Scene generateScene = new Scene(pane);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(generateScene);
            window.show();
        } else {
            user.setRole("Student");
            Parent pane = FXMLLoader.load(getClass().getResource("/student_view.fxml"));
            Scene generateScene = new Scene(pane);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(generateScene);
            window.show();
        }
    }

    /**
     * Getter for User.
     * @return the user instance
     */
    public static User getUser() {
        return user;
    }

    /**
     * Getter for Room.
     * @return the room instance
     */
    public static Room getRoom() {
        return room;
    }

    /**
     * Setter for User.
     * @param user the user we want to set, mainly used for testing
     */
    public static void setUser(User user) {
        InitializationController.user = user;
    }

    /**
     * Setter for Room.
     * @param room the room we want to set, mainly used for testing
     */
    public static void setRoom(Room room) {
        InitializationController.room = room;
    }

}

