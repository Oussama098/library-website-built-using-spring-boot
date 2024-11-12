package ous.LabraryWebSite.Security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ous.LabraryWebSite.Repositories.UserRepository;
import ous.LabraryWebSite.models.UserEntity;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole().stream().map((role)->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList())
            );
        }else{
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
