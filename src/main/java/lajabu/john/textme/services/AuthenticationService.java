package lajabu.john.textme.services;

import lajabu.john.textme.data.dao.UserDto;

public interface AuthenticationService {
  String signup(UserDto request);

  String login(UserDto request);
}
