package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.EventTicketsPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.VendorEventsPayload;
import com.rabbitmq.client.Channel;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;

@Slf4j
@Configuration
public class MessageListener {

    @RabbitListener(queues = "#{appProperties.queue.simpleString}",
            containerFactory = AppConstants.STRING_RABBIT_CONTAINER_FACTORY)
    public void listenSimpleStringMessage(@Payload @Valid String message) {
        log.info("Successfully received Simple String Message: [{}]", message);
    }

    @RabbitListener(queues = "#{appProperties.queue.simpleInteger}",
            containerFactory = AppConstants.STRING_RABBIT_CONTAINER_FACTORY)
    public void listenSimpleIntegerMessage(@Payload @Valid Integer message) {
        log.info("Successfully received Simple Integer Message: [{}]", message);
    }


    @RabbitListener(queues = "#{appProperties.queue.vendorEvents}",
            containerFactory = AppConstants.JSON_RABBIT_CONTAINER_FACTORY)
    public void listenVendorEventsMessage(@Payload @Valid VendorEventsPayload payload) {
        log.info("Successfully received VendorEventsPayload with id=[{}]: [{}]", payload.getEventUuid(), payload);
    }

    @RabbitListener(queues = "#{appProperties.queue.eventTickets}",
            containerFactory = AppConstants.JSON_RABBIT_CONTAINER_FACTORY)
    public void listenEventTicketsMessage(@Payload @Valid EventTicketsPayload payload) {
        log.info("Successfully received EventTicketsPayload with id=[{}]: [{}]", payload.getEventUuid(), payload);
    }

    @RabbitListener(queues = "#{appProperties.queue.manualAck}",
            ackMode = "MANUAL",
            containerFactory = AppConstants.STRING_RABBIT_CONTAINER_FACTORY)
    public void listenSimpleConnectionMessage(@Payload String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("Successfully received ManualAckMessage with: [{}]", message);
        try {
            Integer value = Integer.parseInt(message);
            log.info("Message is Integer [{}] - ack delivery", value);
            channel.basicAck(tag, false);
        } catch (NumberFormatException nfe) {
            // should move to DLQ
            log.info("Message isn't Integer - nack delivery, don't requeue");
            channel.basicNack(tag, false, false);
        }
    }

}
