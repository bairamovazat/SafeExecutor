package ru.ivmiit.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ivmiit.web.forms.UserRegistrationForm;
import ru.ivmiit.web.model.User;
import ru.ivmiit.web.repository.UserRepository;
import ru.ivmiit.web.security.details.Role;
import ru.ivmiit.web.security.details.State;

import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(UserRegistrationForm userForm) {
        UUID uuid = UUID.randomUUID();
        User newUser = User.builder()
                .login(userForm.getLogin())
                .hashPassword(passwordEncoder.encode(userForm.getPassword()))
                .role(Role.USER)
                .state(State.CREATED)
                .email(userForm.getEmail())
                .name(userForm.getName())
                .uuid(uuid)
                .build();
        emailService.sendMail("Здравствуйте, чтобы продтвердить аккаунт перейдите по: http://localhost:8080/confirm/" + uuid,"Подтверждение аккаунта",userForm.getEmail());
        userRepository.save(newUser);
    }
}
