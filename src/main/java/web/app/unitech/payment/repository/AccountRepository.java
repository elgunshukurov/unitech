package web.app.unitech.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.unitech.payment.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<List<Account>> getAccountByUser_Pin(String userPin);

    Optional<Account> getAccountByAccountNumber(String accountNumber);
}
