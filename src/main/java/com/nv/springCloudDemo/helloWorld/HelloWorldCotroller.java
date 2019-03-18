package com.nv.springCloudDemo.helloWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Controller
@Controller
public class HelloWorldCotroller {
    //message get the values of the merge of message properties and the request header
    @Autowired
    private MessageSource messageSource;

    //method - "Hello World"
    //@RequestMapping(method = RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    //URI - /hello-world-bean
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean");
    }

    //hello-world/path-variable/name
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    //hello-world/path-variable/name
    @RequestMapping(value = "/locale", method = RequestMethod.GET)
    public String helloWorldInternazionalized() {
        return "my-locale";
    }
}
