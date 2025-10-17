package by.cizmar.internal.learn_rabbit_mq.controller;

import by.cizmar.internal.learn_rabbit_mq.dto.request.ConsumerTaskRequest;
import by.cizmar.internal.learn_rabbit_mq.service.ScheduledMessageConsumer;
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
@Tag(name = "ScheduledConsumer")
@RequestMapping(value = "/api/v1/scheduled-consumer")
public class ScheduledConsumerController {

    private final ScheduledMessageConsumer scheduledMessageConsumer;

    @PostMapping("/start")
    @Operation(summary = "Configure scheduler for Consumer receiving messages and start/restart")
    public void startConsumingMessages(@ParameterObject @Valid ConsumerTaskRequest request) {
        scheduledMessageConsumer.rescheduleConsumer(request);
    }

    @PostMapping("/stop")
    @Operation(summary = "Stop Consumer for receiving messages if run")
    public void stopConsumingMessages() {
        scheduledMessageConsumer.stopConsumer();
    }
}
