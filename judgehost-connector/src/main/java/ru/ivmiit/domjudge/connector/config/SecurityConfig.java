package ru.ivmiit.domjudge.connector.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@ComponentScan("ru.ivmiit.domjudge.connector")
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String judgehostLogin;
    private String judgehostPassword;

    public SecurityConfig(@Value("${judgehost.credentials.login}") String judgehostLogin,
                          @Value("${judgehost.credentials.password}") String judgehostPassword) {
        this.judgehostLogin = judgehostLogin;
        this.judgehostPassword = judgehostPassword;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        if (this.judgehostLogin != null) {
            auth.inMemoryAuthentication()
                    .withUser(judgehostLogin)
                    .password(encoder.encode(judgehostPassword))
                    .roles("JUDGEHOST");
            log.info("Judgehost login: \"" + judgehostLogin + "\", password: \"" + judgehostPassword + "\"");
        } else {
            log.error("Judgehost login not found not found");
        }
    }

}