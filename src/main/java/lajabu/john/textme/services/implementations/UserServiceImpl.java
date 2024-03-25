package lajabu.john.textme.services.implementations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.projections.UserProjection;
import lajabu.john.textme.data.repositories.UserRepository;
import lajabu.john.textme.exceptions.Status404BadRequest;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lajabu.john.textme.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  public static final String USER_NOT_FOUND = "User not found";
  private final UserRepository userRepository;

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
        .build());
  }

  @Transactional(readOnly = true)
  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new Status404NotFoundException(USER_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<UserProjection> getAllUsers() {
    return userRepository.findAllBy();
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


  public UserDetailsService userDetailsService() {
    return username -> {
      User user = userRepository.findByUsername(username).orElseThrow(
          () -> new Status404NotFoundException("User with username " + username + " not found"));

      log.info("User found for details service: {}", user);
      return new org.springframework.security.core.userdetails.User(user.getUsername(),
          user.getPassword(), Collections.emptyList());
    };
  }
}
