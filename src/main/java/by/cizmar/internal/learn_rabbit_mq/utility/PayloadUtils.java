package by.cizmar.internal.learn_rabbit_mq.utility;

import by.cizmar.internal.learn_rabbit_mq.dto.payload.CustomRandomizedPayload;
import by.cizmar.internal.learn_rabbit_mq.enumeration.PayloadType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayloadUtils {

    public static Object getPayloadFromMessage(String message, PayloadType type) {
        if (PayloadType.JSON == type) {
            return new CustomRandomizedPayload(message);
        } else {
            return message;
        }
    }

}
