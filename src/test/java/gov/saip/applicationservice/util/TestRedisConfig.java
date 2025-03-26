package gov.saip.applicationservice.util;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
//import redis.embedded.RedisServer;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <p>Creates embedded redis server for testing.</p>
 */
@TestConfiguration
public class TestRedisConfig {
//    private RedisServer redisServer;
//
//    @Value("${redis.port}")
//    private int port;
//
//    @PostConstruct
//    public void postConstruct() {
////        this.redisServer = new RedisServer(port);
////        redisServer.start();
////        // Stop server if we CTRL + C out of tests
////        Runtime.getRuntime().addShutdownHook(
////                new Thread(() -> {
////                    redisServer.stop();
////                })
////        );
//    }
//
//    @PreDestroy
//    public void preDestroy() {
//        redisServer.stop();
//    }

}
