package Controllers;

import domain.User;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Service;

public class MainController{


    private Service service;
    private Stage stage;
    private User mainUser;
    private static final Logger logger= LogManager.getLogger();

    public void setService(Service service, Stage stage, User mainUser) {
        this.service = service;
        this.stage = stage;
        this.mainUser = mainUser;
    }


}


