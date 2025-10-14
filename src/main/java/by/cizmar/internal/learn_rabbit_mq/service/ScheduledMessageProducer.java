package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.config.properties.AppProperties;
import by.cizmar.internal.learn_rabbit_mq.dto.request.ProducerTaskChangeRequest;
import by.cizmar.internal.learn_rabbit_mq.scheduler.task.ProducerTask;
import by.cizmar.internal.learn_rabbit_mq.scheduler.ScheduledTasksManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledMessageProducer {

    private final ScheduledTasksManager scheduledTasksManager;
    private final MessageProducer messageProducer;
    private final AppProperties appProperties;

    public void rescheduleProducer(ProducerTaskChangeRequest request) {
        ProducerTask producerTask = new ProducerTask(messageProducer, AppConstants.SCHEDULED_PRODUCER_TASK,
                appProperties.getExchange().getScheduledFanout(), "", request);
        scheduledTasksManager.runScheduledTask(producerTask, Duration.ofMillis(request.getPeriodInMilliseconds()));
    }

    public void stopProducer() {
        scheduledTasksManager.cancelTaskIfExists(AppConstants.SCHEDULED_PRODUCER_TASK);
    }
}
