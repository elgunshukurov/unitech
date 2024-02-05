package web.app.unitech;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import web.app.unitech.user.models.Role;
import web.app.unitech.user.models.SignUpPayload;
import web.app.unitech.user.models.User;
import web.app.unitech.user.repositories.RoleRepository;
import web.app.unitech.user.repositories.UserRepository;
import web.app.unitech.user.services.DefaultUserService;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DefaultUserService userService;

    @Test
    void testSignUp() {
        // Arrange
        SignUpPayload signUpPayload = new SignUpPayload("1234", "John Doe", "password");

        when(userRepository.findByPin(signUpPayload.getPin())).thenReturn(Optional.empty());

        // Act
        userService.signUp(signUpPayload);

        // Assert
        verify(userRepository, times(1)).findByPin(signUpPayload.getPin());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testSignUpPinAlreadyRegistered() {
        // Arrange
        SignUpPayload signUpPayload = new SignUpPayload("1234", "John Doe", "password");

        when(userRepository.findByPin(signUpPayload.getPin())).thenReturn(Optional.of(new User()));

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.signUp(signUpPayload));
        assertEquals("Pin already registered", exception.getMessage());

        // Ensure that save and additional operations are not performed
        verify(userRepository, never()).save(any());
    }

    @Test
    void testAddRoleToUser() {
        // Arrange
        String pin = "1234";
        String roleName = "ROLE_USER";

        User mockUser = new User("John Doe", pin, "password", new HashSet<>());

        Role mockRole = new Role(roleName);

        when(userRepository.findByPin(pin)).thenReturn(Optional.of(mockUser));
        when(roleRepository.findByName(roleName)).thenReturn(mockRole);

        // Act
        userService.addRoleToUser(pin, roleName);

        // Assert
        verify(userRepository, times(1)).findByPin(pin);
        verify(roleRepository, times(1)).findByName(roleName);

        assertTrue(mockUser.getRoles().contains(mockRole));
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testAddRoleToUserUserNotFound() {
        // Arrange
        String pin = "1234";
        String roleName = "ROLE_USER";

        when(userRepository.findByPin(pin)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.addRoleToUser(pin, roleName));

        // Ensure that findByName and save are not performed
        verify(roleRepository, never()).findByName(roleName);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testAddRoleToUserRoleNotFound() {
        // Arrange
        String pin = "1234";
        String roleName = "ROLE_USER";

        User mockUser = new User("John Doe", pin, "password");

        when(userRepository.findByPin(pin)).thenReturn(Optional.of(mockUser));
        when(roleRepository.findByName(roleName)).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.addRoleToUser(pin, roleName));

        // Ensure that user.getRoles().add is not performed
        verify(userRepository, never()).save(any());
    }
}
