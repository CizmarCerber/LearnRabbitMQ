package by.cizmar.internal.learn_rabbit_mq.dto.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VendorEventsPayload extends BasicEventPayload {

    @NotNull
    private Long vendorId;

    @NotNull
    private Long eventsAmount;

    @Builder.Default
    private List<Long> eventIds = new ArrayList<>();

    @NotNull
    private String hash;

}
