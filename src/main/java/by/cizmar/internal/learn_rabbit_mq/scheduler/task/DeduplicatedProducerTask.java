package by.cizmar.internal.learn_rabbit_mq.scheduler.task;

import by.cizmar.internal.learn_rabbit_mq.dto.request.ProducerTaskChangeRequest;
import by.cizmar.internal.learn_rabbit_mq.service.MessageProducer;
import by.cizmar.internal.learn_rabbit_mq.utility.HashUtils;
import by.cizmar.internal.learn_rabbit_mq.utility.StringUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static by.cizmar.internal.learn_rabbit_mq.config.AppConstants.DEDUPLICATION_HEADER;
import static by.cizmar.internal.learn_rabbit_mq.utility.PayloadUtils.getPayloadFromMessage;

@Slf4j
@Getter
@RequiredArgsConstructor
public class DeduplicatedProducerTask extends RunnableTask {
    private final MessageProducer messageProducer;
    private final String title;
    private final String exchange;
    private final String routingKey;
    private final ProducerTaskChangeRequest request;

    @Override
    public void run() {
        log.info("Deduplicated Producer task {} started for request {}", title, request);
        for (int i = 0; i < request.getMessagesPerPeriod(); i++) {
            String randomString = StringUtils.generateRandom(request.getMessageLength());
            Object requiredMessage = getPayloadFromMessage(randomString, request.getPayloadType());
            // add deduplication headers always; if queue supports them - it will use them
            messageProducer.sendCustomMessageToExchangeRoutingKey(requiredMessage, exchange, routingKey, msg -> {
                String hash = HashUtils.calculateHash(requiredMessage);
                msg.getMessageProperties().setHeader(DEDUPLICATION_HEADER, hash);
                return msg;
            });
        }
    }
}
