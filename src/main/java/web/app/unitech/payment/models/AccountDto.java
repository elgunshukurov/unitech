package web.app.unitech.payment.models;

import lombok.Data;

@Data
public class AccountDto {
    private String accountNumber;
    private double amount;
    private boolean active;
}
