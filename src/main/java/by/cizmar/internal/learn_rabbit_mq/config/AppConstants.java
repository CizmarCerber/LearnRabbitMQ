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
    public static final String SCHEDULED_DEDUPLICATED_PRODUCER_TASK = "scheduled-deduplicated-producer-task";
    public static final String SCHEDULED_WITHOUT_DEDUPLICATED_CONSUMER_TASK = "scheduled-consumer-without-deduplicated-task";
    public static final String SCHEDULED_WITH_DEDUPLICATED_CONSUMER_TASK = "scheduled-consumer-with-deduplicated-task";
    public static final String SCHEDULED_TASK_PREFIX = "QueueScheduler-";

    public static final String DEDUPLICATION_HEADER = "x-deduplication-header";

}
