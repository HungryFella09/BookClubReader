package Controllers;

import Exceptions.RepositoryException;
import Exceptions.ServiceException;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Service;

public class SignUpController {
    public PasswordField passwordField;
    public PasswordField retypePasswordField;
    public TextField usernameField;
    public TextField emailField;
    public Label errorLabel;
    private Service service;
    private Stage stage;
    private static final Logger logger= LogManager.getLogger();
    
    public void onSignUp(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String retypePassword = retypePasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || retypePassword.isEmpty()) {
            errorLabel.setText("Please fill all the fields");
            return;
        }
        if (!password.equals(retypePassword)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        try{
            service.createUser(username, password, email);
        }
        catch (ServiceException | RepositoryException e){
            errorLabel.setText(e.getMessage());
        }

        stage.close();
    }

    public void setService(Service service, Stage stage) {
        this.service = service;
        this.stage = stage;
    }
}
