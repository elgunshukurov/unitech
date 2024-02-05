package web.app.unitech.user.services;



import web.app.unitech.user.models.Role;
import web.app.unitech.user.models.SignUpPayload;
import web.app.unitech.user.models.User;

import java.util.List;

public interface UserService {

    User save(User user);
    Role save(Role role);
    void addRoleToUser(String pin, String roleName);
    User get(String pin);
    List<User> list();

    void signUp(SignUpPayload req);
}
