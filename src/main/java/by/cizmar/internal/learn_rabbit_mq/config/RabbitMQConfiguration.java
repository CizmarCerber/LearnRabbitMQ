package by.cizmar.internal.learn_rabbit_mq.config;

import by.cizmar.internal.learn_rabbit_mq.config.properties.AppProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@SuppressWarnings("unused")
@Setter
@Getter
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final AppProperties appProperties;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageConverter stringMessageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    public Queue simpleConnectionQueue() {
        return new Queue(appProperties.getQueue().getSimpleConnection(), false);
    }

    @Bean
    public Queue vendorEventsQueue() {
        return new Queue(appProperties.getQueue().getVendorEvents(), false);
    }

    @Bean
    public Queue eventTicketsQueue() {
        return new Queue(appProperties.getQueue().getEventTickets(), false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(appProperties.getExchange().getDirect());
    }

    @Bean
    public Binding bindingDirectSimple(Queue simpleConnectionQueue, DirectExchange exchange) {
        return BindingBuilder.bind(simpleConnectionQueue).to(exchange).with(appProperties.getRouting().getSimpleConnection());
    }

    @Bean
    public Binding bindingDirectVendorEvents(Queue vendorEventsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(vendorEventsQueue).to(exchange).with(appProperties.getRouting().getVendorEvents());
    }

    @Bean
    public Binding bindingDirectEventTickets(Queue eventTicketsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(eventTicketsQueue).to(exchange).with(appProperties.getRouting().getEventTickets());
    }

    @Bean(AppConstants.STRING_RABBIT_TEMPLATE)
    public RabbitTemplate stringRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(stringMessageConverter());
        return template;
    }

    @Bean(AppConstants.JSON_RABBIT_TEMPLATE)
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .backOffOptions(1000, 2.0, 10000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

    @Bean(AppConstants.STRING_RABBIT_CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory jsonRabbitListener(ConnectionFactory connectionFactory,
                                                                   RetryOperationsInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(stringMessageConverter());
        factory.setAdviceChain(retryInterceptor);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean(AppConstants.JSON_RABBIT_CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory stringRabbitListener(ConnectionFactory connectionFactory,
                                                                     RetryOperationsInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setAdviceChain(retryInterceptor);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }
}
