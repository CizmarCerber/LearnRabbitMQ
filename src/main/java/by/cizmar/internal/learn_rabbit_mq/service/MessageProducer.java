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
        logMessageSentToQueue(appProperties.getQueue().getSimpleString(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            stringRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getSimpleString(), message);
        } else {
            stringRabbitTemplate.convertAndSend(appProperties.getQueue().getSimpleString(), message);
        }
    }

    public void sendSimpleIntegerMessage(Object message, Boolean sendDirect) {
        logMessageSentToQueue(appProperties.getQueue().getSimpleInteger(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            stringRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getSimpleInteger(), message);
        } else {
            stringRabbitTemplate.convertAndSend(appProperties.getQueue().getSimpleInteger(), message);
        }
    }

    public void sendVendorEventsMessage(Object message, Boolean sendDirect) {
        logMessageSentToQueue(appProperties.getQueue().getVendorEvents(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            jsonRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getVendorEvents(), message);
        } else {
            jsonRabbitTemplate.convertAndSend(appProperties.getQueue().getVendorEvents(), message);
        }
    }

    public void sendEventTicketsMessage(Object message, Boolean sendDirect) {
        logMessageSentToQueue(appProperties.getQueue().getEventTickets(), message);
        if (Boolean.TRUE.equals(sendDirect)) {
            jsonRabbitTemplate.convertAndSend(appProperties.getExchange().getDirect(), appProperties.getRouting().getEventTickets(), message);
        } else {
            jsonRabbitTemplate.convertAndSend(appProperties.getQueue().getEventTickets(), message);
        }
    }

    public void sendManualAckMessage(Object message) {
        logMessageSentToQueue(appProperties.getQueue().getManualAck(), message);
        stringRabbitTemplate.convertAndSend(appProperties.getQueue().getManualAck(), message);
    }

    private void logMessageSentToQueue(String queueName, Object message) {
        log.info("Sending to queue {} message {}", queueName, message);
    }
}
