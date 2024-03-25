package lajabu.john.textme.controllers;

import java.util.List;
import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUserByUsername(username));
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
    return ResponseEntity.ok(userService.getUserByEmail(email));
  }

  @GetMapping("/exists/username/{username}")
  public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.existsByUsername(username));
  }

  @GetMapping("/exists/email/{email}")
  public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
    return ResponseEntity.ok(userService.existsByEmail(email));
  }

  @PutMapping("/update/email")
  public ResponseEntity<User> findUserEmailUpdate(@RequestBody UserDto user) {
    return ResponseEntity.ok(userService.findUserEmailUpdate(user));
  }
}
