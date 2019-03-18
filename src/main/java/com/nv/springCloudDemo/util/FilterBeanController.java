package com.nv.springCloudDemo.util;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class FilterBeanController {

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBeanFiltered() {
        FilterBean filterBean = new FilterBean("a", "b", "c");

        return generateAndApplyFilters(
                "field1", "field2",
                "DynamicFilter",
                Collections.singletonList(filterBean));
    }


    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveSomeBeanFilteredList() {
        List<FilterBean> filterBeansList = Arrays.asList(
                new FilterBean("a", "b", "c"),
                new FilterBean("a2", "b2", "c2"));

        return generateAndApplyFilters("field2", "field3", "DynamicFilter", filterBeansList);
    }

    private MappingJacksonValue generateAndApplyFilters(String filter1, String filter2, String dynamicFilterName, List<FilterBean> filterBean) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterBean);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(filter1, filter2);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter(dynamicFilterName, filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}
