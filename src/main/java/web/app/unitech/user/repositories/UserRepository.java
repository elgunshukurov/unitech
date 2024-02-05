package web.app.unitech.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.unitech.user.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    User findByPin(String pin);

    Optional<User> findByPin(String pin);
}
