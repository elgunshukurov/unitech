package web.app.unitech.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.unitech.payment.models.CurrenciesDto;
import web.app.unitech.payment.models.Currency;
import web.app.unitech.payment.services.CacheServiceImpl;
import web.app.unitech.payment.services.CurrencyService;
import web.app.unitech.payment.services.CurrencyServiceImpl;


@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CacheServiceImpl cacheService;

    @GetMapping
    public Currency getCurrencies(@RequestBody CurrenciesDto currenciesDto){
        return currencyService.getCurrencies(currenciesDto);
    }

    @PostMapping("/evict")
    public ResponseEntity<String> evictCache(){
        return ResponseEntity.status(HttpStatus.OK).body(cacheService.evictCache());
    }
}
