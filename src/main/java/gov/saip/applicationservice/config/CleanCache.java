package gov.saip.applicationservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class CleanCache implements CommandLineRunner {


    public final static String SERVICE_NAME_KEY_PREFIX = "application_";

    private final RedisTemplate<String, String> redistemplate;
    @Override
    public void run(String... args) throws Exception {
        log.info("start clear cache of  {} service", SERVICE_NAME_KEY_PREFIX);
        flushCashRedisServer();
        log.info("end clear cache of  {} service ", SERVICE_NAME_KEY_PREFIX);

    }



    private void flushCashRedisServer() {
        try {
            String prefix = SERVICE_NAME_KEY_PREFIX;
            ScanOptions options = ScanOptions.scanOptions().match(prefix + "*").build();
            Cursor<String> cursor = redistemplate.scan(options);
            while (cursor.hasNext()) {
                String key = cursor.next();
                redistemplate.delete(key);
            }
        } catch (Exception ex) {
            log.info("some thing wrong when delete cache cause ={}", ex.getMessage());
        }

    }
}
