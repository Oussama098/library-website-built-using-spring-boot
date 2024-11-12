package ous.LabraryWebSite.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ous.LabraryWebSite.Repositories.RoleRepository;
import ous.LabraryWebSite.Repositories.UserRepository;
import ous.LabraryWebSite.models.Role;
import ous.LabraryWebSite.models.UserEntity;

import java.security.Principal;
import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository , RoleRepository roleRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void addUser(UserEntity user) {
        Role role = roleRepository.findByName("USER");
        user.setRole(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void processOauth2Login(OAuth2User OAuth2User) {
        String email = OAuth2User.getAttribute("email");
        String username = OAuth2User.getAttribute("name");
        UserEntity userEntity = this.findByEmail(email);
        if (userEntity == null) {
            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setUsername(username);
            Role role = roleRepository.findByName("USER");
            user.setRole(Collections.singletonList(role));
            this.userRepository.save(user);
        }
    }
}
