package by.cizmar.internal.learn_rabbit_mq.config.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueueProperties {

    private String simpleString;
    private String simpleInteger;
    private String vendorEvents;
    private String eventTickets;
    private String manualAck;
    private String scheduled;
    private String dlq;

}
