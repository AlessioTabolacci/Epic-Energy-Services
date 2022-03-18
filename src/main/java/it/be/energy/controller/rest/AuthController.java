package it.be.energy.controller.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.be.energy.exception.UserException;
import it.be.energy.model.LoginRequest;
import it.be.energy.model.LoginResponse;
import it.be.energy.model.RequestRegisterUser;
import it.be.energy.model.Role;
import it.be.energy.model.Roles;
import it.be.energy.model.User;
import it.be.energy.repository.RoleRepository;
import it.be.energy.repository.UserRepository;
import it.be.energy.service.UserDetailsImpl;
import it.be.energy.util.JwtUtils;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
    PasswordEncoder encoder;
	
	@Autowired
    RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Operation (summary = "username: admin password: admin (TUTTE LE OPERAZIONI)   -   username: user password: user (SOLO GET)")
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		LoginResponse loginResponse = new LoginResponse();
		
		loginResponse.setToken(token);
		loginResponse.setRoles(roles);
		
		return ResponseEntity.ok(loginResponse);
	}
	
	@Operation(summary = "Inserire username e password a scelta. Nel campo roles inserire USER o ADMIN in base ai permessi da abilitare")
	@PostMapping("/signup")
    public ResponseEntity<?> registraUser(@RequestBody RequestRegisterUser registraUser) {

        if (userRepository.existsByEmail(registraUser.getEmail())) {
            return new ResponseEntity<String>("email già registrata!", HttpStatus.BAD_REQUEST);
        } else if (userRepository.existsByUserName(registraUser.getUserName())) {
            return new ResponseEntity<String>("username non disponibile!", HttpStatus.BAD_REQUEST);
        }

        User nuovoUtente = new User();
        nuovoUtente.setUserName(registraUser.getUserName());
        nuovoUtente.setEmail(registraUser.getEmail());
        nuovoUtente.setPassword(encoder.encode(registraUser.getPassword()));
        if (registraUser.getRoles().isEmpty()) {
            Optional<Role> ruolo = roleRepository.findByRoleName(Roles.ROLE_USER);
            Set<Role> ruoli = new HashSet<>();
            ruoli.add(ruolo.get());
            nuovoUtente.setRoles(ruoli);
        } else {
            Set<Role> ruoli = new HashSet<>();
            for (String set : registraUser.getRoles()) {
                switch (set.toUpperCase()) {
                case "ADMIN":
                    Optional<Role> ruolo1 = roleRepository.findByRoleName(Roles.ROLE_ADMIN);
                    ruoli.add(ruolo1.get());
                    break;
                case "USER":
                    Optional<Role> ruolo2 = roleRepository.findByRoleName(Roles.ROLE_USER);
                    ruoli.add(ruolo2.get());
                    break;
                default:
                    throw new UserException("Ruolo non trovato!");

                }

            }
            nuovoUtente.setRoles(ruoli);

        }
        userRepository.save(nuovoUtente);
        return new ResponseEntity<>("Utente registrato correttamente: " + nuovoUtente.toString(), HttpStatus.CREATED);

    }

	

}
