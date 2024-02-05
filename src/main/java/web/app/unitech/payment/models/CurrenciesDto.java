package web.app.unitech.payment.models;

import lombok.Data;

@Data
public class CurrenciesDto {
    private final String baseCurrency;
    private final String currencies;
}
