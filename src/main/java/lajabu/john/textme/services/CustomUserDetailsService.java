package lajabu.john.textme.services;

import java.util.Collections;
import lajabu.john.textme.data.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userService.getUserByUsername(username);

    log.info("User found for details service: {}", user);
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), Collections.emptyList());
  }
}
