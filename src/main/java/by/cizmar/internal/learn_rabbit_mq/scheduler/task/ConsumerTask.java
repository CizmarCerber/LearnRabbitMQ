package by.cizmar.internal.learn_rabbit_mq.scheduler.task;

import by.cizmar.internal.learn_rabbit_mq.dto.payload.CustomRandomizedPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.request.ConsumerTaskRequest;
import by.cizmar.internal.learn_rabbit_mq.service.MessageConsumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class ConsumerTask extends RunnableTask {

    private final MessageConsumer messageConsumer;
    private final String title;
    private final String queue;
    private final ConsumerTaskRequest request;

    @Override
    public void run() {
        log.info("Consumer task {} started for request {}", title, request);
        for (int i = 0; i < request.getMessagesPerPeriod(); i++) {
            Object message = messageConsumer.receiveCustomMessageFromQueueImmediately(queue);
            if (message == null) {
                log.warn("Queue {} is already empty; stop consuming cycle", queue);
            }
            if (message instanceof CustomRandomizedPayload payload) {
                log.info("JSON payload received from {}: {}", queue, payload);
            } else if (message instanceof String stringMessage) {
                log.info("String payload received from {}: {}", queue, stringMessage);
            } else {
                log.info("Message with unknown format received from {}: {}", queue, message);
            }
        }
    }
}
