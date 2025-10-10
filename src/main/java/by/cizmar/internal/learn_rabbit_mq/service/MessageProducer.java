package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.EventTicketsPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.VendorEventsPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    @Qualifier(AppConstants.JSON_RABBIT_TEMPLATE)
    private final RabbitTemplate jsonRabbitTemplate;

    @Qualifier(AppConstants.STRING_RABBIT_TEMPLATE)
    private final RabbitTemplate simpleRabbitTemplate;

    public void sendSimpleConnectionMessage(String message) {
        simpleRabbitTemplate.convertAndSend(AppConstants.SIMPLE_CONNECTION_QUEUE, message);
    }

    public void sendVendorEventsMessage(VendorEventsPayload message) {
        jsonRabbitTemplate.convertAndSend(AppConstants.VENDOR_EVENTS_QUEUE, message);
    }

    public void sendEventTicketsMessage(EventTicketsPayload message) {
        jsonRabbitTemplate.convertAndSend(AppConstants.EVENT_TICKETS_QUEUE, message);
    }
}
