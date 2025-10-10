package by.cizmar.internal.learn_rabbit_mq.controller;

import by.cizmar.internal.learn_rabbit_mq.dto.payload.EventTicketsPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.payload.VendorEventsPayload;
import by.cizmar.internal.learn_rabbit_mq.service.MessageProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Message")
@RequestMapping(value = "/api/v1/messages")
public class MessageController {

    private final MessageProducer messageProducer;

    @PostMapping("/simple-connection")
    @Operation(summary = "Send SimpleConnection (String) message")
    public ResponseEntity<String> sendSimpleConnectionMessage(@Valid @RequestBody String message) {
        messageProducer.sendSimpleConnectionMessage(message);
        return ResponseEntity.ok("Simple message sent: %s".formatted(message));
    }

    @PostMapping("/event-tickets")
    @Operation(summary = "Send EventTickets message")
    public ResponseEntity<String> sendEventTicketsMessage(@Valid @RequestBody EventTicketsPayload payload) {
        messageProducer.sendEventTicketsMessage(payload);
        return ResponseEntity.ok("Message UUID: %s".formatted(payload.getEventUuid()));
    }

    @PostMapping("/vendor-events")
    @Operation(summary = "Send VendorEvents message")
    public ResponseEntity<String> sendVendorEventsMessage(@Valid @RequestBody VendorEventsPayload payload) {
        messageProducer.sendVendorEventsMessage(payload);
        return ResponseEntity.ok("Message UUID: %s".formatted(payload.getEventUuid()));
    }

}
