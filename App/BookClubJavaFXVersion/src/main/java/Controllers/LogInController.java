package Controllers;

import Exceptions.RepositoryException;
import Exceptions.ServiceException;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Service;

import java.io.IOException;

public class LogInController {

    public PasswordField passwordField;
    public TextField usernameField;
    public Label errorLabel;
    private Service service;
    private static final Logger logger= LogManager.getLogger();

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    public void initialize() {
    }

    public void onLogIn(ActionEvent actionEvent) {
        logger.info("onLogIn");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill all the fields");
            return;
        }
        try{
            User u = service.logIn(username, password);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../main-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            var stage = new Stage();
            stage.setTitle("BK");
            stage.setScene(scene);
            MainController controller = fxmlLoader.getController();
            controller.setService(service, stage, u);
            stage.show();
        }
        catch (ServiceException | RepositoryException e){
            errorLabel.setText(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSignUp(ActionEvent actionEvent) throws IOException {
        logger.info("onSignUp");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sign-up-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        var stage = new Stage();
        stage.setTitle("Sign Up Option");
        stage.setScene(scene);
        SignUpController controller = fxmlLoader.getController();
        controller.setService(service, stage);
        stage.show();
    }
}


