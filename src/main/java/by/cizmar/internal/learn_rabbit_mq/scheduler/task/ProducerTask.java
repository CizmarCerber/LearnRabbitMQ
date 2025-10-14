package by.cizmar.internal.learn_rabbit_mq.scheduler.task;

import by.cizmar.internal.learn_rabbit_mq.dto.payload.CustomRandomizedPayload;
import by.cizmar.internal.learn_rabbit_mq.dto.request.ProducerTaskChangeRequest;
import by.cizmar.internal.learn_rabbit_mq.enumeration.PayloadType;
import by.cizmar.internal.learn_rabbit_mq.service.MessageProducer;
import by.cizmar.internal.learn_rabbit_mq.utility.HashUtils;
import by.cizmar.internal.learn_rabbit_mq.utility.StringUtils;
import by.cizmar.internal.learn_rabbit_mq.utility.UniqueMessageHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class ProducerTask extends RunnableTask {

    private final MessageProducer messageProducer;
    private final String title;
    private final String exchange;
    private final String routingKey;
    private final ProducerTaskChangeRequest request;

    @Override
    public void run() {
        log.info("Runnable task {} started with params {}", title, request);
        UniqueMessageHelper uniqueMessageHelper = UniqueMessageHelper.getInstance();
        for (int i = 0; i < request.getMessagesPerPeriod(); i++) {
            String randomString = StringUtils.generateRandom(request.getMessageLength());
            Object requiredMessage = getPayloadFromMessage(randomString, request.getPayloadType());
            if (request.isCheckDuplicates()) {
                String hash = HashUtils.calculateHash(requiredMessage);
                if (uniqueMessageHelper.checkDuplicate(hash)) {
                    log.warn("Message {} is duplicate and was previously sent", requiredMessage);
                    continue;
                }
                uniqueMessageHelper.addToCache(hash);
            }
            messageProducer.sendCustomMessageToExchangeRoutingKey(requiredMessage, exchange, routingKey);
        }
    }

    private Object getPayloadFromMessage(String message, PayloadType type) {
        if (PayloadType.JSON == type) {
            return new CustomRandomizedPayload(message);
        } else {
            return message;
        }
    }
}
