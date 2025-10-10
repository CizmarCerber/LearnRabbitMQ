package by.cizmar.internal.learn_rabbit_mq.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BasicEventPayload implements Serializable {

    @Builder.Default
    private UUID eventUuid = UUID.randomUUID();

}
