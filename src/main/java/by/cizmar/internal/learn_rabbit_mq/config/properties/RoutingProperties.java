package by.cizmar.internal.learn_rabbit_mq.config.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoutingProperties {
    private String simpleConnection;
    private String vendorEvents;
    private String eventTickets;
}
