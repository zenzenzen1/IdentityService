package org.example.identityservice;

import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.enums.ResponseCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdentityServiceApplication {

    public static void main(String[] args) {
        System.out.println(ResponseCode.SUCCESS.getCode() + " "
                + ApiResponse.builder().build().getCode() + " " + new ApiResponse<Void>().getCode());
        SpringApplication.run(IdentityServiceApplication.class, args);
    }
}
