package by.cizmar.internal.learn_rabbit_mq.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class RabbitMQConfiguration {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageConverter stringMessageConverter() {
        return new SimpleMessageConverter();
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

    @Bean(AppConstants.STRING_RABBIT_CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory jsonRabbitListener(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(stringMessageConverter());
        return factory;
    }

    @Bean(AppConstants.JSON_RABBIT_CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory stringRabbitListener(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
