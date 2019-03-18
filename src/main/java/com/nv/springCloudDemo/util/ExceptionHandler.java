package com.nv.springCloudDemo.util;

import com.nv.springCloudDemo.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public class ExceptionHandler {
    public static void handleUserExceptions(@PathVariable Integer id, Optional<?> object) {
        if (!object.isPresent()) {
            throw new UserNotFoundException("id:" + id);
        }
    }

    public static boolean handleUserExceptions(@PathVariable Integer id, JpaRepository jpaRepository) {
        if (!jpaRepository.existsById(id)) {
            throw new UserNotFoundException("id:" + id);
        }
        return true;
    }
}
