package by.cizmar.internal.learn_rabbit_mq.dto.request;

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
public class ConsumerTaskRequest {
    @NotNull
    @Min(1000)
    private Integer periodInMilliseconds;

    @NotNull
    @Min(1)
    private Integer messagesPerPeriod;
}
