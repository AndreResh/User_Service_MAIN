package userservice.service;

import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import userservice.domain.Role;
import userservice.domain.User;
import userservice.pojo.RegisterClass;
import userservice.pojo.UserForUpdate;
import userservice.repository.RoleRepository;
import userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user.get();
    }

    public User registerUser(RegisterClass registerClass) {
        if (userRepository.findUserByEmail(registerClass.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(registerClass.getEmail());
        user.setPassword(encoder.encode(registerClass.getPassword()));
        List<Role> roleList = new ArrayList<>();
        registerClass.getRoles().forEach(role -> {
            Role role1 = roleRepository.findRoleByName(role);
            System.out.println(role1);
            if (role1 != null) {
                roleList.add(role1);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        });
        user.setRoles(roleList);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserForUpdate userForUpdate, UserDetails details) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            User user = optionalUser.get();
            User user1 = userRepository.findUserByEmail(details.getUsername());
            if (!user1.getEmail().equals(user.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            if (userForUpdate.getEmail() != null) {
                user.setEmail(userForUpdate.getEmail());
            }
            if (userForUpdate.getPassword() != null) {
                user.setPassword(encoder.encode(userForUpdate.getPassword()));
            }
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User addRoleToUser(Long id, String roleName) {
        Role role = roleRepository.findRoleByName(roleName);
        Optional<User> optionalUser = userRepository.findById(id);
        if (role == null || !optionalUser.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User user = optionalUser.get();
        List<Role> roleList = user.getRoles();
        if (roleList.contains(role)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        roleList.add(role);
        user.setRoles(roleList);
        return userRepository.save(user);
    }

    public User deleteRoleFromUser(Long id, String roleName) {
        Role role = roleRepository.findRoleByName(roleName);
        Optional<User> optionalUser = userRepository.findById(id);
        if (role == null || !optionalUser.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User user = optionalUser.get();
        List<Role> roleList = user.getRoles();
        if (roleList.size() == 1 || !roleList.contains(role)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        roleList.remove(role);
        user.setRoles(roleList);
        return userRepository.save(user);
    }
}
