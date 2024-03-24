package lajabu.john.textme.services.implementations;

import java.util.Collections;
import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.services.AuthenticationService;
import lajabu.john.textme.services.JwtService;
import lajabu.john.textme.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  @Override
  public String signup(UserDto request) {
    User user = userService.saveUser(request);
    return jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), Collections.emptyList()));
  }

  @Override
  public String login(UserDto request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    User user = userService.findByEmailOrUsername(request);
    return jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), Collections.emptyList()));
  }
}
