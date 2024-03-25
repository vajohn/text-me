package lajabu.john.textme.services;

import java.util.Optional;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.UserRepository;
import lajabu.john.textme.services.implementations.AuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthenticationServiceTest {
  @Mock
  private UserRepository userRepository;
  private AuthenticationService authenticationService;
  private JwtService jwtService;
  private AuthenticationManager authenticationManager;
  @Mock
  private PasswordEncoder passwordEncoder;
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    authenticationService = new AuthenticationServiceImpl(userRepository, jwtService, authenticationManager, passwordEncoder);
  }
  @Test
  void createAdmin_existingAdmin() {
    User existingAdmin = new User("lajabu.john", "123456", "lajabu.john@outlook.com");
    existingAdmin.setId(1L);
    Mockito.when(userRepository.findByUsername("lajabu.john")).thenReturn(Optional.of(existingAdmin));
    User result = authenticationService.createAdmin();
    Assertions.assertNotEquals(existingAdmin, result);
  }

  @Test
  void createAdmin_newAdmin() {
    Mockito.when(userRepository.findByUsername("lajabu.john")).thenReturn(Optional.empty());
    User expectedAdmin = new User("lajabu.john", "lajabu.john@outlook.com", "123456");
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedAdmin);
    User result = authenticationService.createAdmin();
    Assertions.assertEquals(expectedAdmin, result);
  }
}
