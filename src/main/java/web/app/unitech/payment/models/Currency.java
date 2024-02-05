package web.app.unitech.payment.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Currency implements Serializable {
    private Meta meta;
    private Map<String, CurrencyInfo> data;
}
