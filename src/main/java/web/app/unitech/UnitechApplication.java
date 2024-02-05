package web.app.unitech;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import web.app.unitech.payment.services.AccountService;
import web.app.unitech.user.models.Role;
import web.app.unitech.user.models.User;
import web.app.unitech.user.services.UserService;

import java.util.HashSet;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class UnitechApplication {

	private final AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(UnitechApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.save(Role.builder().name("ROLE_USER").build());
			userService.save(Role.builder().name("ROLE_ADMIN").build());

			userService.save(User.builder().name("John").pin("jdoe").password("12234").roles(new HashSet<>()).build());

			userService.addRoleToUser("jdoe", "ROLE_USER");

			accountService.addAccount("jdoe");

		};
	}

}
