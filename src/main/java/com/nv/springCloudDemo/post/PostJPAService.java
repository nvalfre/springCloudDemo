package com.nv.springCloudDemo.post;

import com.nv.springCloudDemo.exception.UserNotFoundException;
import com.nv.springCloudDemo.user.User;
import com.nv.springCloudDemo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class PostJPAService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> retrieveUserPostsById(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        handleUserExceptions(id, userOptional);

        User user = userOptional.get();
        post.setUser(user);

        postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    private void handleUserExceptions(@PathVariable Integer id, Optional<User> user) {
        if (!user.isPresent()) {
            throw new UserNotFoundException("id:" + id);
        }
    }
}
