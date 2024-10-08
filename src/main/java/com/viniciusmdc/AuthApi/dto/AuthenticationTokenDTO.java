package com.viniciusmdc.AuthApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenDTO {

    private String accessToken;

    private String refreshToken;

}
