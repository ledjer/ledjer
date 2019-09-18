package galaktica.trading.kryptonite;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * https://www.baeldung.com/java-bouncy-castle
 */
public class LedjerCrypto {
    public static String sha256HashOf(Object ... parameters) {
        StringBuilder sb = new StringBuilder();
        for (Object p : parameters) {
            sb.append(p.toString());
        }
        String m = sb.toString();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(m.getBytes(UTF_8)); // Change this to UTF-16 if needed
            byte[] digest = md.digest();

            String hex = String.format("%064x", new BigInteger(1, digest));
            return hex;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String sign(TxData txData) {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
