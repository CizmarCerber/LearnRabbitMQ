package by.cizmar.internal.learn_rabbit_mq.dto.request;

import by.cizmar.internal.learn_rabbit_mq.enumeration.PayloadType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProducerTaskChangeRequest {
    @NotNull
    @Min(100)
    private Integer periodInMilliseconds;

    @NotNull
    @Min(1)
    private Integer messagesPerPeriod;

    @NotNull
    @Min(1)
    private Integer messageLength;

    @NotNull
    private PayloadType payloadType;

    @NotNull
    private boolean checkDuplicates;
}
