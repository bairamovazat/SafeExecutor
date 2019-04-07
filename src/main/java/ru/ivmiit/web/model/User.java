package ru.ivmiit.web.model;

import lombok.*;
import ru.ivmiit.web.security.details.Role;
import ru.ivmiit.web.security.details.State;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chat_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private UUID uuid;


    @Column(unique = true)
    private String login;

    private String hashPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private State state;

    private String email;

}