package com.nv.springCloudDemo.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDAO {
    private static List<User> users = new ArrayList<>();

    static{
        users.add(new User(1, "Nicolas", new Date()));
        users.add(new User(2, "Nicolas2", new Date()));
        users.add(new User(3, "Nicolas3", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(users.size()+1);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for(User user:users){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
}
