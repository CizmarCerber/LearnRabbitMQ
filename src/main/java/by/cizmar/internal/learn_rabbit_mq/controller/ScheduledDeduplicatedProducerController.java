package by.cizmar.internal.learn_rabbit_mq.controller;

import by.cizmar.internal.learn_rabbit_mq.dto.request.ProducerTaskChangeRequest;
import by.cizmar.internal.learn_rabbit_mq.service.ScheduledDeduplicatedMessageProducer;
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
@Tag(name = "ScheduledDeduplicatedProducer", description = "Send messages with deduplication plugin headers")
@RequestMapping(value = "/api/v1/scheduled-deduplication-producer")
public class ScheduledDeduplicatedProducerController {

    private final ScheduledDeduplicatedMessageProducer scheduledDeduplicatedMessageProducer;

    @PostMapping("/start")
    @Operation(summary = "Configure scheduler for DeduplicatedProducer sending messages (to both queues, without+with deduplication) and start/restart")
    public void startProducingMessages(@ParameterObject @Valid ProducerTaskChangeRequest request) {
        scheduledDeduplicatedMessageProducer.rescheduleProducer(request);
    }

    @PostMapping("/stop")
    @Operation(summary = "Stop DeduplicatedProducer for sending messages if run")
    public void stopProducingMessages() {
        scheduledDeduplicatedMessageProducer.stopProducer();
    }
}
