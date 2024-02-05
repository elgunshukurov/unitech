package web.app.unitech.payment.services;

import web.app.unitech.payment.models.CurrenciesDto;
import web.app.unitech.payment.models.Currency;

import java.util.List;

public interface CurrencyService {
    Currency getCurrencies(CurrenciesDto currenciesDto);

}
