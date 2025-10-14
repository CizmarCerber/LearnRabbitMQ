package by.cizmar.internal.learn_rabbit_mq.config;

import by.cizmar.internal.learn_rabbit_mq.config.properties.AppProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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
    public Queue simpleStringQueue() {
        return QueueBuilder.durable(appProperties.getQueue().getSimpleString())
                .withArgument("x-dead-letter-exchange", appProperties.getExchange().getFanoutDlx())
                .build();
    }

    @Bean
    public Queue simpleIntegerQueue() {
        return QueueBuilder.durable(appProperties.getQueue().getSimpleInteger())
                .withArgument("x-dead-letter-exchange", appProperties.getExchange().getFanoutDlx())
                .build();
    }

    @Bean
    public Queue vendorEventsQueue() {
        return QueueBuilder.durable(appProperties.getQueue().getVendorEvents())
                .withArgument("x-dead-letter-exchange", appProperties.getExchange().getFanoutDlx())
                .build();
    }

    @Bean
    public Queue eventTicketsQueue() {
        return QueueBuilder.durable(appProperties.getQueue().getEventTickets())
                .withArgument("x-dead-letter-exchange", appProperties.getExchange().getFanoutDlx())
                .build();
    }

    @Bean
    public Queue manualAckQueue() {
        return QueueBuilder.durable(appProperties.getQueue().getManualAck())
                .withArgument("x-dead-letter-exchange", appProperties.getExchange().getFanoutDlx())
                .build();
    }

    @Bean
    public Queue scheduledQueue() {
        return QueueBuilder.durable(appProperties.getQueue().getScheduled())
                .withArgument("x-dead-letter-exchange", appProperties.getExchange().getFanoutDlx())
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(appProperties.getQueue().getDlq()).build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(appProperties.getExchange().getDirect());
    }

    @Bean
    public FanoutExchange scheduledFanoutExchange() {
        return new FanoutExchange(appProperties.getExchange().getScheduledFanout());
    }

    @Bean
    public FanoutExchange fanoutDlxExchange() {
        return new FanoutExchange(appProperties.getExchange().getFanoutDlx());
    }

    @Bean
    public Binding bindingDirectSimpleString(Queue simpleStringQueue, DirectExchange exchange) {
        return BindingBuilder.bind(simpleStringQueue).to(exchange).with(appProperties.getRouting().getSimpleString());
    }

    @Bean
    public Binding bindingDirectSimpleInteger(Queue simpleIntegerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(simpleIntegerQueue).to(exchange).with(appProperties.getRouting().getSimpleInteger());
    }

    @Bean
    public Binding bindingDirectVendorEvents(Queue vendorEventsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(vendorEventsQueue).to(exchange).with(appProperties.getRouting().getVendorEvents());
    }

    @Bean
    public Binding bindingDirectEventTickets(Queue eventTicketsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(eventTicketsQueue).to(exchange).with(appProperties.getRouting().getEventTickets());
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, FanoutExchange fanoutDlxExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(fanoutDlxExchange);
    }

    @Bean
    public Binding scheduledBinding(Queue scheduledQueue, FanoutExchange scheduledFanoutExchange) {
        return BindingBuilder.bind(scheduledQueue).to(scheduledFanoutExchange);
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
