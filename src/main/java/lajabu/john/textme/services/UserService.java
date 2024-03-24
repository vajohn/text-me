package lajabu.john.textme.services;

import java.util.Optional;
import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.UserRepository;
import lajabu.john.textme.exceptions.Status404BadRequest;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  public static final String USER_NOT_FOUND = "User not found";
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${data.config.username:lajabu.john}")
  private String configUsername;
  @Value("${data.config.email:lajabu.john@outlook.com}")
  private String configEmail;
  @Value("${data.config.password:123456}")
  private String configPassword;

  @Transactional
  public User saveUser(UserDto user) {
    Optional<User> email = userRepository.findByUsername(user.getUsername());
    Optional<User> username = userRepository.findByEmail(user.getEmail());

    if (email.isPresent() || username.isPresent()) {
      throw new Status404BadRequest("User already exists");
    }

    return userRepository.save(User.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .password(passwordEncoder.encode(user.getPassword()))
        .build());
  }

  @Transactional(readOnly = true)
  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new Status404NotFoundException(USER_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(
        () -> new Status404NotFoundException("User with username " + username + " not found"));
  }

  @Transactional(readOnly = true)
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(
        () -> new Status404NotFoundException("User with email " + email + " not found"));
  }

  @Transactional(readOnly = true)
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Transactional(readOnly = true)
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public User findUserEmailUpdate(UserDto dto) {
    User user = userRepository.findById(dto.getId())
        .orElseThrow(() -> new Status404NotFoundException(USER_NOT_FOUND));
    user.setEmail(dto.getEmail());
    return userRepository.save(user);
  }

  @Transactional
  public User createAdmin() {
    Optional<User> user = userRepository.findByUsername(configUsername);
    return user.orElseGet(() -> userRepository.save(User.builder()
        .username(configUsername)
        .email(configEmail)
        .password(passwordEncoder.encode(configPassword))
        .build()));
  }

  @Transactional(readOnly = true)
  public User findByEmailOrUsername(UserDto request) {
    Optional<User> user = userRepository.findByEmail(request.getEmail());
    return user.orElseGet(() -> userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new Status404NotFoundException(USER_NOT_FOUND)));
  }
}
