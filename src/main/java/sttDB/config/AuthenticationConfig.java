package sttDB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import sttDB.domain.User;
import sttDB.repository.UserRepository;


@Configuration
@Profile("!Test")
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(User.passwordEncoder);

        String rootUser = System.getenv("ROOT_USER");
        String rootPassword = System.getenv("ROOT_PASSWORD");
        if(!userRepository.exists(rootUser)) {
            User user = new User();
            user.setUsername(rootUser);
            user.setPassword(rootPassword);
            userRepository.save(user);
        }
    }
}