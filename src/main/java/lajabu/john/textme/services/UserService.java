package lajabu.john.textme.services;

import java.util.List;
import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
  UserDetailsService userDetailsService();
  User saveUser(UserDto user);
  List<User> getAllUsers();
  User getUserById(Long id);
  User getUserByUsername(String username);
  User getUserByEmail(String email);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  User findUserEmailUpdate(UserDto dto);

}
