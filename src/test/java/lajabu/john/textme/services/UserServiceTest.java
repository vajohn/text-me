package lajabu.john.textme.services;

import java.util.Optional;
import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.UserRepository;
import lajabu.john.textme.exceptions.Status404BadRequest;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userService = new UserService(userRepository, passwordEncoder);
  }

  @Test
  void saveUser_success() {
    UserDto userDto = UserDto.builder()
        .email("new@email.com")
        .username("newUser")
        .password("password")
        .build();
    User savedUser = User.builder()
        .email("old@email.com")
        .username("oldUser")
        .password("password")
        .build();
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);
    User result = userService.saveUser(userDto);
    Assertions.assertEquals(savedUser, result);
  }

  @Test
  void saveUser_existingUsername() {
    UserDto userDto = UserDto.builder()
        .email("existing@email.com")
        .username("existing")
        .password("password")
        .build();
    Mockito.when(userRepository.findByUsername("existing")).thenReturn(Optional.of(new User()));
    Assertions.assertThrows(Status404BadRequest.class, () -> userService.saveUser(userDto));
  }

  @Test
  void saveUser_existingEmail() {
    UserDto userDto = UserDto.builder()
        .email("existing@email.com")
        .username("existingUser")
        .password("password")
        .build();
    Mockito.when(userRepository.findByEmail("existing@email.com")).thenReturn(Optional.of(new User()));
    Assertions.assertThrows(Status404BadRequest.class, () -> userService.saveUser(userDto));
  }

  @Test
  void getUserById_success() {
    User user = User.builder()
        .email("new@email.com")
        .username("newUser")
        .password("password")
        .build();
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    User result = userService.getUserById(1L);
    Assertions.assertEquals(user, result);
  }

  @Test
  void getUserById_notFound() {
    Assertions.assertThrows(Status404NotFoundException.class, () -> userService.getUserById(1L));
  }

  @Test
  void createAdmin_existingAdmin() {
    User existingAdmin = new User("lajabu.john", "lajabu.john@outlook.com", "123456");
    Mockito.when(userRepository.findByUsername("lajabu.john")).thenReturn(Optional.of(existingAdmin));
    User result = userService.createAdmin();
    Assertions.assertEquals(existingAdmin, result);
  }

  @Test
  void createAdmin_newAdmin() {
    Mockito.when(userRepository.findByUsername("lajabu.john")).thenReturn(Optional.empty());
    User expectedAdmin = new User("lajabu.john", "lajabu.john@outlook.com", "123456");
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedAdmin);
    User result = userService.createAdmin();
    Assertions.assertEquals(expectedAdmin, result);
  }
}

