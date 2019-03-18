package com.nv.springCloudDemo.post;

import com.nv.springCloudDemo.exception.PostNotFoundException;
import com.nv.springCloudDemo.user.User;
import com.nv.springCloudDemo.user.UserRepository;
import com.nv.springCloudDemo.util.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        ExceptionHandler.handleUserExceptions(id, userOptional);

        User user = userOptional.get();
        post.setUser(user);

        postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{idUser}/posts/{idPost}")
    public void deletePostById(@PathVariable Integer idUser, @PathVariable Integer idPost) {
        if (ExceptionHandler.handleUserExceptions(idUser, userRepository)) {
            Optional<User> userOptional = userRepository.findById(idUser);
            Optional<Post> postOptional = postRepository.findById(idPost);
            if (userOptional.isPresent() && postOptional.isPresent()) {
                User user = userOptional.get();
                Post post = postOptional.get();
                deletePostIfPresentInUserPostList(idPost, user, post);
            }
        }
    }

    private void deletePostIfPresentInUserPostList(@PathVariable Integer idPost, User user, Post post) {
        if (user.getPostList().contains(post)) {
            postRepository.deleteById(idPost);
        } else {
            throw new PostNotFoundException("id:" + idPost);
        }
    }
}
