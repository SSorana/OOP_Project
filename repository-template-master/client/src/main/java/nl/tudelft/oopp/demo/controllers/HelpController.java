package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelpController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /**
     * On Click of "close" button it will load the student view.
     *
     * @param event the event that will trigger the function (click button).
     * @throws IOException whenever an input or output operation is failed or interpreted.
     */
    @FXML
    public void goToStudentView(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("/student_view.fxml"));
        Scene generateScene = new Scene(pane);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(generateScene);
        window.show();
    }
}
