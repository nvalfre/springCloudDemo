package com.nv.springCloudDemo.post;

import com.nv.springCloudDemo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostJPAService {
    @Autowired
    private UserRepository postRepository;

}
