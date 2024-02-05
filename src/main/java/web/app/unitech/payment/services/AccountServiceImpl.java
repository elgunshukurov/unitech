package web.app.unitech.payment.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.app.unitech.payment.models.Account;
import web.app.unitech.payment.models.AccountDto;
import web.app.unitech.payment.models.AccountTransferDto;
import web.app.unitech.payment.repository.AccountRepository;
import web.app.unitech.user.models.User;
import web.app.unitech.user.services.UserService;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final UserService userService;

    @Override
    public List<Account> getAccountsByPin(String pin) {
        if (accountRepository.getAccountByUser_Pin(pin).isPresent()) {
            return accountRepository
                    .getAccountByUser_Pin(pin)
                    .get()
                    .stream()
                    .filter(Account::isActive)
                    .toList();
        } else {
            throw new RuntimeException("User has no account!");
        }
    }

    @Override
    public void addAccount(String pin) {
        String accountNum = generateRandomAccountNumber();
        User user = userService.get(pin);

        Account account = new Account();
//        account.setAccountNumber(accountNum);
        account.setAccountNumber("6629438670");
        account.setActive(true);
        account.setBalance(0);
        account.setUser(user);

        accountRepository.save(account);
    }

    @Override
    public void increaseAccountBalance(AccountDto accountDto) {
        Account account = null;

        if (accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).isPresent()){
            account = accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).get();
        } else {
            throw new RuntimeException("User has not account account number with " + accountDto.getAccountNumber());
        }

        account.setBalance(account.getBalance() + accountDto.getAmount());

        accountRepository.save(account);
    }

    @Override
    public void decreaseAccountBalance(AccountDto accountDto) {
        Account account = null;

        if (accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).isPresent()){
            account = accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).get();
        } else {
            throw new RuntimeException("User has not account account number with " + accountDto.getAccountNumber());
        }

        if (account.getBalance()<accountDto.getAmount())
            throw new RuntimeException("Insufficient balance!");

        account.setBalance(account.getBalance() - accountDto.getAmount());

        accountRepository.save(account);
    }

    @Override
    public void deactivateAccount(AccountDto accountDto) {

        Account account = null;

        if (accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).isPresent()){
            account = accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).get();
        } else {
            throw new RuntimeException("User has not account account number with " + accountDto.getAccountNumber());
        }

        if (!account.isActive()) throw new RuntimeException("Account already deactivate!");

        account.setActive(false);

        accountRepository.save(account);
    }

    @Override
    public void activateAccount(AccountDto accountDto) {
        Account account = null;

        if (accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).isPresent()){
            account = accountRepository.getAccountByAccountNumber(accountDto.getAccountNumber()).get();
        } else {
            throw new RuntimeException("User has not account account number with " + accountDto.getAccountNumber());
        }

        if (account.isActive()) throw new RuntimeException("Account already active!");

        account.setActive(true);

        accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccountsByPin(String pin) {
        if (accountRepository.getAccountByUser_Pin(pin).isPresent()) {
            return accountRepository
                    .getAccountByUser_Pin(pin)
                    .get();
        } else {
            throw new RuntimeException("User has no account!");
        }
    }

    @Override
    public void transfer(AccountTransferDto dto) {
        Account from = null;
        Account to = null;

        if (accountRepository.getAccountByAccountNumber(dto.getFromAccount()).isPresent()) {
            from = accountRepository.getAccountByAccountNumber(dto.getFromAccount()).get();
        } else {
            throw new RuntimeException("From account does not exist");
        }

        if (accountRepository.getAccountByAccountNumber(dto.getToAccount()).isPresent()) {
            to = accountRepository.getAccountByAccountNumber(dto.getToAccount()).get();
        } else {
            throw new RuntimeException("To account does not exist");
        }

        checkTransfer(from, to, dto);

        from.setBalance(from.getBalance() - dto.getAmount());
        to.setBalance(to.getBalance() + dto.getAmount());

        accountRepository.saveAll(List.of(from, to));

    }

    private static void checkTransfer(Account from, Account to, AccountTransferDto transferDto) {
        if (from.getAccountNumber().equals(to.getAccountNumber()))
            throw new RuntimeException("You can not send money to the same account!");

        if (from.getBalance()<transferDto.getAmount())
            throw new RuntimeException("Insufficient balance!");

        if (!to.isActive())
            throw new RuntimeException("You can not send money to the deactivated account!");
    }

    private static String generateRandomAccountNumber() {
        int accountNumberLength = 10;

        StringBuilder accountNumberBuilder = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < accountNumberLength; i++) {
            int digit = random.nextInt(10);
            accountNumberBuilder.append(digit);
        }

        return accountNumberBuilder.toString();
    }

}
