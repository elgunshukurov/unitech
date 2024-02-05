package web.app.unitech;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import web.app.unitech.payment.models.CurrenciesDto;
import web.app.unitech.payment.models.Currency;
import web.app.unitech.payment.models.CurrencyInfo;
import web.app.unitech.payment.models.Meta;
import web.app.unitech.payment.services.CurrencyApiClient;
import web.app.unitech.payment.services.CurrencyServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CurrencyControllerTest {

    @Autowired
    private CurrencyServiceImpl currencyService;

    @MockBean
    private CurrencyApiClient currencyApiClient;

    @Test
    void testGetCurrencies() {
        // Given
        CurrenciesDto currenciesDto = new CurrenciesDto("USD", "EUR,GBP");

        Currency mockCurrency = new Currency();

        Meta meta = new Meta();
        meta.setLastUpdatedAt("2024-02-02T23:59:59Z");
        mockCurrency.setMeta(meta);

        CurrencyInfo currencyInfo1 = new CurrencyInfo();
        currencyInfo1.setCode("EUR");
        currencyInfo1.setValue(1.346110179);

        CurrencyInfo currencyInfo2 = new CurrencyInfo();
        currencyInfo1.setCode("GBP");
        currencyInfo1.setValue(1.012340179);

        mockCurrency.setData(Map.of("EUR",currencyInfo1, "GBP",currencyInfo2));

        when(currencyApiClient.getCurrencies(any(), any(), any())).thenReturn(mockCurrency);

        // When
        Currency result = currencyService.getCurrencies(currenciesDto);

        // Then

        assertEquals("2024-02-02T23:59:59Z", result.getMeta().getLastUpdatedAt());
        assertEquals(2, result.getData().size());

        verify(currencyApiClient, times(1)).getCurrencies(any(), any(), any());
    }
}
