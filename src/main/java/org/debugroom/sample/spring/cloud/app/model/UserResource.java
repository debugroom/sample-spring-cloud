package org.debugroom.sample.spring.cloud.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
public class UserResource implements Serializable {

    private User user;

}
