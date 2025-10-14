package by.cizmar.internal.learn_rabbit_mq.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "by.cizmar.internal.learn-rabbit-mq")
public class AppProperties {
    private ExchangeProperties exchange;
    private QueuePropetries queue;
    private RoutingProperties routing;
}
