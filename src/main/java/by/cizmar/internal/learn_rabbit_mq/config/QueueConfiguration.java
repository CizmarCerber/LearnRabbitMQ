package by.cizmar.internal.learn_rabbit_mq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "by.cizmar.internal.learn-rabbit-mq.queue")
public class QueueConfiguration {

    private String simpleConnection;
    private String vendorEvents;
    private String eventTickets;

    @Bean
    public Queue simpleConnectionQueue() {
        return new Queue(simpleConnection, false);
    }

    @Bean
    public Queue vendorEventsQueue() {
        return new Queue(vendorEvents, false);
    }

    @Bean
    public Queue eventTicketsQueue() {
        return new Queue(eventTickets, false);
    }

}
