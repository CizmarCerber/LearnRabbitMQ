package by.cizmar.internal.learn_rabbit_mq.controller;

import by.cizmar.internal.learn_rabbit_mq.dto.payload.EventTicketsPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.VendorEventsPayload;
import by.cizmar.internal.learn_rabbit_mq.service.MessageProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Producer")
@RequestMapping(value = "/api/v1/producer")
public class ProducerController {

    private final MessageProducer messageProducer;

    @PostMapping("/simple/string/{sendDirect}")
    @Operation(summary = "Send Simple (String) message")
    public ResponseEntity<String> sendSimpleStringMessage(@Valid @RequestBody String message,
                                                          @PathVariable @NotNull Boolean sendDirect) {
        messageProducer.sendSimpleStringMessage(message, sendDirect);
        return ResponseEntity.ok("Simple String message sent: %s".formatted(message));
    }

    @PostMapping("/simple/integer/{sendDirect}")
    @Operation(summary = "Send Simple (Integer) message - redirected to DLQ if can't parse as Integer")
    public ResponseEntity<String> sendSimpleIntegerMessage(@Valid @RequestBody Object message,
                                                           @PathVariable @NotNull Boolean sendDirect) {
        messageProducer.sendSimpleIntegerMessage(message, sendDirect);
        return ResponseEntity.ok("Simple Integer message sent: %s".formatted(message));
    }

    @PostMapping("/event-tickets/{sendDirect}")
    @Operation(summary = "Send EventTickets (JSON) message")
    public ResponseEntity<String> sendEventTicketsMessage(@Valid @RequestBody EventTicketsPayload payload,
                                                          @PathVariable @NotNull Boolean sendDirect) {
        messageProducer.sendEventTicketsMessage(payload, sendDirect);
        return ResponseEntity.ok("Message UUID: %s".formatted(payload.getEventUuid()));
    }

    @PostMapping("/vendor-events/{sendDirect}")
    @Operation(summary = "Send VendorEvents (JSON) message")
    public ResponseEntity<String> sendVendorEventsMessage(@Valid @RequestBody VendorEventsPayload payload,
                                                          @PathVariable @NotNull Boolean sendDirect) {
        messageProducer.sendVendorEventsMessage(payload, sendDirect);
        return ResponseEntity.ok("Message UUID: %s".formatted(payload.getEventUuid()));
    }

    @PostMapping("/manual-nack")
    @Operation(summary = "Send ManualAck (String) message - should Nack and requeue to DLQ if message not instanceof Integer")
    public ResponseEntity<String> sendManualAckMessage(@RequestBody Object message) {
        messageProducer.sendManualAckMessage(message);
        return ResponseEntity.ok("ManualAck message sent: %s".formatted(message));
    }
}
