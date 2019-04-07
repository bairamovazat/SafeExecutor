package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.User;
import ru.ivmiit.web.security.details.Role;
import ru.ivmiit.web.security.details.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String login;
    private Role role;
    private State state;
    private String email;

    public static UserDto from(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .role(user.getRole())
                .state(user.getState())
                .email(user.getEmail())
                .build();
    }
}
