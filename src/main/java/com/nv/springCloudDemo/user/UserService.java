package com.nv.springCloudDemo.user;

import com.nv.springCloudDemo.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDAO.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUserById(@PathVariable Integer id) {
        User user = userDAO.findOne(id);
        handleUserExceptions(id, user);

        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()
        );
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    private void handleUserExceptions(@PathVariable Integer id, User user) {
        if (user == null) {
            throw new UserNotFoundException("id:" + id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User userSaved = userDAO.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSaved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        User user = userDAO.deleteById(id);
        handleUserExceptions(id, user);
    }
}
