package org.ewul.model.rest.request.auth;

import org.ewul.model.rest.request.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginRequest implements Request {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
