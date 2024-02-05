package web.app.unitech.user.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.app.unitech.user.models.SignUpPayload;
import web.app.unitech.user.models.User;
import web.app.unitech.user.services.UserService;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpPayload req){

        userService.signUp(req);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> list() {
        return userService.list();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @PatchMapping("/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addRoles(@RequestBody AddRolesRequest request) {
        userService.addRoleToUser(request.getPin(), request.getRole());
    }


    @Data
    class AddRolesRequest {
        private String pin;
        private String role;
    }
}
