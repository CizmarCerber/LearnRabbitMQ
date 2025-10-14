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

    @Qualifier(AppConstants.JSON_RABBIT_TEMPLATE)
    private final RabbitTemplate jsonRabbitTemplate;

    @Qualifier(AppConstants.STRING_RABBIT_TEMPLATE)
    private final RabbitTemplate stringRabbitTemplate;

    private final AppProperties appProperties;

    public void sendSimpleStringMessage(Object message, Boolean sendDirect) {
        if (Boolean.TRUE.equals(sendDirect)) {
            logMessageSentToExchangeRoutingKey(message,appProperties.getExchange().getDirect(), appProperties.getRouting().getSimpleString());
            stringRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getSimpleString(), message);
        } else {
            logMessageSentToQueue(message, appProperties.getQueue().getSimpleString());
            stringRabbitTemplate.convertAndSend(appProperties.getQueue().getSimpleString(), message);
        }
    }

    public void sendSimpleIntegerMessage(Object message, Boolean sendDirect) {
        if (Boolean.TRUE.equals(sendDirect)) {
            logMessageSentToExchangeRoutingKey(message,appProperties.getExchange().getDirect(), appProperties.getRouting().getSimpleInteger());
            stringRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getSimpleInteger(), message);
        } else {
            logMessageSentToQueue(message, appProperties.getQueue().getSimpleInteger());
            stringRabbitTemplate.convertAndSend(appProperties.getQueue().getSimpleInteger(), message);
        }
    }

    public void sendVendorEventsMessage(Object message, Boolean sendDirect) {
        if (Boolean.TRUE.equals(sendDirect)) {
            logMessageSentToExchangeRoutingKey(message,appProperties.getExchange().getDirect(), appProperties.getRouting().getVendorEvents());
            jsonRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getVendorEvents(), message);
        } else {
            logMessageSentToQueue(message, appProperties.getQueue().getVendorEvents());
            jsonRabbitTemplate.convertAndSend(appProperties.getQueue().getVendorEvents(), message);
        }
    }

    public void sendEventTicketsMessage(Object message, Boolean sendDirect) {
        if (Boolean.TRUE.equals(sendDirect)) {
            logMessageSentToExchangeRoutingKey(message,appProperties.getExchange().getDirect(), appProperties.getRouting().getEventTickets());
            jsonRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getEventTickets(), message);
        } else {
            logMessageSentToQueue(message, appProperties.getQueue().getEventTickets());
            jsonRabbitTemplate.convertAndSend(appProperties.getQueue().getEventTickets(), message);
        }
    }

    public void sendManualAckMessage(Object message) {
        logMessageSentToQueue(message, appProperties.getQueue().getManualAck());
        stringRabbitTemplate.convertAndSend(appProperties.getQueue().getManualAck(), message);
    }

    public void sendCustomMessageToExchangeRoutingKey(Object message, String exchange, String routingKey) {
        logMessageSentToExchangeRoutingKey(message, exchange, routingKey);
        jsonRabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    private void logMessageSentToQueue(Object message, String queueName) {
        log.info("Sending to queue {} message {}", queueName, message);
    }

    private void logMessageSentToExchangeRoutingKey(Object message, String exchange, String routingKey) {
        log.info("Sending to exchange [routing] {} [{}] message {}", exchange, routingKey, message);
    }
}
