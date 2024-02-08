package fr.dtek.lab.controller;

import fr.dtek.lab.jpa.UserEntity;
import fr.dtek.lab.jpa.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

    @CrossOrigin
    @GetMapping("/login")
    public ResponseEntity<?> login() {
        LOG.info("Trying to login for NDI !!");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            // L'utilisateur est authentifié, renvoyer les détails de l'utilisateur ou un message de succès
            return ResponseEntity.ok("Utilisateur authentifié : " + authentication.getName());
        } else {
            // L'utilisateur n'est pas authentifié, renvoyer un message d'erreur
            return ResponseEntity.status(401).body("Échec de l'authentification");
        }
    }

    @CrossOrigin
    @GetMapping("/loggedIn")
    public ResponseEntity<?> loggedIn() {
        // Récupération de l'utilisateur connecté
        // Cela dépend de la manière dont vous gérez l'authentification dans votre application
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserEntity> user = userRepository.findByUsername(username); // userService est une dépendance injectée pour interagir avec la base de données

        if (user.isPresent()) {
            user.get().setPassword("");
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @CrossOrigin
//    @PreAuthorize("hasRole('hastwitter')")
    @GetMapping("/status/check")
    public String status() {
        LOG.info("Checking the status for NDI !!");

        return "Working";
    }
}
