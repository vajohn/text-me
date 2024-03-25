package lajabu.john.textme.services;

import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;

public interface AuthenticationService {
  String signup(UserDto request);

  String login(UserDto request);

  User createAdmin();
}
