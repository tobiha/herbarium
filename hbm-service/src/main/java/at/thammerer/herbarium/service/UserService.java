package at.thammerer.herbarium.service;

import at.thammerer.herbarium.dao.UserDao;
import at.thammerer.herbarium.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.regex.Pattern;

import static at.thammerer.herbarium.service.ValidationUtils.assertMatches;
import static at.thammerer.herbarium.service.ValidationUtils.assertMinimumLength;
import static at.thammerer.herbarium.service.ValidationUtils.assertNotBlank;


/**
 *
 * Business service for User entity related operations
 *
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Inject
    private UserDao userDao;


    /**
     *
     * creates a new user in the database
     *
     * @param username - the username of the new user
     * @param email - the user email
     * @param password - the user plain text password
     */
    @Transactional
    public void createUser(String username, String email, String password) {

        assertNotBlank(username, "Username cannot be empty.");
        assertMinimumLength(username, 6, "Username must have at least 6 characters.");
        assertNotBlank(email, "Email cannot be empty.");
        assertMatches(email, EMAIL_REGEX, "Invalid email.");
        assertNotBlank(password, "Password cannot be empty.");
        assertMatches(password, PASSWORD_REGEX, "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");

        if (!userDao.isUsernameAvailable(username)) {
            throw new IllegalArgumentException("The username is not available.");
        }

        User user = new User(username, new BCryptPasswordEncoder().encode(password), email);

        userDao.save(user);
        LOG.info("new User created: {} ", user.toString());
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userDao.findById(id);
    }

}
