package ru.ivmiit.web.service;

import ru.ivmiit.web.model.autorization.User;

import java.util.List;


public interface AdminService {
    List<User> getAllUsers();

}
