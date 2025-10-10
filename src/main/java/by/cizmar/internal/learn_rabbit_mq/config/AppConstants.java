package by.cizmar.internal.learn_rabbit_mq.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConstants {

    public static final String JSON_RABBIT_TEMPLATE = "jsonRabbitTemplate";
    public static final String STRING_RABBIT_TEMPLATE = "stringRabbitTemplate";

    public static final String JSON_RABBIT_CONTAINER_FACTORY = "jsonRabbitContainerFactory";
    public static final String STRING_RABBIT_CONTAINER_FACTORY = "stringRabbitContainerFactory";

    public static final String SIMPLE_CONNECTION_QUEUE = "simple-connection-queue";
    public static final String VENDOR_EVENTS_QUEUE = "vendor-events-queue";
    public static final String EVENT_TICKETS_QUEUE = "event-tickets-queue";

}
