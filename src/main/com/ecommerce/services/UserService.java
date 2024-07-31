package main.com.ecommerce.services;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userDAO.createUser(user);
    }

    public User authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}

