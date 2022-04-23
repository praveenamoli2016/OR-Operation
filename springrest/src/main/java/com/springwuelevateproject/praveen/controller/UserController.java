package com.springwuelevateproject.praveen.controller;

import java.util.ArrayList;
import com.google.common.base.Joiner;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springwuelevateproject.praveen.model.User;
import com.springwuelevateproject.praveen.repository.UserRepository;
import com.springwuelevateproject.praveen.services.SearchCriteria;
import com.springwuelevateproject.praveen.services.SearchOperation;
import com.springwuelevateproject.praveen.utility.UserSpecificationsBuilder;

@Controller
public class UserController {

	@Autowired
    private UserRepository repo;

	@GetMapping("/users")
	@ResponseBody
	public List<User> findAllByOrPredicate(@RequestParam String search) {
	    Specification<User> spec = resolveSpecification(search);
	    return repo.findAll(spec);
	}

	protected Specification<User> resolveSpecification(String searchParameters) {
	    UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
	    String operationSetExper = Joiner.on("|")
	      .join(SearchOperation.SIMPLE_OPERATION_SET);
	    Pattern pattern = Pattern.compile(
	      "(\\p{Punct}?)(\\w+?)("
	      + operationSetExper 
	      + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
	    Matcher matcher = pattern.matcher(searchParameters + ",");
	    while (matcher.find()) {
	        builder.with(matcher.group(1), matcher.group(2), matcher.group(3), 
	        matcher.group(5), matcher.group(4), matcher.group(6));
	    }
	    
	    return builder.build();
	}
}

