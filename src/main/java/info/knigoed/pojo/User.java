package info.knigoed.pojo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int userId;
    @Email(message = "Адрес электронной почты не корректен")
    @Size(min = 1, message = "Адрес электронной почты не корректен")
    private String email;
    @Pattern(regexp = "^(?iu)[a-z0-9!@#$%^&*()_\\-+:;,.]{5,20}$", message = "Пароль должен содержать от 5 до 20 символов. "
        + "Можно использовать латинские буквы, цифры и "
        + "символы из списка: <b>! @ # $ % ^ &amp; * ( ) _ - + : ; , .</b>")
    private String password;
    @NotNull
    @NotEmpty
    private String name;
    private String hash;
    private Role role = Role.user;
    public enum Role {
        user, admin
    }
    private Date lastLogin;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
