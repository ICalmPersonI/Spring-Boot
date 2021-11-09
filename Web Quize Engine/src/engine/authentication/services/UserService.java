package engine.authentication.services;

import engine.authentication.UserDetailsImpl;
import engine.entity.User;
import engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    public UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(userName);
        user.orElseThrow(() -> new UsernameNotFoundException(userName + "not found!"));
        return user.map(UserDetailsImpl::new).get();
    }

    public boolean saveUser(User user) {
        Optional<User> dbUserRecord = repository.findByEmail(user.getEmail());

        if (dbUserRecord.isPresent()) {
            return false;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            return true;
        }
    }
}
