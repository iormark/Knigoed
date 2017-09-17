package info.knigoed.pojo;

import javax.validation.constraints.*;
import java.io.Serializable;

public class SignInForm implements Serializable {

    @Email(message = "Адрес электронной почты не корректен")
    @Size(min = 1, message = "Адрес электронной почты не корректен")
    private String email;
    @Pattern(regexp = "^(?iu)[a-z0-9!@#$%^&*()_\\-+:;,.]{5,20}$", message = "Пароль должен содержать от 5 до 20 символов. "
        + "Можно использовать латинские буквы, цифры и "
        + "символы из списка: <b>! @ # $ % ^ &amp; * ( ) _ - + : ; , .</b>")
    private String password;

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
