package by.cizmar.internal.learn_rabbit_mq.config.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExchangeProperties {
    private String direct;
    private String fanoutDlx;
}
