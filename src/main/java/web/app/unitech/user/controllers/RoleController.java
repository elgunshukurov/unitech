package web.app.unitech.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.unitech.user.models.Role;
import web.app.unitech.user.services.UserService;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final UserService userService;

    @PostMapping
    public Role save(@RequestBody Role role) {
        return userService.save(role);
    }
}
