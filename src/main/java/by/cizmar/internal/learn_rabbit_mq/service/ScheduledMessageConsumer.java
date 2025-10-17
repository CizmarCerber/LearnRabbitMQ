package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.config.properties.AppProperties;
import by.cizmar.internal.learn_rabbit_mq.dto.request.ConsumerTaskRequest;
import by.cizmar.internal.learn_rabbit_mq.scheduler.ScheduledTasksManager;
import by.cizmar.internal.learn_rabbit_mq.scheduler.task.ConsumerTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledMessageConsumer {

    private final ScheduledTasksManager scheduledTasksManager;
    private final MessageConsumer messageConsumer;
    private final AppProperties appProperties;

    public void rescheduleConsumer(ConsumerTaskRequest request) {
        ConsumerTask consumerTask = new ConsumerTask(messageConsumer, AppConstants.SCHEDULED_CONSUMER_TASK,
                appProperties.getQueue().getScheduled(), request);
        scheduledTasksManager.runScheduledTask(consumerTask, Duration.ofMillis(request.getPeriodInMilliseconds()));
    }

    public void stopConsumer() {
        scheduledTasksManager.cancelTaskIfExists(AppConstants.SCHEDULED_CONSUMER_TASK);
    }

}
