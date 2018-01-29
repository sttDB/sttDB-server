package sttDB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import sttDB.domain.CustomUser;
import sttDB.repository.CustomUserRepository;

@Configuration
@Profile("Test")
public class AuthenticationTestConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    CustomUserRepository customUserRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(CustomUser.passwordEncoder);

        CustomUser customUser = new CustomUser();
        customUser.setUsername("test");
        customUser.setPassword("password");
        customUserRepository.save(customUser);
    }

}
