package com.nv.springCloudDemo.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FilterBeanController {

    @GetMapping("/filtering")
    public FilterBean retrieveSomeBeanFiltered(){
        return new FilterBean("a","b","c");
    }


    @GetMapping("/filtering-list")
    public List<FilterBean> retrieveSomeBeanFilteredList(){
        return Arrays.asList(
                new FilterBean("a","b","c"),
                new FilterBean("a2","b2", "c2"))
                ;
    }
}
