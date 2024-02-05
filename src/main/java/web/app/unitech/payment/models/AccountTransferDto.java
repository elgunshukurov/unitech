package web.app.unitech.payment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferDto {
    private String fromAccount;
    private String toAccount;
    private double amount;

}
