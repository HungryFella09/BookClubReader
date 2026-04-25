package Controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {

    /**
     * Shows message box
     * @param owner Stage
     * @param type AlertType
     * @param header String, header of the message
     * @param text String, the message
     */
    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Platform.runLater(()->{
            Alert message=new Alert(type);
            message.setHeaderText(header);
            message.setContentText(text);
            message.initOwner(owner);
            message.showAndWait();
        });
    }

    /**
     * Shows an error box
     * @param owner Stage
     * @param text String
     */
    public static void showErrorMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("Error");
        message.setContentText(text);
        message.showAndWait();
    }
}

