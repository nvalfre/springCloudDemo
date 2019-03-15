package com.nv.springCloudDemo.user;

import com.nv.springCloudDemo.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserService {
    @Autowired
    private UserDAO userDAO;

    //GET /users

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDAO.findAll();
    }

    //GET /users/{id}
    @GetMapping("/users/{id}")
    public User retrieveUserById(@PathVariable Integer id) {
        User user = userDAO.findOne(id);
        handleUserExceptions(id, user);
        return userDAO.findOne(id);
    }

    private void handleUserExceptions(@PathVariable Integer id, User user) {
        if (user == null) {
            throw new UserNotFoundException("id:" + id);
        }
    }

    //POST /users
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        /*userDAO.save(user);
        System.out.printf("New user saved" + user.toString());*/

        //input - details of user
        //ouput 0 created and return created URI
        User userSaved = userDAO.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSaved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //DELETE /users
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        User user = userDAO.deleteById(id);
        handleUserExceptions(id, user);
    }
}
