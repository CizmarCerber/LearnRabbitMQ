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

    public void sendSimpleConnectionMessage(Object message) {
        log.info(LOG_MESSAGE, queueConfiguration.getSimpleConnection(), message);
        simpleRabbitTemplate.convertAndSend(queueConfiguration.getSimpleConnection(), message);
    }

    public void sendVendorEventsMessage(Object message) {
        log.info(LOG_MESSAGE, queueConfiguration.getVendorEvents(), message);
        jsonRabbitTemplate.convertAndSend(queueConfiguration.getVendorEvents(), message);
    }

    public void sendEventTicketsMessage(Object message) {
        log.info(LOG_MESSAGE, queueConfiguration.getEventTickets(), message);
        jsonRabbitTemplate.convertAndSend(queueConfiguration.getEventTickets(), message);
    }
}
