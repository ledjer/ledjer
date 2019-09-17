package galaktica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    public static void main(String[] args) {
        log.info("Hello");
        log.debug("foo");
    }
}
