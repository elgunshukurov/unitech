package web.app.unitech.payment.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import web.app.unitech.payment.models.CurrenciesDto;
import web.app.unitech.payment.models.Currency;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService{

    private final CurrencyApiClient currencyApiClient;
    private final String apikey = "cur_live_77VYUkIHDKbW38CFBv6Ym7OnwAP9qBbsYWqMVXB2";
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = "getCurrenciesCache", key = "'MySpecCache'")
    public Currency getCurrencies(CurrenciesDto currenciesDto) {
        Thread.sleep(5000);
        String baseCurrency = currenciesDto.getBaseCurrency();
        String currencies = currenciesDto.getCurrencies();
        return currencyApiClient.getCurrencies(apikey, currencies, baseCurrency);
    }

}
