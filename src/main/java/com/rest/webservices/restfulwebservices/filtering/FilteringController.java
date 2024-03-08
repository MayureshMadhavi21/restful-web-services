package com.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
    
	@GetMapping("/static-filtering")
	public StaticFilterBean staticFiltering() {
		return new StaticFilterBean("value1", "value2", "value3","value4");
	}
    
	@GetMapping("/static-filtering-list")
	public List<StaticFilterBean> staticFilteringList() {
		return Arrays.asList(new StaticFilterBean("value1", "value2", "value3","value4"),
				new StaticFilterBean("value5", "value6", "value7","value8"));
	}
	
	@GetMapping("/dynamic-filtering")
	public MappingJacksonValue dynamicFiltering() {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(new DynamicFilterBean("value1", "value2", "value3","value4"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
    
	@GetMapping("/dynamic-filtering-list")
	public MappingJacksonValue dynamicFilteringList() {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(Arrays.asList(new DynamicFilterBean("value1", "value2", "value3","value4"),
				new DynamicFilterBean("value5", "value6", "value7","value8")));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field3","field4");
		FilterProvider filters = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

}
