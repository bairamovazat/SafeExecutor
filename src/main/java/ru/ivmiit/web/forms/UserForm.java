package ru.ivmiit.web.forms;

import lombok.Data;

@Data
public class UserForm {
    private String name;
    private String login;
    private String password;
    private String phone;
    private String email;
}
