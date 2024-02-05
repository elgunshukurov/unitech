package web.app.unitech.payment.services;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.app.unitech.payment.models.Currency;

@FeignClient(name = "currency-service", url = "https://api.currencyapi.com")
public interface CurrencyApiClient {

    @GetMapping("/v3/latest")//?apikey=YOUR-API-KEY&base_currency=EUR&currencies=USD,AED,CHF
    Currency getCurrencies(
            @RequestParam(value = "apikey", required = false) String apikey,
            @RequestParam(value = "currencies", required = false) String currencies,
            @RequestParam(value = "baseCurrency", required = false) String baseCurrency
            );

}
