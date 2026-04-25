
import Controllers.LogInController;
import Validators.UserValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.UserDBRepository;
import repository.UserRepositoryInterface;
import service.Service;

import java.io.IOException;

public class BookApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UserRepositoryInterface userRepository = new UserDBRepository();
        UserValidator userValidator = new UserValidator();

        Service s = new Service(userRepository, userValidator);

        FXMLLoader fxmlLoader = new FXMLLoader(BookApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin");
        LogInController controller = fxmlLoader.getController();
        controller.setService(s);
        stage.setScene(scene);
        stage.show();

    }
}

