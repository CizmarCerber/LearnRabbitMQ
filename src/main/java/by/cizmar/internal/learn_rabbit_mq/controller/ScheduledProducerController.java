package by.cizmar.internal.learn_rabbit_mq.controller;

import by.cizmar.internal.learn_rabbit_mq.dto.request.ProducerTaskChangeRequest;
import by.cizmar.internal.learn_rabbit_mq.service.ScheduledMessageProducer;
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
@Tag(name = "ScheduledProducer")
@RequestMapping(value = "/api/v1/scheduled-producer")
public class ScheduledProducerController {

    private final ScheduledMessageProducer scheduledMessageProducer;

    @PostMapping("/start-producer")
    @Operation(summary = "Configure scheduler for Producer sending messages and start/restart")
    public void startProducingMessages(@ParameterObject @Valid ProducerTaskChangeRequest request) {
        scheduledMessageProducer.rescheduleProducer(request);
    }

    @PostMapping("/stop-producer")
    @Operation(summary = "Stop Producer for sending messages if run")
    public void stopProducingMessages() {
        scheduledMessageProducer.stopProducer();
    }

}
