package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.config.properties.AppProperties;
import by.cizmar.internal.learn_rabbit_mq.dto.request.ConsumerTaskRequest;
import by.cizmar.internal.learn_rabbit_mq.scheduler.ScheduledTasksManager;
import by.cizmar.internal.learn_rabbit_mq.scheduler.task.ConsumerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ScheduledDeduplicatedMessageConsumer {

    private final ScheduledTasksManager scheduledTasksManager;
    private final MessageConsumer messageConsumer;
    private final AppProperties appProperties;

    public void rescheduleConsumerWithoutDedup(ConsumerTaskRequest request) {
        ConsumerTask consumerTask = new ConsumerTask(messageConsumer, AppConstants.SCHEDULED_WITHOUT_DEDUPLICATED_CONSUMER_TASK,
                appProperties.getQueue().getScheduledDedupWithout(), request);
        scheduledTasksManager.runScheduledTask(consumerTask, Duration.ofMillis(request.getPeriodInMilliseconds()));
    }

    public void rescheduleConsumerWithDedup(ConsumerTaskRequest request) {
        ConsumerTask consumerTask = new ConsumerTask(messageConsumer, AppConstants.SCHEDULED_WITH_DEDUPLICATED_CONSUMER_TASK,
                appProperties.getQueue().getScheduledDedupWith(), request);
        scheduledTasksManager.runScheduledTask(consumerTask, Duration.ofMillis(request.getPeriodInMilliseconds()));
    }

    public void stopConsumerWithoutDedup() {
        scheduledTasksManager.cancelTaskIfExists(AppConstants.SCHEDULED_WITHOUT_DEDUPLICATED_CONSUMER_TASK);
    }

    public void stopConsumerWithDedup() {
        scheduledTasksManager.cancelTaskIfExists(AppConstants.SCHEDULED_WITH_DEDUPLICATED_CONSUMER_TASK);
    }

}
