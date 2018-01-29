package sttDB.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sttDB.domain.CustomUser;
import sttDB.repository.CustomUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = customUserRepository.findByUsername(username);
        if(customUser == null)
            throw new UsernameNotFoundException("Incorrect username");
        return customUser;
    }
}
