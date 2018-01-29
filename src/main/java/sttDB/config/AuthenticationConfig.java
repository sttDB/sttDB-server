package sttDB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import sttDB.domain.CustomUser;
import sttDB.repository.CustomUserRepository;


@Configuration
@Profile("!Test")
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    CustomUserRepository customUserRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(CustomUser.passwordEncoder);

        String rootUser = System.getenv("ROOT_USER");
        String rootPassword = System.getenv("ROOT_PASSWORD");
        if(rootUser == null || rootPassword == null) {
            throw new IllegalStateException("Environment variable user or password not defined.");
        }else if(!customUserRepository.exists(rootUser)) {
            CustomUser customUser = new CustomUser();
            customUser.setUsername(rootUser);
            customUser.setPassword(rootPassword);
            customUserRepository.save(customUser);
        }
    }
}