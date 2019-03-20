package com.nv.springCloudDemo.user;

import com.nv.springCloudDemo.exception.UserNotFoundException;
import com.nv.springCloudDemo.post.Post;
import com.nv.springCloudDemo.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private PostRepository postRepository;

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
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User userSaved = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSaved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        handleUserExceptions(id);
        return userRepository.findById(id).map(account -> {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Repository [repositoryId=" + id + "] can't be found"));

    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveUserPostsById(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        handleUserExceptions(id, userOptional);

        return userOptional.get().getPostList();
    }

    private void handleUserExceptions(@PathVariable Integer id, Optional<User> user) {
        if (!user.isPresent()) {
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
