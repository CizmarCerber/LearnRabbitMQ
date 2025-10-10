package by.cizmar.internal.learn_rabbit_mq.dto.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventTicketsPayload extends BasicEventPayload {

    @NotNull
    private Long vendorId;

    @NotNull
    private Long eventId;

    @NotNull
    private Long ticketsAmount;

    @NotNull
    private String hash;

}
