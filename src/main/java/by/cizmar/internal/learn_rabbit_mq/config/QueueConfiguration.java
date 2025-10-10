package by.cizmar.internal.learn_rabbit_mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class QueueConfiguration {

    @Bean
    public Queue vendorEventsQueue() {
        return new Queue(AppConstants.VENDOR_EVENTS_QUEUE, false);
    }

    @Bean
    public Queue eventTicketsQueue() {
        return new Queue(AppConstants.EVENT_TICKETS_QUEUE, false);
    }

    @Bean
    public Queue simpleConnectionQueue() {
        return new Queue(AppConstants.SIMPLE_CONNECTION_QUEUE, false);
    }
}
