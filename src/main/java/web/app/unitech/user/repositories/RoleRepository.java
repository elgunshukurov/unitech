package web.app.unitech.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.unitech.user.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
