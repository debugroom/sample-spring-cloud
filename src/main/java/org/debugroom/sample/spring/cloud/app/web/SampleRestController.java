package org.debugroom.sample.spring.cloud.app.web;

import java.util.List;

import org.debugroom.sample.spring.cloud.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.debugroom.sample.spring.cloud.app.model.UserResource;

@RequestMapping("/api/v1")
@RestController
public class SampleRestController {

    @RequestMapping(value="users/{userId}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUserResource(@PathVariable Long userId){
        return UserResource.builder()
                .user(User.builder().userId(userId).userName("(・∀・)").build())
                .build();
    }

}
