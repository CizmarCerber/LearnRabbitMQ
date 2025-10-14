package by.cizmar.internal.learn_rabbit_mq.utility;

import jakarta.xml.bind.DatatypeConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashUtils {

    private static final String NO_HASH = "65dd1418b533b8a397b1802a2bca4a83";

    public static String calculateHash(Object message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(message.toString().getBytes());
            return DatatypeConverter.printHexBinary(hash).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("Can't calculate hash - no encrypting algorithm");
            return NO_HASH;
        }
    }

}
