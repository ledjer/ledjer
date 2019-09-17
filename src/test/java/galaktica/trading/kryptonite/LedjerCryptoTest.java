package galaktica.trading.kryptonite;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LedjerCryptoTest {

    @Test
    public void makesSha256Hash() {
        String m = "A beginning is a very delicate time";

        String hash = LedjerCrypto.sha256HashOf(m);

        assertThat(hash, is("93e34a63750eb78861d860e159f87e4f55408cd61c51f3a738bf1a44825247e9"));
    }
}
