package com.joe.testdb.controller.testuser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TestUserCreateRequest {

    @JsonProperty("user_name")
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String mobile;
}
