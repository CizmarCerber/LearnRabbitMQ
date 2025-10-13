package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.config.QueueConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {

    private static final String LOG_MESSAGE = "Sending to queue {} message {}";

    @Qualifier(AppConstants.JSON_RABBIT_TEMPLATE)
    private final RabbitTemplate jsonRabbitTemplate;

    @Qualifier(AppConstants.STRING_RABBIT_TEMPLATE)
    private final RabbitTemplate simpleRabbitTemplate;

    private final QueueConfiguration queueConfiguration;

    public void sendSimpleConnectionMessage(Object message, Boolean sendDirect) {
        log.info(LOG_MESSAGE, queueConfiguration.getSimpleConnection(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            simpleRabbitTemplate.convertAndSend(AppConstants.DIRECT_EXCHANGE, AppConstants.ROUTING_SIMPLE, message);
        } else {
            simpleRabbitTemplate.convertAndSend(queueConfiguration.getSimpleConnection(), message);
        }
    }

    public void sendVendorEventsMessage(Object message, Boolean sendDirect) {
        log.info(LOG_MESSAGE, queueConfiguration.getVendorEvents(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            jsonRabbitTemplate.convertAndSend(AppConstants.DIRECT_EXCHANGE, AppConstants.ROUTING_VENDOR_EVENTS, message);
        } else {
            jsonRabbitTemplate.convertAndSend(queueConfiguration.getVendorEvents(), message);
        }
    }

    public void sendEventTicketsMessage(Object message, Boolean sendDirect) {
        log.info(LOG_MESSAGE, queueConfiguration.getEventTickets(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            jsonRabbitTemplate.convertAndSend(AppConstants.DIRECT_EXCHANGE, AppConstants.ROUTING_EVENT_TICKETS, message);
        } else {
            jsonRabbitTemplate.convertAndSend(queueConfiguration.getEventTickets(), message);
        }
    }
}
