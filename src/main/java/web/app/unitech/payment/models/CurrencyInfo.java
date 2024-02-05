package web.app.unitech.payment.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrencyInfo implements Serializable {
    private String code;
    private double value;
}
