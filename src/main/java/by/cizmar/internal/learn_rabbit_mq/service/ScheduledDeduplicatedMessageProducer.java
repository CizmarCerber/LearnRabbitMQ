package by.cizmar.internal.learn_rabbit_mq.service;

import by.cizmar.internal.learn_rabbit_mq.config.AppConstants;
import by.cizmar.internal.learn_rabbit_mq.config.properties.AppProperties;
import by.cizmar.internal.learn_rabbit_mq.dto.request.ProducerTaskChangeRequest;
import by.cizmar.internal.learn_rabbit_mq.enumeration.PayloadType;
import by.cizmar.internal.learn_rabbit_mq.scheduler.ScheduledTasksManager;
import by.cizmar.internal.learn_rabbit_mq.scheduler.task.DeduplicatedProducerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ScheduledDeduplicatedMessageProducer {

    private final ScheduledTasksManager scheduledTasksManager;
    private final MessageProducer messageProducer;
    private final AppProperties appProperties;

    public void rescheduleProducer(ProducerTaskChangeRequest request) {
        DeduplicatedProducerTask producerTask = new DeduplicatedProducerTask(messageProducer,
                AppConstants.SCHEDULED_DEDUPLICATED_PRODUCER_TASK,
                appProperties.getExchange().getScheduledDedupTopic(), getRouting(request), request);
        scheduledTasksManager.runScheduledTask(producerTask, Duration.ofMillis(request.getPeriodInMilliseconds()));
    }

    public void stopProducer() {
        scheduledTasksManager.cancelTaskIfExists(AppConstants.SCHEDULED_DEDUPLICATED_PRODUCER_TASK);
    }

    private String getRouting(ProducerTaskChangeRequest request) {
        return request.getPayloadType() == PayloadType.TEXT
                ? appProperties.getRouting().getScheduledDedupText()
                : appProperties.getRouting().getScheduledDedupJson();
    }

}
