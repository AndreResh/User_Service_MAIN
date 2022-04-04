package userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import userservice.domain.User;
import userservice.pojo.RegisterClass;
import userservice.pojo.UserForUpdate;
import userservice.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody RegisterClass registerClass) {
        return ResponseEntity.ok(userService.registerUser(registerClass));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserForUpdate userForUpdate, @AuthenticationPrincipal UserDetails details) {
        return ResponseEntity.ok(userService.updateUser(id, userForUpdate, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/addRole")
    public ResponseEntity<User> addRoleToUser(@PathVariable("id") Long id, @RequestParam(value = "roleName", required = true) String roleName) {
        return ResponseEntity.ok(userService.addRoleToUser(id, roleName));
    }

    @PatchMapping("/{id}/removeRole")
    public ResponseEntity<User> removeRoleToUser(@PathVariable("id") Long id, @RequestParam(value = "roleName", required = true) String roleName) {
        return ResponseEntity.ok(userService.deleteRoleFromUser(id, roleName));
    }
}
