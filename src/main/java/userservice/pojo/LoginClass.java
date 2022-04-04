package userservice.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginClass {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}