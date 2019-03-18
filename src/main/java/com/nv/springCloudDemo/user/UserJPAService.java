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
import java.util.Optional;

@RestController
public class UserJPAService {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public Resource<Optional<User>> retrieveUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        handleUserExceptions(id, user);

        Resource<Optional<User>> resource = new Resource<Optional<User>>(user);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()
        );
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }


    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User userSaved = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSaved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        if (handleUserExceptions(id)) {
            userRepository.deleteById(id);
        }
    }


    private void handleUserExceptions(@PathVariable Integer id, Optional<User> user) {
        if (user == null) {
            throw new UserNotFoundException("id:" + id);
        }
    }

    private boolean handleUserExceptions(@PathVariable Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("id:" + id);
        }
        return true;
    }
}
