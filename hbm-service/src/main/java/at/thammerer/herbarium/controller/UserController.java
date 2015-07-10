package at.thammerer.herbarium.controller;


import at.thammerer.herbarium.dto.NewUserDTO;
import at.thammerer.herbarium.dto.UserInfoDTO;
import at.thammerer.herbarium.model.User;
import at.thammerer.herbarium.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;

/**
 *
 *  REST service for users.
 *
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    @Inject
    UserService userService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public UserInfoDTO getUserInfo(Principal principal) {

        User user = userService.findUserByUsername(principal.getName());

        return user != null ? new UserInfoDTO(user.getUsername(), user.getEmail()) : null;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public UserInfoDTO getUserById(@PathVariable Long id) {

        User user = userService.findUserById(id);

        return user != null ? new UserInfoDTO(user.getUsername(), user.getEmail()) : null;
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody NewUserDTO user) {
        userService.createUser(user.getUsername(), user.getEmail(), user.getPlainTextPassword());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
