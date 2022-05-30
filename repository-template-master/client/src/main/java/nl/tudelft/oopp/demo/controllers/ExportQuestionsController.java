package nl.tudelft.oopp.demo.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.AnsweredQuestionsServerCommunication;
import nl.tudelft.oopp.demo.data.Answered;


public class ExportQuestionsController  implements Initializable {

    @FXML
    private TextField pathToFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Get all the questions for a specific room.
     * write them in a user friendly way to the file.
     *
     * @param event button clicks
     * @throws IOException if there is no question yet
     */
    public void exportQuestions(ActionEvent event) throws IOException {


        String path = pathToFile.getText().toString();

        List<Answered> questionListFromServer = new ArrayList<>();
        questionListFromServer = AnsweredQuestionsServerCommunication
                .getAllAnswers(InitializationController.getRoom().getId());
        String roomName = questionListFromServer.get(0).getQuestion().getRoom().getName();

        String stringToWrite = "Questions asked during lecture " + roomName + ":\n";
        for (Answered question: questionListFromServer) {
            stringToWrite += question.showOnWindow();
        }

        try {
            Writer output = null;
            File file = new File(path + "/" + roomName + "AnsweredQuestions.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(stringToWrite);
            output.close();
            System.out.println("File has been written");
            // generate the new scene
            Parent pane = FXMLLoader.load(getClass().getResource("/lecturer_view.fxml"));
            Scene generateScene = new Scene(pane);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(generateScene);
            window.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please provide a good path");
            alert.show();
            e.printStackTrace();
        }
    }
}
