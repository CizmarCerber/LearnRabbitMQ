package by.cizmar.internal.learn_rabbit_mq.controller;

import by.cizmar.internal.learn_rabbit_mq.dto.request.ConsumerTaskRequest;
import by.cizmar.internal.learn_rabbit_mq.service.ScheduledDeduplicatedMessageConsumer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ScheduledDeduplicatedConsumer", description = "Receive messages with deduplication plugin headers")
@RequestMapping(value = "/api/v1/scheduled-deduplication-consumer")
public class ScheduledDeduplicatedConsumerController {

    private final ScheduledDeduplicatedMessageConsumer scheduledDeduplicatedMessageConsumer;

    @PostMapping("/start-without")
    @Operation(summary = "Configure scheduler for Consumer receiving messages without deduplication and start/restart")
    public void startConsumingMessagesWithoutDeduplication(@ParameterObject @Valid ConsumerTaskRequest request) {
        scheduledDeduplicatedMessageConsumer.rescheduleConsumerWithoutDedup(request);
    }

    @PostMapping("/start-with")
    @Operation(summary = "Configure scheduler for Consumer receiving messages with deduplication and start/restart")
    public void startConsumingMessagesWithDeduplication(@ParameterObject @Valid ConsumerTaskRequest request) {
        scheduledDeduplicatedMessageConsumer.rescheduleConsumerWithDedup(request);
    }

    @PostMapping("/stop-without")
    @Operation(summary = "Stop Consumer without deduplication for receiving messages if run")
    public void stopConsumingMessagesWithoutDeduplication() {
        scheduledDeduplicatedMessageConsumer.stopConsumerWithoutDedup();
    }

    @PostMapping("/stop-with")
    @Operation(summary = "Stop Consumer with deduplication for receiving messages if run")
    public void stopConsumingMessagesWithDeduplication() {
        scheduledDeduplicatedMessageConsumer.stopConsumerWithDedup();
    }

}
