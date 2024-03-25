package lajabu.john.textme.services.implementations;

import java.util.Collections;
import java.util.Optional;
import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.UserRepository;
import lajabu.john.textme.exceptions.Status404BadRequest;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lajabu.john.textme.services.AuthenticationService;
import lajabu.john.textme.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  @Value("${data.config.username:lajabu.john}")
  private String configUsername;
  @Value("${data.config.email:lajabu.john@outlook.com}")
  private String configEmail;
  @Value("${data.config.password:123456}")
  private String configPassword;
  @Override
  public String signup(UserDto request) {
    Optional<User> email = userRepository.findByUsername(request.getUsername());
    Optional<User> username = userRepository.findByEmail(request.getEmail());

    if (email.isPresent() || username.isPresent()) {
      throw new Status404BadRequest("User already exists");
    }

    User user = userRepository.save(User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .build());

    return jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), Collections.emptyList()));
  }

  @Override
  public String login(UserDto request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    Optional<User> userSearch = userRepository.findByEmail(request.getEmail());
    User user = userSearch.orElseGet(() -> userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new Status404NotFoundException("User not found")));

    return jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), Collections.emptyList()));
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
}
