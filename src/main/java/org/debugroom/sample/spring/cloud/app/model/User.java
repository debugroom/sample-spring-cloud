package org.debugroom.sample.spring.cloud.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable {

    public User(){}

    private long userId;
    private String userName;

}
