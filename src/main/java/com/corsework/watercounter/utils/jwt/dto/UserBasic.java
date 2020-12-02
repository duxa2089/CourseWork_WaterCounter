package com.corsework.watercounter.utils.jwt.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserBasic {

    @NotEmpty
    private String username;

    @NotEmpty
    private String role;

    @NotEmpty
    private String status;
}
