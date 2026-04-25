package service;

import Exceptions.ServiceException;
import Validators.UserValidator;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.UserRepositoryInterface;
import utils.PasswordEncryptor;

public class Service {
    private UserRepositoryInterface userRepository;
    private UserValidator userValidator;
    private static final Logger logger= LogManager.getLogger();

    public Service(UserRepositoryInterface userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    public User logIn(String username, String password){
        logger.traceEntry("logIn() {0}", username);
        password = PasswordEncryptor.encryptPassword(password);
        User u = userRepository.findUserByUsername(username);
        if(u.getPassword().equals(password)){
            logger.info("logIn Success");
            return u;
        }
        else{
            throw new ServiceException("Incorrect username or password");
        }

    }

    public void createUser(String username, String password, String email){
        User user = new User(0L, username,  PasswordEncryptor.encryptPassword(password), email);
        userValidator.validate(user);
        userRepository.save(user);

    }

}
