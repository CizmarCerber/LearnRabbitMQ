package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.config.properties.AppProperties;
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

    private final AppProperties appProperties;

    public void sendSimpleConnectionMessage(Object message, Boolean sendDirect) {
        log.info(LOG_MESSAGE, appProperties.getQueue().getSimpleConnection(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            simpleRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getSimpleConnection(), message);
        } else {
            simpleRabbitTemplate.convertAndSend(appProperties.getQueue().getSimpleConnection(), message);
        }
    }

    public void sendVendorEventsMessage(Object message, Boolean sendDirect) {
        log.info(LOG_MESSAGE, appProperties.getQueue().getVendorEvents(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            jsonRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getVendorEvents(), message);
        } else {
            jsonRabbitTemplate.convertAndSend(appProperties.getQueue().getVendorEvents(), message);
        }
    }

    public void sendEventTicketsMessage(Object message, Boolean sendDirect) {
        log.info(LOG_MESSAGE, appProperties.getQueue().getEventTickets(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            jsonRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getEventTickets(), message);
        } else {
            jsonRabbitTemplate.convertAndSend(appProperties.getQueue().getEventTickets(), message);
        }
    }
}
