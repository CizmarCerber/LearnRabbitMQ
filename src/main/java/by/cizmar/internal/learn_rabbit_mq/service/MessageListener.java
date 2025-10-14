package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.EventTicketsPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.VendorEventsPayload;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@Configuration
public class MessageListener {

    @RabbitListener(queues = "#{appProperties.queue.simpleConnection}",
            containerFactory = AppConstants.STRING_RABBIT_CONTAINER_FACTORY)
    public void listenSimpleConnectionMessage(@Payload @Valid String message) {
        log.info("Successfully received SimpleConnectionMessage with: [{}]", message);
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

}
