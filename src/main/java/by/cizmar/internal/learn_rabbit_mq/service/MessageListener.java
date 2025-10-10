package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.EventTicketsPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.VendorEventsPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MessageListener {

    private static final String NOT_EXPECTED_EVENT_PAYLOAD = "Can't parse object as event: {}";

    @RabbitListener(queues = AppConstants.SIMPLE_CONNECTION_QUEUE, containerFactory = AppConstants.STRING_RABBIT_CONTAINER_FACTORY)
    public void listenSimpleConnectionMessage(final  String message) {
        log.info("Successfully received SimpleConnectionMessage with: [{}]", message);
    }

    @RabbitListener(queues = AppConstants.VENDOR_EVENTS_QUEUE, containerFactory = AppConstants.JSON_RABBIT_CONTAINER_FACTORY)
    public void listenVendorEventsMessage(final VendorEventsPayload payload) {
        log.info("Successfully received VendorEventsPayload with id=[{}]: [{}]", payload.getEventUuid(), payload);
    }

    @RabbitListener(queues = AppConstants.EVENT_TICKETS_QUEUE, containerFactory = AppConstants.JSON_RABBIT_CONTAINER_FACTORY)
    public void listenEventTicketsMessage(final EventTicketsPayload payload) {
        log.info("Successfully received EventTicketsPayload with id=[{}]: [{}]", payload.getEventUuid(), payload);
    }

}
