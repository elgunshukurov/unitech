package web.app.unitech.user.services;

import lombok.RequiredArgsConstructor;
import web.app.unitech.user.models.Role;
import web.app.unitech.user.models.SignUpPayload;
import web.app.unitech.user.models.User;
import web.app.unitech.user.repositories.RoleRepository;
import web.app.unitech.user.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class DefaultUserService implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void signUp(SignUpPayload payload) {
        String pin = payload.getPin();
        String name = payload.getName();
        String password = payload.getPassword();


        if (userRepository.findByPin(pin).isPresent()) {
            throw new RuntimeException("Pin already registered");
        }

        save(User.builder().name(name).pin(pin).password(password).roles(new HashSet<>()).build());

        addRoleToUser(pin, "ROLE_USER");

    }

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String pin, String roleName) {
        User user = userRepository.findByPin(pin).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User get(String pin) {
        return userRepository.findByPin(pin).get();
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        User user = userRepository.findByPin(pin).get();
        if (user == null) {
            throw new UsernameNotFoundException("User is not found.");
        }
        List<SimpleGrantedAuthority> authorities  = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getPin(), user.getPassword(), authorities);
    }

}
