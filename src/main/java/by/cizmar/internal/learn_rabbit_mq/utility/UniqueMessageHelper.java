package by.cizmar.internal.learn_rabbit_mq.utility;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UniqueMessageHelper {
    @Getter(lazy = true)
    private static final UniqueMessageHelper instance = new UniqueMessageHelper();

    private final Set<String> messageHashes = ConcurrentHashMap.newKeySet();

    public boolean checkDuplicate(String hash) {
        return messageHashes.contains(hash);
    }

    public void addToCache(String hash) {
        messageHashes.add(hash);
    }

    public void clearCache() {
        messageHashes.clear();
    }
}
