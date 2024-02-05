package web.app.unitech;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import web.app.unitech.payment.models.Account;
import web.app.unitech.payment.models.AccountTransferDto;
import web.app.unitech.payment.repository.AccountRepository;
import web.app.unitech.payment.services.AccountServiceImpl;
import web.app.unitech.user.models.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountControllerTest {


    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void testGetAccountsByPin() {
        String testPin = "1234";
        User testUser = new User();
        Account activeAccount1 = new Account(testUser, true);
        Account activeAccount2 = new Account(testUser, true);
        Account inactiveAccount = new Account(testUser, false);

        when(accountRepository.getAccountByUser_Pin(testPin)).thenReturn(Optional.of(List.of(activeAccount1, inactiveAccount, activeAccount2)));


        List<Account> result = accountService.getAccountsByPin(testPin);


        assertEquals(2, result.size());
        assertTrue(result.contains(activeAccount1));
        assertTrue(result.contains(activeAccount2));
        assertFalse(result.contains(inactiveAccount));
    }

    @Test
    void testGetAccountsByPinNoAccounts() {
        String testPin = "5678";

        when(accountRepository.getAccountByUser_Pin(testPin)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.getAccountsByPin(testPin));
        assertEquals("User has no account!", exception.getMessage());

        verify(accountRepository, times(1)).getAccountByUser_Pin(testPin);
    }

    @Test
    void testTransfer() {
        // Arrange
        String fromAccountNumber = "123";
        String toAccountNumber = "456";
        double amount = 50.0;

        User testUser = new User();
        Account fromAccount = new Account(testUser, true, fromAccountNumber, 100.0);
        Account toAccount = new Account(testUser, true, toAccountNumber, 50.0);

        AccountTransferDto transferDto = new AccountTransferDto(fromAccountNumber, toAccountNumber, amount);

        when(accountRepository.getAccountByAccountNumber(fromAccountNumber)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.getAccountByAccountNumber(toAccountNumber)).thenReturn(Optional.of(toAccount));

        // Act
        accountService.transfer(transferDto);

        // Assert
        assertEquals(50.0, fromAccount.getBalance());
        assertEquals(100.0, toAccount.getBalance());

        verify(accountRepository, times(1)).saveAll(List.of(fromAccount, toAccount));
    }

    @Test
    void testTransferSameAccount() {
        // Arrange
        String accountNumber = "123";
        double amount = 50.0;

        User testUser = new User();
        Account account = new Account(testUser, true, accountNumber, 100.0);

        AccountTransferDto transferDto = new AccountTransferDto(accountNumber, accountNumber, amount);

        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.transfer(transferDto));
        assertEquals("You can not send money to the same account!", exception.getMessage());

        // Ensure that no save operation is performed
        verify(accountRepository, never()).saveAll(anyList());
    }

    @Test
    void testTransferInsufficientBalance() {
        // Arrange
        String fromAccountNumber = "123";
        String toAccountNumber = "456";
        double amount = 150.0; // More than the balance

        User testUser = new User();
        Account fromAccount = new Account(testUser, true, fromAccountNumber, 100.0);
        Account toAccount = new Account(testUser, true, toAccountNumber, 50.0);

        AccountTransferDto transferDto = new AccountTransferDto(fromAccountNumber, toAccountNumber, amount);

        when(accountRepository.getAccountByAccountNumber(fromAccountNumber)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.getAccountByAccountNumber(toAccountNumber)).thenReturn(Optional.of(toAccount));

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.transfer(transferDto));
        assertEquals("Insufficient balance!", exception.getMessage());

        // Ensure that no save operation is performed
        verify(accountRepository, never()).saveAll(anyList());
    }

    @Test
    void testTransferToInactiveAccount() {
        // Arrange
        String fromAccountNumber = "123";
        String toAccountNumber = "456";
        double amount = 50.0;

        User testUser = new User();
        Account fromAccount = new Account(testUser, true, fromAccountNumber, 100.0);
        Account toAccount = new Account(testUser, false, toAccountNumber, 50.0);

        AccountTransferDto transferDto = new AccountTransferDto(fromAccountNumber, toAccountNumber, amount);

        when(accountRepository.getAccountByAccountNumber(fromAccountNumber)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.getAccountByAccountNumber(toAccountNumber)).thenReturn(Optional.of(toAccount));

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.transfer(transferDto));
        assertEquals("You can not send money to the deactivated account!", exception.getMessage());

        // Ensure that no save operation is performed
        verify(accountRepository, never()).saveAll(anyList());
    }

    @Test
    void testTransferToNonExistingAccount() {
        // Arrange
        String fromAccountNumber = "123";
        String toAccountNumber = "789"; // Non-existing account
        double amount = 50.0;

        User testUser = new User();
        Account fromAccount = new Account(testUser, true, fromAccountNumber, 100.0);

        AccountTransferDto transferDto = new AccountTransferDto(fromAccountNumber, toAccountNumber, amount);

        when(accountRepository.getAccountByAccountNumber(fromAccountNumber)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.getAccountByAccountNumber(toAccountNumber)).thenReturn(Optional.empty());

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.transfer(transferDto));
        assertEquals("To account does not exist", exception.getMessage());

        // Ensure that no save operation is performed
        verify(accountRepository, never()).saveAll(anyList());
    }
}
