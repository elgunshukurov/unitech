package web.app.unitech.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.app.unitech.payment.models.Account;
import web.app.unitech.payment.models.AccountDto;
import web.app.unitech.payment.models.AccountTransferDto;
import web.app.unitech.payment.services.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{pin}")
    public List<Account> getAccountsByPin(@PathVariable String pin) {
        return accountService.getAccountsByPin(pin);
    }

    @GetMapping("/all/{pin}")
    public List<Account> getAllAccountsByPin(@PathVariable String pin) {
        return accountService.getAllAccountsByPin(pin);
    }

    @PostMapping("/{pin}")
    public ResponseEntity<Void> addAccount(@PathVariable String pin) {
        accountService.addAccount(pin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody AccountTransferDto dto) {
        accountService.transfer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @PatchMapping("/increase")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> increaseAccountBalance(@RequestBody AccountDto accountDto) {
        accountService.increaseAccountBalance(accountDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/decrease")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> decreaseAccountBalance(@RequestBody AccountDto accountDto) {
        accountService.decreaseAccountBalance(accountDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(@RequestBody AccountDto accountDto) {
        accountService.deactivateAccount(accountDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/activate")
    public ResponseEntity<Void> activateAccount(@RequestBody AccountDto accountDto) {
        accountService.activateAccount(accountDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
