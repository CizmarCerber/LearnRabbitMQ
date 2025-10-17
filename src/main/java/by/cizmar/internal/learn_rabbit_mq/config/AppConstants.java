package by.cizmar.internal.learn_rabbit_mq.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConstants {

    public static final String JSON_RABBIT_TEMPLATE = "jsonRabbitTemplate";
    public static final String STRING_RABBIT_TEMPLATE = "stringRabbitTemplate";

    public static final String JSON_RABBIT_CONTAINER_FACTORY = "jsonRabbitContainerFactory";
    public static final String STRING_RABBIT_CONTAINER_FACTORY = "stringRabbitContainerFactory";

    public static final String SCHEDULED_PRODUCER_TASK = "scheduled-producer-task";
    public static final String SCHEDULED_CONSUMER_TASK = "scheduled-consumer-task";
    public static final String SCHEDULED_TASK_PREFIX = "QueueScheduler-";

}
