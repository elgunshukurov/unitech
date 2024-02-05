package web.app.unitech.payment.services;

import web.app.unitech.payment.models.Account;
import web.app.unitech.payment.models.AccountDto;
import web.app.unitech.payment.models.AccountTransferDto;

import java.util.List;

public interface AccountService {
    List<Account> getAccountsByPin(String pin);

    void addAccount(String pin);

    void increaseAccountBalance(AccountDto accountDto);

    void decreaseAccountBalance(AccountDto accountDto);

    void deactivateAccount(AccountDto accountDto);

    void activateAccount(AccountDto accountDto);

    List<Account> getAllAccountsByPin(String pin);

    void transfer(AccountTransferDto dto);
}
