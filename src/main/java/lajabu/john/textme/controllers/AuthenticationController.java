package lajabu.john.textme.controllers;

import lajabu.john.textme.data.dao.UserDto;
import lajabu.john.textme.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody UserDto request) {
    return ResponseEntity.ok(authenticationService.signup(request));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserDto request) {
    return ResponseEntity.ok(authenticationService.login(request));
  }
}
